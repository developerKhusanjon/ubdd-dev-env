package uz.ciasev.ubdd_service.service.publicapi.eventdata;

import com.fasterxml.jackson.databind.JsonNode;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.invoice.Payment;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.resolution.execution.ExecutorType;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.service.publicapi.dto.eventdata.*;

public interface PublicApiWebhookEventDataService {

    JsonNode convertToJson(PublicApiWebhookEventDataDTO dto);

    PublicApiWebhookEventDataMibExecutionDTO makeMibExecutionDTO(MibCardMovement movement);

    PublicApiWebhookEventDataInvoiceDTO makeInvoiceDTO(Invoice invoice);

    PublicApiWebhookEventDataPaymentDTO makePaymentDTO(Payment payment, Invoice invoice, Violator violator);

    PublicApiWebhookEventDataAdmCaseStatusDTO makeAdmCaseStatusDTO(AdmCase admCase);

    PublicApiWebhookEventDataDecisionStatusDTO makeDecisionStatusDTO(Decision decision);

    PublicApiWebhookEventDataPunishmentStatusDTO makePunishmentStatusDTO(Punishment punishment, ExecutorType executorType);

    PublicApiWebhookEventDataCompensationStatusDTO makeCompensationStatusDTO(Compensation compensation);

    PublicApiWebhookEventDataDecisionDTO makeDecisionDTO(Decision decision);
}
