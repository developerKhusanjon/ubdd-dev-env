package uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.service.protocol;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatusAlias;
import uz.ciasev.ubdd_service.entity.mib.PaymentData;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.execution.ExecutorType;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecutionDTO;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecutionType;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.exception.MibNoAccessOnResolutionException;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.execution.MibPaymentDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.execution.MibRequestDTO;
import uz.ciasev.ubdd_service.service.execution.ExecutionCallbackService;
import uz.ciasev.ubdd_service.service.execution.ManualBillingService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;
import uz.ciasev.ubdd_service.service.resolution.punishment.PunishmentActionService;
import uz.ciasev.ubdd_service.service.status.StatusService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static uz.ciasev.ubdd_service.entity.status.AdmStatusAlias.EXECUTED;
import static uz.ciasev.ubdd_service.entity.status.AdmStatusAlias.IN_EXECUTION_PROCESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class MibApiAdmServiceImpl implements MibApiAdmService {


    private final DecisionService decisionService;
    private final PunishmentActionService punishmentService;
    private final ExecutionCallbackService executionCallbackService;
    private final ManualBillingService manualBillingService;
    private final StatusService admStatusService;

    @Override
    @Transactional(timeout = 90)
    public void execution(Long decisionId, MibRequestDTO result) {

        Decision decision = decisionService.getById(decisionId);

        if (!decision.getResolution().getOrgan().isMib()) {
            throw new MibNoAccessOnResolutionException(decision.getResolution());
        }

        MibCaseStatusAlias caseStatus = result.getExecutionCaseStatus().getAlias();
        if (MibCaseStatusAlias.TERMINATION.equals(caseStatus)) {
            cancelExecution(decision);
            return;
        }

        List<PaymentData> payments = result.getPayments().stream().map(MibPaymentDTO::buildPaymentData).collect(Collectors.toList());
        LocalDate lastPayDate = result.getPayments().stream().map(MibPaymentDTO::getPaymentTime).map(LocalDateTime::toLocalDate).max(Comparator.naturalOrder()).orElse(LocalDate.now());

        Punishment punishment = decision.getMainPunishment();

        manualBillingService.replaceMibPayments(punishment, payments);
        punishmentService.addExecution(punishment, ExecutorType.MIB_ORGAN, "IIB", new ForceExecutionDTO(lastPayDate, ForceExecutionType.MIB));
        executionCallbackService.executeCallback(decision);

    }

    private void cancelExecution(Decision decision) {

        Punishment punishment = decision.getMainPunishment();

        manualBillingService.deleteMibPayments(punishment);
        punishmentService.deleteExecution(decision.getMainPunishment(), ExecutorType.MIB_ORGAN, "IIB", ForceExecutionType.MIB);

        admStatusService.cancelStatus(decision, EXECUTED, IN_EXECUTION_PROCESS);
        admStatusService.cancelStatus(decision.getResolution(), EXECUTED, IN_EXECUTION_PROCESS);

        executionCallbackService.executeCallbackWithoutLazy(decision);
    }

}
