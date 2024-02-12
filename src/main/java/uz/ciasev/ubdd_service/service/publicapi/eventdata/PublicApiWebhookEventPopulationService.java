package uz.ciasev.ubdd_service.service.publicapi.eventdata;

import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.invoice.Payment;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.execution.ExecutorType;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.service.publicapi.dto.eventdata.PublicApiWebhookEventDataCourtDTO;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedResolutionDTO;

import java.util.List;
import java.util.function.Supplier;

public interface PublicApiWebhookEventPopulationService {

    void addSendToMibEvent(MibCardMovement movement);

    void addMibAcceptEvent(MibCardMovement movement);

    void addMibPaidEvent(MibCardMovement movement);

    void addValidationMibEvent(MibCardMovement movement);

    void addMibReturnEvent(MibCardMovement movement);

    void addMibExecuteEvent(MibCardMovement movement);

    void addMibCancelExecutionEvent(MibCardMovement movement);

    void addCourtEvent(PublicApiWebhookEventDataCourtDTO courtEventDTO);

    void addInvoiceEvent(Invoice invoice);

    void addPaymentEvent(Payment payment, Invoice invoice, Violator violator);

    void addAdmCaseStatusEvent(AdmCase admCase);

    void addDecisionStatusEvent(Decision decision);

    void addPunishmentStatusEvent(Punishment punishment, ExecutorType punishmentExecutorType);

    void addCompensationStatusEvent(Compensation compensation);

    void addDecisionMadeEvent(Decision decision);

    void addDecisionsMadeEvent(CreatedResolutionDTO createdResolution);

    void addDecisionsMadeEvent(AdmCase admCase, Supplier<List<Decision>> decisionSupplier);

    void addDecisionCancelEvent(Decision decision);

}
