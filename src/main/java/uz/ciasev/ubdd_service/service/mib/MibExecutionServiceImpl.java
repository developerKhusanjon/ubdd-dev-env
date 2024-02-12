package uz.ciasev.ubdd_service.service.mib;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.dict.OrganAlias;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.evidence_decision.EvidenceDecision;
import uz.ciasev.ubdd_service.entity.resolution.execution.ExecutorType;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecutionDTO;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecutionType;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.entity.mib.*;
import uz.ciasev.ubdd_service.event.AdmEventService;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.repository.mib.EvidenceDecisionMibRepository;
import uz.ciasev.ubdd_service.repository.resolution.evidence_decision.EvidenceDecisionRepository;
import uz.ciasev.ubdd_service.service.execution.BillingEntity;
import uz.ciasev.ubdd_service.service.execution.BillingEntityService;
import uz.ciasev.ubdd_service.service.execution.ExecutionCallbackService;
import uz.ciasev.ubdd_service.service.execution.PaymentsData;
import uz.ciasev.ubdd_service.service.invoice.InvoiceActionService;
import uz.ciasev.ubdd_service.service.resolution.compensation.CompensationActionService;
import uz.ciasev.ubdd_service.service.resolution.compensation.CompensationService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionActionService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;
import uz.ciasev.ubdd_service.service.resolution.punishment.PunishmentActionService;
import uz.ciasev.ubdd_service.service.status.StatusService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static uz.ciasev.ubdd_service.entity.invoice.InvoiceDeactivateReasonAlias.DECISION_SEND_TO_MIB;
import static uz.ciasev.ubdd_service.entity.status.AdmStatusAlias.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MibExecutionServiceImpl implements MibExecutionService {

    private final String MIB_EXECUTOR_NAME = OrganAlias.MIB.name();

    private final DecisionService decisionService;
    private final DecisionActionService decisionActionService;
    private final StatusService admStatusService;
    private final InvoiceActionService invoiceService;
    private final EvidenceDecisionRepository evidenceDecisionRepository;
    private final CompensationService compensationService;
    private final CompensationActionService compensationActionService;
    private final PunishmentActionService punishmentService;
    private final ExecutionCallbackService executionCallbackService;
    private final EvidenceDecisionMibRepository evidenceDecisionMibRepository;
    private final AdmEventService admEventService;
    private final BillingEntityService billingEntityService;

    @Override
    public Pair<Long, Long> getPaidDataForDecision(MibCardMovement movement) {
        Punishment punishment = movement.getCard().getDecision().getMainPunishment();
        return getPaidData(punishment);
    }

    @Override
    public Pair<Long, Long> getPaidDataCompensation(MibCardMovement movement) {
        MibExecutionCard card = movement.getCard();
        Compensation compensation = compensationService.findById(card.getCompensationId());
        return getPaidData(compensation);
    }

    @Override
    @Transactional
    public Pair<Long, Long> registerDecisionInMib(MibCardMovement movement) {
        decisionActionService.saveStatus(movement.getCard().getDecision(), SEND_TO_MIB);

        Punishment punishment = movement.getCard().getDecision().getMainPunishment();
        PenaltyPunishment penalty = punishment.getPenalty();

//        MibExecutionCard card = movement.getCard();
//        card.setAmountOfRecovery(penalty.getAmount() - penalty.getPaidAmount());
//        cardRepository.create(card);

            invoiceService.closeForPenalty(
                    penalty,
                    DECISION_SEND_TO_MIB,
                    List.of(movement.getMibCaseNumber())
            );

        return getPaidData(punishment);
    }

    @Override
    @Transactional
    public Pair<Long, Long> registerCompensationInMib(MibCardMovement movement) {
        MibExecutionCard card = movement.getCard();
        Compensation compensation = compensationService.findById(card.getCompensationId());

        admStatusService.addStatus(compensation, SEND_TO_MIB);
        compensationActionService.refreshStatusAndSave(compensation);

//        card.setAmountOfRecovery(compensation.getAmount() - compensation.getPaidAmount());
//        cardRepository.create(card);

        if (compensation.getInvoice() != null) {
//            invoiceService.closePermanently(
            invoiceService.close(
                    compensation.getInvoice(),
                    DECISION_SEND_TO_MIB,
                    List.of(movement.getMibCaseNumber())
            );
        }

        return getPaidData(compensation);
    }

    @Override
    @Transactional
    public void returnEvidenceFromMib(MibCardMovement movement) {
        setEvidenceStatuses(movement, RETURN_FROM_MIB);
    }

    @Override
    @Transactional
    public void returnDecisionFromMib(MibCardMovement movement) {

        Decision decision = movement.getCard().getDecision();
        admStatusService.cancelStatus(decision, AdmStatusAlias.SEND_TO_MIB);
        decisionActionService.saveStatus(decision, RETURN_FROM_MIB);

        decision.getPenalty().ifPresent(invoiceService::openForPenalty);

        admEventService.fireEvent(AdmEventType.MIB_RETURN_DECISION, decision);
    }

    @Override
    @Transactional
    public void returnCompensationFromMib(MibCardMovement movement) {

        Compensation compensation = compensationService.findById(movement.getCard().getCompensationId());
        admStatusService.cancelStatus(compensation, AdmStatusAlias.SEND_TO_MIB);
        admStatusService.addStatus(compensation, RETURN_FROM_MIB);
        compensationActionService.refreshStatusAndSave(compensation);

        Optional.ofNullable(compensation.getInvoice())
                .ifPresent(invoiceService::open);
    }

    @Override
    @Transactional
    public void executeEvidenceByMib(MibCardMovement movement) {
        setEvidenceStatuses(movement, EXECUTED);
    }

    @Override
    @Transactional
    public void executeDecisionByMib(MibCardMovement movement) {
        LocalDate lastPayDate = movement.getLastPayDate().orElseGet(LocalDate::now);

        Decision decision = movement.getCard().getDecision();
        admStatusService.cancelStatus(decision, AdmStatusAlias.SEND_TO_MIB);

        Punishment punishment = decision.getMainPunishment();
        punishmentService.addExecution(punishment, ExecutorType.MIB_EXECUTOR, MIB_EXECUTOR_NAME, new ForceExecutionDTO(lastPayDate, ForceExecutionType.MIB));

        executionCallbackService.executeCallback(decision);
    }

    @Override
    @Transactional
    public void executeCompensationByMib(MibCardMovement movement) {
        LocalDate lastPayDate = movement.getLastPayDate().orElse(LocalDate.now());

        Compensation compensation = compensationService.findById(movement.getCard().getCompensationId());

        admStatusService.cancelStatus(compensation, AdmStatusAlias.SEND_TO_MIB);
        compensationActionService.setExecution(compensation, List.of(MIB_EXECUTOR_NAME), new ForceExecutionDTO(lastPayDate, ForceExecutionType.MIB));

        executionCallbackService.executeCallback(decisionService.getById(compensation.getDecisionId()));
    }

    @Override
    public void cancelExecuteEvidenceByMib(MibCardMovement movement) {
        cancelEvidenceStatuses(movement, IN_EXECUTION_PROCESS, EXECUTED, RETURN_FROM_MIB);
    }

    @Override
    public void cancelExecuteDecisionByMib(MibCardMovement movement) {
        Decision decision = movement.getCard().getDecision();
        Punishment punishment = decision.getMainPunishment();
        punishmentService.deleteExecution(punishment, ExecutorType.MIB_EXECUTOR, MIB_EXECUTOR_NAME, ForceExecutionType.MIB);

        admStatusService.cancelStatus(decision, RETURN_FROM_MIB, EXECUTED, IN_EXECUTION_PROCESS);
        executionCallbackService.executeCallbackWithoutLazy(decision);

    }

    @Override
    public void cancelExecuteCompensationByMib(MibCardMovement movement) {
        Compensation compensation = compensationService.findById(movement.getCard().getCompensationId());

        admStatusService.cancelStatus(compensation, RETURN_FROM_MIB);
        compensationActionService.deleteExecution(compensation, MIB_EXECUTOR_NAME, ForceExecutionType.MIB);

        Decision decision = decisionService.getById(compensation.getDecisionId());
        admStatusService.cancelStatus(decision, EXECUTED, IN_EXECUTION_PROCESS);
        executionCallbackService.executeCallbackWithoutLazy(decision);
    }

    private Pair<Long, Long> getPaidData(BillingEntity billingEntity) {

        Optional<PaymentsData> paymentsDate = billingEntityService.getInvoicePaymentsData(billingEntity);

        Long billingPaidAmount = paymentsDate.map(PaymentsData::getTotalAmount).orElse(0l);
        Long amountOfRecovery = billingEntity.getAmount() - billingEntity.getPaidAmount();

        return Pair.of(amountOfRecovery, billingPaidAmount);
    }


    private void setEvidenceStatuses(MibCardMovement movement, AdmStatusAlias statusAlias, AdmStatusAlias... canceledStatuses) {

        List<Long> decisionsIds = evidenceDecisionMibRepository.findActivesByCard(movement.getCardId());

        for (Long id : decisionsIds) {
            EvidenceDecision ed = evidenceDecisionRepository.findById(id).get();
            admStatusService.cancelStatus(ed, canceledStatuses);
            admStatusService.setStatus(ed, statusAlias);
            evidenceDecisionRepository.save(ed);
        }
    }

    private void cancelEvidenceStatuses(MibCardMovement movement, AdmStatusAlias... canceledStatuses) {

        List<Long> decisionsIds = evidenceDecisionMibRepository.findActivesByCard(movement.getCardId());

        for (Long id : decisionsIds) {
            EvidenceDecision ed = evidenceDecisionRepository.findById(id).get();
            admStatusService.cancelStatus(ed, canceledStatuses);
            ed.setStatus(admStatusService.getStatus(ed));
            evidenceDecisionRepository.save(ed);
        }
    }
}