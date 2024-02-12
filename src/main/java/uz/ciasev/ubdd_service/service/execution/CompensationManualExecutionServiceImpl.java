package uz.ciasev.ubdd_service.service.execution;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.ManualPaymentRequestDTO;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.dict.VictimTypeAlias;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationCollectingError;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;
import uz.ciasev.ubdd_service.service.resolution.compensation.CompensationService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionAccessService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CompensationManualExecutionServiceImpl implements CompensationManualExecutionService {

    private final DecisionAccessService decisionAccessService;
    private final DecisionService decisionService;
    private final ManualBillingService manualBillingService;
    private final ProtocolService protocolService;
    private final CompensationService compensationService;

    @Override
    @Transactional
    public void deleteManualBilling(User user, Long compensationId) {
        Compensation compensation = compensationService.findById(compensationId);
        Decision decision = decisionService.getById(compensation.getDecisionId());

        if (!manualBillingService.hasAdminPayment(compensation)) {
            return;
        }

        manualBillingService.deleteAdminPayment(user, decision, compensation);
//        historyService.deleteManualExecution(compensation, ManualExecutionDeleteRegistrationType.ADMIN_MANUAL_PAYMENT);
    }

    @Override
    @Transactional
    public void executeWithoutBilling(User user, Long compensationId, ManualPaymentRequestDTO dto) {
        Compensation compensation = compensationService.findById(compensationId);

        // VALIDATION
        validate(user, compensation, dto);

        // CREATE MANUAL PAYMENT
        manualBillingService.createAdminPayment(user, compensation, dto);
    }

    private void validate(User user, Compensation compensation, ManualPaymentRequestDTO dto) {
        Decision decision = decisionService.getById(compensation.getDecisionId());
        decisionAccessService.checkUserActionPermit(user, ActionAlias.MANUAL_PAYMENT_EXECUTION, decision);

        if(compensation.getVictimType().not(VictimTypeAlias.GOVERNMENT)) {
            throw new ValidationException(ErrorCode.COMPENSATION_VICTIM_NOT_GOVERNMENT);
        }

        ValidationCollectingError errors = new ValidationCollectingError();

        // Не стали добавлять, по той же причине, почему убрали в штрафах
//        errors.addIf(!resolution.getResolutionTime().isBefore(GlobalConstants.DAY_X.atStartOfDay()), ErrorCode.ONLY_MIGRATED_DATA_IS_APPLICABLE);

        errors.addIf(compensation.isExecuted(), ErrorCode.COMPENSATION_ALREADY_EXECUTED);

        Protocol mainProtocol = protocolService.getMainByViolatorId(decision.getViolatorId());
        errors.addIf(dto.getLastPayDate().isBefore(mainProtocol.getViolationTime().toLocalDate()), ErrorCode.LAST_PAY_DATE_BEFORE_VIOLATION_TIME);

        errors.throwErrorIfNotEmpty();
    }
}
