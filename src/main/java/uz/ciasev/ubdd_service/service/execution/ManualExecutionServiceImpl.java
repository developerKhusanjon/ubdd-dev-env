package uz.ciasev.ubdd_service.service.execution;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.execution.ArrestExecutionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.execution.DeportationExecutionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.execution.PunishmentExecutionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.execution.WithdrawalExecutionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.PunishmentExecuteWithoutBillingDTO;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias;
import uz.ciasev.ubdd_service.entity.history.ManualExecutionDeleteRegistrationType;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.execution.ExecutorType;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecutionType;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.signature.SignatureEvent;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.event.AdmEventService;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.IncorrectExecutionTypeForPunishmentException;
import uz.ciasev.ubdd_service.exception.PunishmentAlreadyExecutedException;
import uz.ciasev.ubdd_service.exception.ValidationCollectingError;
import uz.ciasev.ubdd_service.repository.protocol.ProtocolRepository;
import uz.ciasev.ubdd_service.repository.resolution.punishment.PenaltyPunishmentRepository;
import uz.ciasev.ubdd_service.service.aop.signature.DigitalSignatureCheck;
import uz.ciasev.ubdd_service.service.history.HistoryService;
import uz.ciasev.ubdd_service.service.invoice.InvoiceActionService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionAccessService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;
import uz.ciasev.ubdd_service.service.resolution.punishment.PunishmentActionService;
import uz.ciasev.ubdd_service.service.resolution.punishment.PunishmentService;
import uz.ciasev.ubdd_service.service.search.arrest.ArrestFullListResponseDTO;
import uz.ciasev.ubdd_service.service.search.arrest.ArrestSearchService;
import uz.ciasev.ubdd_service.service.status.StatusService;
import uz.ciasev.ubdd_service.utils.validator.Inspector;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

import static uz.ciasev.ubdd_service.entity.status.AdmStatusAlias.EXECUTED;
import static uz.ciasev.ubdd_service.entity.status.AdmStatusAlias.IN_EXECUTION_PROCESS;

@RequiredArgsConstructor
@Service
@Slf4j
public class ManualExecutionServiceImpl implements ManualExecutionService {

    private final PunishmentActionService punishmentActionService;
    private final PunishmentService punishmentService;
    private final DecisionAccessService decisionAccessService;
    private final DecisionService decisionService;
    private final ExecutionCallbackService executionCallbackService;
    private final ArrestSearchService arrestSearchService;
    private final ManualBillingService manualBillingService;
    private final PenaltyPunishmentRepository penaltyPunishmentRepository;
    private final InvoiceActionService invoiceActionService;
    private final ProtocolService protocolService;
    private final HistoryService historyService;
    private final StatusService admStatusService;
    private final ProtocolRepository protocolRepository;
    private final AdmEventService admEventService;

    // на этих трех методах не было транзакции до 2023-03-11. изза этого омгут быть ситуации в базе. когда арест вроде как исполнен, а статусы не исполненые.
    @Override
    @Transactional
    public void executionWithdrawal(@Inspector User user, Long id, WithdrawalExecutionRequestDTO requestDTO) {
        executionApply(user, id, requestDTO, this::validateWithdrawal, false);
    }

    // на этих трех методах не было транзакции до 2023-03-11. изза этого омгут быть ситуации в базе. когда арест вроде как исполнен, а статусы не исполненые.
    @Override
    @Transactional
    public void executionArrest(@Inspector User user, Long id, ArrestExecutionRequestDTO requestDTO) {
        executionApply(user, id, requestDTO, this::validateArrest, true);
    }

    // на этих трех методах не было транзакции до 2023-03-11. изза этого омгут быть ситуации в базе. когда арест вроде как исполнен, а статусы не исполненые.
    @Override
    @Transactional
    public void executionDeportation(@Inspector User user, Long id, DeportationExecutionRequestDTO requestDTO) {
        executionApply(user, id, requestDTO, this::validateDeportation, false);
    }

    @Transactional
    @Override
    public void deleteManualBilling(User user, Long punishmentId) {
        Punishment punishment = punishmentService.getById(punishmentId);
        Decision decision = decisionService.getById(punishment.getDecisionId());

        if (punishment.getPenalty() == null) {
            return;
        }

        if (!manualBillingService.hasAdminPayment(punishment)) {
            return;
        }

        manualBillingService.deleteAdminPayment(user, decision, punishment);
//        historyService.deleteManualExecution(punishment, ManualExecutionDeleteRegistrationType.ADMIN_MANUAL_PAYMENT);
    }

