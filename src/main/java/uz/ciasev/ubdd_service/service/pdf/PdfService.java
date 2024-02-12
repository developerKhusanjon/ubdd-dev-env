package uz.ciasev.ubdd_service.service.pdf;

import uz.ciasev.ubdd_service.entity.document.RequirementGeneration;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolArticlesProjection;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolRequirementProjection;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.settings.OrganInfo;
import uz.ciasev.ubdd_service.entity.document.DocumentGenerationLog;

import java.util.List;
import java.util.function.Supplier;

public interface PdfService {

    PdfFile getDecision(Long decisionId, DocumentGenerationLog documentGenerationLog);

    PdfFile getRequirement(RequirementGeneration requirementGeneration, Supplier<List<ProtocolRequirementProjection>> protocolsSupplier, Supplier<List<ProtocolArticlesProjection>> articlesSupplier);

    PdfFile getInvoiceForCourt(Long invoiceId);

    PdfFile getProtocol(Long protocolId);

    PdfFile getDecisionMail(Decision decision, OrganInfo organInfo, String number);

    PdfFile getMibPresentMail(Decision decision, OrganInfo organInfo, String number);

    byte[] getDecisionMailContent(Decision decision, OrganInfo organInfo, String number);

    byte[] getMibPresentMailContent(Decision decision, OrganInfo organInfo, String number);

    PdfFile getPersonCard(Long violatorDetailId);

    PdfFile getPayments(Long invoiceId);

    PdfFile getPenaltyPayments(Long violatorId);

    PdfFile getCompensationPayments(Long violatorId);

    PdfFile getSms(Long smsNotificationId);

    PdfFile getMibReturnRequest(Long returnRequestId);
}
