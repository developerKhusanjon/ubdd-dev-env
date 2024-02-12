package uz.ciasev.ubdd_service.service.publicapi.eventdata;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.invoice.Payment;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.resolution.execution.ExecutorType;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.exception.implementation.LogicalException;
import uz.ciasev.ubdd_service.service.publicapi.dto.eventdata.*;
import uz.ciasev.ubdd_service.service.utils.EntityViolatorService;

import java.util.Map;
import java.util.Optional;

import static uz.ciasev.ubdd_service.entity.status.AdmStatusAlias.*;

@Service
@RequiredArgsConstructor
public class PublicApiWebhookEventDataServiceImpl implements PublicApiWebhookEventDataService {

    private final ObjectMapper objectMapper;
    private final EntityViolatorService entityViolatorService;


    /**
     * Значения гаишников:
     * 21 - Штраф оплачен
     * 22 - Штраф оплачен со скидкой
     * 23 - Взыскано МИБом
     * 24 - Завершено в административном суде
     * 25 - Другое
     * **/
    private final Map<ExecutorType, Map<AdmStatusAlias, Integer>> punishmentGaiStatusMap = Map.of(
            ExecutorType.BILLING_WITH_DISCOUNT, Map.of(
                    DECISION_MADE, 25,
                    IN_EXECUTION_PROCESS, 25,
                    EXECUTED, 22),
            ExecutorType.BILLING, Map.of(
                    DECISION_MADE, 25,
                    IN_EXECUTION_PROCESS, 25,
                    EXECUTED, 21),
            ExecutorType.MIB_EXECUTOR, Map.of(
                    DECISION_MADE, 25,
                    IN_EXECUTION_PROCESS, 25,
                    EXECUTED, 23),
            ExecutorType.COURT_EXECUTION_BY_MATERIAL, Map.of(
                    DECISION_MADE, 25,
                    IN_EXECUTION_PROCESS, 25,
                    EXECUTED, 24),
            ExecutorType.MANUAL_EXECUTION, Map.of(
                    DECISION_MADE, 25,
                    IN_EXECUTION_PROCESS, 25,
                    EXECUTED, 24),
            ExecutorType.SYSTEM_AUTO_EXECUTION, Map.of(
                    DECISION_MADE, 25,
                    IN_EXECUTION_PROCESS, 25,
                    EXECUTED, 24)
    );

    @Override
    public JsonNode convertToJson(PublicApiWebhookEventDataDTO dto) {
        return objectMapper.convertValue(dto, JsonNode.class);
    }

    @Override
    public PublicApiWebhookEventDataMibExecutionDTO makeMibExecutionDTO(MibCardMovement movement) {

        PublicApiWebhookEventDataMibExecutionDTO rsl = new PublicApiWebhookEventDataMibExecutionDTO();
        rsl.setSendTime(movement.getSendTime());
        rsl.setMibCaseNumber(movement.getMibCaseNumber());
        rsl.setMibCaseStatusId(movement.getMibCaseStatusId());
        rsl.setMibRequestId(movement.getMibRequestId());
        rsl.setSendStatusId(movement.getSendStatusId());
        rsl.setSendMessage(movement.getSendMessage());

        MibExecutionCard card = movement.getCard();

        rsl.setMibExecutionCardId(card.getId());
//        rsl.setRegionId(card.getRegionId());
//        rsl.setDistrictId(card.getDistrictId());
        rsl.setRegionId(movement.getRegionId());
        rsl.setDistrictId(movement.getDistrictId());
        rsl.setDecisionId(card.getDecisionId());
        rsl.setCompensationId(card.getCompensationId());
//        rsl.setPayments(card.getPayments());
        rsl.setPayments(movement.getPayments());


        return rsl;
    }

    @Override
    public PublicApiWebhookEventDataInvoiceDTO makeInvoiceDTO(Invoice invoice) {

        PublicApiWebhookEventDataInvoiceDTO rsl = new PublicApiWebhookEventDataInvoiceDTO();

        rsl.setInvoiceId(invoice.getId());
        rsl.setActive(invoice.isActive());
        rsl.setDeactivateReasonAlias(Optional.ofNullable(invoice.getDeactivateReason()).map(Enum::name).orElse(null));
        rsl.setDeactivateReasonText(invoice.getDeactivateReasonDesc());
        rsl.setDeactivateTime(invoice.getDeactivateTime());

        return rsl;
    }