    @Transactional
    @Override
    @DigitalSignatureCheck(event = SignatureEvent.PUNISHMENT_MANUAL_EXECUTION)
    public Punishment executeWithoutBilling(User user, Long punishmentId, PunishmentExecuteWithoutBillingDTO dto) {

        Punishment punishment = punishmentService.getById(punishmentId);

        validate(user, punishment, dto);

        PenaltyPunishment penaltyPunishment = punishment.getPenalty();

        // GET PUNISHMENT AND UPDATE PENALTY PUNISHMENT
        updatePenalty(user, penaltyPunishment, dto);

        // CREATE MANUAL PAYMENT
        manualBillingService.createAdminPayment(user, punishment, dto);

        // BLOCK INVOICE

//        invoiceActionService.closeForPenalty(penaltyPunishment, InvoiceDeactivateReasonAlias.MANUAL_PAYMENT_EXECUTION, List.of());
        Punishment savedPunishment = punishmentService.getById(punishmentId);
        admEventService.fireEvent(AdmEventType.PUNISHMENT_STATUS_CHANGE, Pair.of(savedPunishment, ExecutorType.MANUAL_EXECUTION));

        return punishment;
    }

    @Override
    @Transactional
    public void cancelGaiFileForceExecution(Long punishmentId) {
        if (!punishmentService.existsForceExecutionByPunishmentIdAndType(punishmentId, ForceExecutionType.UBDD_FILE)) {
            return;
        }

        Punishment punishment = punishmentService.getById(punishmentId);
        Decision decision = decisionService.getById(punishment.getDecisionId());

        punishmentActionService.deleteExecution(punishment, ExecutorType.MANUAL_EXECUTION, "", ForceExecutionType.UBDD_FILE);
        admStatusService.cancelStatus(decision, EXECUTED, IN_EXECUTION_PROCESS);
        admStatusService.cancelStatus(decision.getResolution(), EXECUTED, IN_EXECUTION_PROCESS);
        executionCallbackService.executeCallbackWithoutLazy(decision);

        historyService.deleteManualExecution(punishment, ManualExecutionDeleteRegistrationType.UBDD_FILE_FORCE_EXECUTION);
    }

    @Override
    public Page<ArrestFullListResponseDTO> globalSearchByFilter(Map<String, String> filterValues, Pageable pageable) {
        return arrestSearchService.findAllFullProjectionByFilter(filterValues, pageable)
                .map(ArrestFullListResponseDTO::new);
    }

    private void executionApply(User user, Long id, PunishmentExecutionRequestDTO requestDTO, BiConsumer<Decision,
            PunishmentExecutionRequestDTO> additionalValidation, boolean notValidateByOrgan) {

        Punishment punishment = punishmentService.getById(id);
        if (!punishment.getType().getAlias().equals(requestDTO.getPunishmentTypeAlias())) {
            throw new IncorrectExecutionTypeForPunishmentException();
        }
        Decision decision = decisionService.getById(punishment.getDecisionId());

        if (notValidateByOrgan) {
            validateExecutionAbilityNoOrgan(user, punishment, decision);
        } else {
            validateExecutionAbility(user, punishment, decision);
        }

        if (additionalValidation != null) {
            additionalValidation.accept(decision, requestDTO);
        }

        requestDTO.applyTo(punishment);
        punishmentActionService.addExecution(user, punishment, ExecutorType.MANUAL_EXECUTION);

        //executionCallbackService.executeCallback(decision);
        executionCallbackService.executeCallback(decisionService.getById(decision.getId()));
    }

    private void validateExecutionAbility(User user, Punishment punishment, Decision decision) {
        if (punishment.isExecuted()) {
            throw new PunishmentAlreadyExecutedException();
        }
        decisionAccessService.checkUserActionPermit(user, ActionAlias.PUNISHMENT_EXECUTION, decision);
    }

    private void validateExecutionAbilityNoOrgan(User user, Punishment punishment, Decision decision) {
        if (punishment.isExecuted()) {
            throw new PunishmentAlreadyExecutedException();
        }
        decisionAccessService.checkUserActionPermitNoOrgan(user, ActionAlias.PUNISHMENT_EXECUTION, decision);
    }

