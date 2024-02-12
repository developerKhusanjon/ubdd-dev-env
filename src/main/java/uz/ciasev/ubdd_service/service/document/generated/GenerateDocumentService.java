package uz.ciasev.ubdd_service.service.document.generated;

import uz.ciasev.ubdd_service.entity.mib.MibCardMovementReturnRequest;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.settings.OrganInfo;

public interface GenerateDocumentService {

    byte[] getDecision(Long decisionId, DocumentAutoGenerationEventType eventType);

    byte[] getInvoiceForCourt(Long invoiceId, DocumentAutoGenerationEventType eventType);

    byte[] getProtocol(Long protocolId, DocumentAutoGenerationEventType eventType);

    byte[] getDecisionMail(Decision decision, OrganInfo organInfo, String number, DocumentAutoGenerationEventType eventType);

    byte[] getMibPresentMail(Decision decision, OrganInfo organInfo, String number, DocumentAutoGenerationEventType eventType);

    byte[] getPersonCard(Long violatorDetailId, DocumentAutoGenerationEventType eventType);

    byte[] getPayments(Long invoiceId, DocumentAutoGenerationEventType eventType);

    byte[] getSms(Long smsNotificationId, DocumentAutoGenerationEventType eventType);

    byte[] getMibReturnRequest(MibCardMovementReturnRequest returnRequest, DocumentAutoGenerationEventType eventType);
}