    @Override
    public PublicApiWebhookEventDataPaymentDTO makePaymentDTO(Payment payment, Invoice invoice, Violator violator) {

        PublicApiWebhookEventDataPaymentDTO rsl = new PublicApiWebhookEventDataPaymentDTO();

        rsl.setViolatorId(violator.getId());
        rsl.setInvoiceId(invoice.getId());

        rsl.setSupplierType(invoice.getOwnerTypeId().shortValue());
        rsl.setSupplierDate(payment.getPaymentTime());
        rsl.setAmount(payment.getAmount());

        rsl.setBlankNumber(payment.getBlankNumber());
        rsl.setBlankDate(String.valueOf(payment.getBlankDate()));

        rsl.setPayerBank(payment.getFromBankName());
        rsl.setPayerBankInn(payment.getFromInn());
        rsl.setPayerBankMfo(payment.getFromBankCode());
        rsl.setPayerBankAccount(payment.getFromBankAccount());

        rsl.setPayeeBank(payment.getToBankName());
        rsl.setPayeeBankInn(payment.getToInn());
        rsl.setPayeeBankMfo(payment.getToBankCode());
        rsl.setPayeeBankAccount(payment.getToBankAccount());

//        rsl.setFullyPaid(fullyPaid);

        return rsl;
    }

    @Override
    public PublicApiWebhookEventDataAdmCaseStatusDTO makeAdmCaseStatusDTO(AdmCase admCase) {
        PublicApiWebhookEventDataAdmCaseStatusDTO rsl = new PublicApiWebhookEventDataAdmCaseStatusDTO();
//        rsl.setAdmCaseId(admCase.getId());
        rsl.setStatusId(admCase.getStatusId());
        return rsl;
    }

    @Override
    public PublicApiWebhookEventDataDecisionStatusDTO makeDecisionStatusDTO(Decision decision) {
        PublicApiWebhookEventDataDecisionStatusDTO rsl = new PublicApiWebhookEventDataDecisionStatusDTO();
//        rsl.setAdmCaseId(decision.getResolution().getAdmCaseId());
        rsl.setDecisionId(decision.getId());
        rsl.setViolatorId(decision.getViolatorId());
        rsl.setStatusId(decision.getStatusId());
        return rsl;
    }

    @Override
    public PublicApiWebhookEventDataPunishmentStatusDTO makePunishmentStatusDTO(Punishment punishment, ExecutorType executorType) {
        Integer changeReasonId = Optional.ofNullable(punishmentGaiStatusMap)
                .map(m -> m.get(executorType))
                .map(m -> m.get(punishment.getStatus().getAlias()))
                .orElseThrow(() -> new LogicalException(String.format("No found gai status for %s %s", punishment.getStatus().getAlias(), executorType)));

        PublicApiWebhookEventDataPunishmentStatusDTO rsl = new PublicApiWebhookEventDataPunishmentStatusDTO();
//        rsl.setAdmCaseId(entityAdmCaseService.idBy(punishment));
        rsl.setDecisionId(punishment.getDecisionId());
        rsl.setViolatorId(entityViolatorService.idBy(punishment));
        rsl.setPunishmentId(punishment.getId());
        rsl.setIsMain(punishment.isMain());
        rsl.setStatusId(punishment.getStatusId());
        rsl.setChangeReasonId(changeReasonId);
        return rsl;
    }

    @Override
    public PublicApiWebhookEventDataCompensationStatusDTO makeCompensationStatusDTO(Compensation compensation) {
        PublicApiWebhookEventDataCompensationStatusDTO rsl = new PublicApiWebhookEventDataCompensationStatusDTO();
//        rsl.setAdmCaseId(entityAdmCaseService.idBy(compensation));
        rsl.setDecisionId(compensation.getDecisionId());
        rsl.setViolatorId(entityViolatorService.idBy(compensation));
        rsl.setCompensationId(compensation.getId());
        rsl.setStatusId(compensation.getStatusId());
        return rsl;
    }

    @Override
    public PublicApiWebhookEventDataDecisionDTO makeDecisionDTO(Decision decision) {
        PublicApiWebhookEventDataDecisionDTO rsl = new PublicApiWebhookEventDataDecisionDTO();
//        rsl.setAdmCaseId(decision.getResolution().getAdmCaseId());
        rsl.setDecisionId(decision.getId());
        rsl.setViolatorId(decision.getViolatorId());
        return rsl;
    }
}