    private void validateWithdrawal(Decision decision, PunishmentExecutionRequestDTO dto) {

        WithdrawalExecutionRequestDTO requestDTO = (WithdrawalExecutionRequestDTO) dto;

        LocalDate repaymentDate = requestDTO.getRepaymentDate();

        ValidationCollectingError error = new ValidationCollectingError();
        error.addIf(
                Objects.nonNull(repaymentDate) && repaymentDate.isAfter(LocalDate.now()),
                ErrorCode.REPAYMENT_DATE_IN_FUTURE
        );
        error.addIf(
                Objects.nonNull(repaymentDate) && repaymentDate.isBefore(requestDTO.getSaleDate()),
                ErrorCode.REPAYMENT_DATE_BEFORE_SAIL_DATE
        );
        error.addIf(
                requestDTO.getSaleDate().isBefore(decision.getExecutionFromDate()),
                ErrorCode.SAIL_DATE_BEFORE_DECISION_EXECUTION_FROM_DATE
        );
        error.throwErrorIfNotEmpty();
    }

    private void validateArrest(Decision decision, PunishmentExecutionRequestDTO dto) {

        ArrestExecutionRequestDTO requestDTO = (ArrestExecutionRequestDTO) dto;
        ValidationCollectingError error = new ValidationCollectingError();

        error.addIf(
                decision.getExecutionFromDate() != null && requestDTO.getInDate().isBefore(decision.getExecutionFromDate()),
                ErrorCode.IN_DATE_BEFORE_DECISION_EXECUTION_FROM_DATE
        );
        error.addIf(
                requestDTO.getOutDate() != null && requestDTO.getOutDate().isBefore(requestDTO.getInDate()),
                ErrorCode.OUT_DATE_BEFORE_IN_DATE
        );

        LocalDate recentDate = LocalDate.now();

        error.addIf(
                requestDTO.getInDate() != null && recentDate.isBefore(requestDTO.getInDate()),
                ErrorCode.IN_DATE_GREATER_THAN_RECENT_DATE
        );
        error.addIf(
                requestDTO.getOutDate() != null && recentDate.isBefore(requestDTO.getOutDate()),
                ErrorCode.OUT_DATE_GREATER_THAN_RECENT_DATE
        );

        error.throwErrorIfNotEmpty();
    }

    private void validateDeportation(Decision decision, PunishmentExecutionRequestDTO dto) {

        DeportationExecutionRequestDTO requestDTO = (DeportationExecutionRequestDTO) dto;
        ValidationCollectingError error = new ValidationCollectingError();

        error.addIf(
                decision.getExecutionFromDate() != null && requestDTO.getExecutionDate().isBefore(decision.getExecutionFromDate()),
                ErrorCode.DEPORTATION_DATE_BEFORE_DECISION_EXECUTION_FROM_DATE
        );

        LocalDate recentDate = LocalDate.now();

        error.addIf(
                requestDTO.getDeportationDate() != null && recentDate.isBefore(requestDTO.getDeportationDate()),
                ErrorCode.DEPORTATION_DATE_GREATER_THAN_RECENT_DATE
        );

        error.throwErrorIfNotEmpty();
    }

    private void updatePenalty(User user, PenaltyPunishment penaltyPunishment, PunishmentExecuteWithoutBillingDTO dto) {

        boolean discount70Set = dto.getDiscount70Amount() != null && dto.getDiscount70ForDate() != null;
        boolean discount50Set = dto.getDiscount50Amount() != null && dto.getDiscount50ForDate() != null;

        if (discount70Set && !penaltyPunishment.getIsDiscount70()) {
            penaltyPunishment.setDiscount70Amount(dto.getDiscount70Amount());
            penaltyPunishment.setDiscount70ForDate(dto.getDiscount70ForDate());
            penaltyPunishment.setIsDiscount70(true);

            if (discount50Set && !penaltyPunishment.getIsDiscount50()) {
                penaltyPunishment.setDiscount50Amount(dto.getDiscount50Amount());
                penaltyPunishment.setDiscount50ForDate(dto.getDiscount50ForDate());
                penaltyPunishment.setIsDiscount50(true);
            }

            penaltyPunishmentRepository.save(penaltyPunishment);
        }

    }

    private void validate(User user, Punishment punishment, PunishmentExecuteWithoutBillingDTO dto) {

        Decision decision = decisionService.getById(punishment.getDecisionId());
        Resolution resolution = decision.getResolution();
        decisionAccessService.checkUserActionPermit(user, ActionAlias.MANUAL_PAYMENT_EXECUTION, decision);

        ValidationCollectingError errors = new ValidationCollectingError();

        // Убрали проверку по просьбе Бегзода 2022-01-14
        // CREATE TIME IS BEFORE LAUNCH
//        errors.addIf(!resolution.getResolutionTime().isBefore(GlobalConstants.DAY_X.atStartOfDay()), ErrorCode.ONLY_MIGRATED_DATA_IS_APPLICABLE);

        errors.addIf(resolution.getAdmCase().getOrgan().isCourt(), ErrorCode.COURT_DATA_NOT_ALLOWED);

        errors.addIf(punishment.isExecuted(), ErrorCode.PUNISHMENT_ALREADY_EXECUTED);

        Protocol mainProtocol = protocolService.getMainByViolatorId(decision.getViolatorId());
        errors.addIf(dto.getLastPayDate().isBefore(mainProtocol.getViolationTime().toLocalDate()), ErrorCode.LAST_PAY_DATE_BEFORE_VIOLATION_TIME);

        boolean isPenalty = punishment.getType().is(PunishmentTypeAlias.PENALTY);
        errors.addIf(!isPenalty, ErrorCode.PUNISHMENT_MUST_BE_OF_PENALTY_TYPE);

        if (!isPenalty) {
            errors.throwErrorIfNotEmpty();
            return;
        }

        // 2022-09-20 Из-за пересмотров, сума отплаты может быть больше суммы текущего решения
//        errors.addIf(dto.getPaidAmount().compareTo(punishment.getPenalty().getAmount()) > 0, ErrorCode.PAID_AMOUNT_MUST_NOT_BE_GREATER_THEN_PUNISHMENT_AMOUNT);

        // DISCOUNT VALIDATIONS:

        // IF ALREADY IS_DISCOUNT THEN REJECT INPUT OF DISCOUNT DATA
        boolean discount70Set = dto.getDiscount70Amount() != null && dto.getDiscount70ForDate() != null;
        boolean discount50Set = dto.getDiscount50Amount() != null && dto.getDiscount50ForDate() != null;
        if (punishment.getPenalty().getIsDiscount70()) {

            errors.addIf(discount70Set, ErrorCode.DISCOUNT_IS_ALREADY_SET);

            // IF (DTO.PAID_AMOUNT < PENALTY_PUNISHMENT.AMOUNT)
        }
        // 2023-04-12 В связи с добавлениемдвойной скидки, не имет смысл валедировтаь размер скидки, он будет то 70 % то 50.
        // Дата скидки тоже будет прыгать.
        // Так что пусть это остается на усмотрение админа
//        else if (dto.getPaidAmount().compareTo(punishment.getPenalty().getAmount()) < 0) {
//
//            boolean discountSet = dto.getDiscountAmount() != null && dto.getDiscountForDate() != null;
//
////            // DTO.DISCOUNT_AMOUNT && DTO.DISCOUNT_FOR_DATE = NOT NULL
////            errors.addIf(!discountSet, ErrorCode.DISCOUNT_NOT_SET);
//
//            if (discountSet) {
//
//                // DTO.DISCOUNT_FOR_DATE = RESOLUTION_TIME + 15 DAYS
//                errors.addIf(!dto.getDiscountForDate().equals(resolution.getResolutionTime().toLocalDate().plusDays(15)), ErrorCode.DISCOUNT_FOR_DATE_MUST_BE_RESOLUTION_DATE_PLUS_15);
//
//                // DTO.DISCOUNT_AMOUNT = PENALTY_PUNISHMENT.AMOUNT * 70%
//
//                long punishmentAmount70 = punishment.getPenalty().getAmount() * 7 / 10;
//                punishmentAmount70 = punishmentAmount70 / 100;
//                long discountAmount = dto.getDiscountAmount() / 100;
//
//                errors.addIf(discountAmount != punishmentAmount70, ErrorCode.DISCOUNT_AMOUNT_MUST_BE_70_PERCENT_OF_PENALTY_AMOUNT);
//
//                // DTO.LAST_PAY_DATE <= RESOLUTION_TIME + 15 DAYS
//                errors.addIf(!dto.getLastPayDate().isBefore(resolution.getResolutionTime().toLocalDate().plusDays(15)), ErrorCode.LAST_PAY_DATE_NOT_SUITABLE_FOR_DISCOUNT);
//            }
//        }

        // IF ALREADY IS_DISCOUNT THEN REJECT INPUT OF DISCOUNT DATA
        if (punishment.getPenalty().getIsDiscount50()) {

            errors.addIf(discount50Set, ErrorCode.DISCOUNT_50_IS_ALREADY_SET);

            // IF (DTO.PAID_AMOUNT < PENALTY_PUNISHMENT.AMOUNT)
        }

        if (discount50Set && !discount70Set) {
            errors.add(ErrorCode.DISCOUNT_50_NOT_ALLOW_WITHOUT_DISCOUNT);
        }

        errors.throwErrorIfNotEmpty();
    }
}
