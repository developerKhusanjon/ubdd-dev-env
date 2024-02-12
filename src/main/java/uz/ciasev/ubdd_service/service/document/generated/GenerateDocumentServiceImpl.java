package uz.ciasev.ubdd_service.service.document.generated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.document.DocumentGenerationLog;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovementReturnRequest;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.settings.OrganInfo;
import uz.ciasev.ubdd_service.service.invoice.InvoiceService;
import uz.ciasev.ubdd_service.service.notification.sms.SmsNotificationService;
import uz.ciasev.ubdd_service.service.pdf.PdfFile;
import uz.ciasev.ubdd_service.service.pdf.PdfService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;
import uz.ciasev.ubdd_service.service.violator.ViolatorDetailService;

@Service
@RequiredArgsConstructor
public class GenerateDocumentServiceImpl implements GenerateDocumentService {

    private final PdfService pdfService;
    private final DecisionService decisionService;
    private final ProtocolService protocolService;
    private final InvoiceService invoiceService;
    private final ViolatorDetailService violatorDetailService;
    private final SmsNotificationService smsNotificationService;
    private final DocumentGenerationLogService documentGenerationLogService;

    private void logJson(AdmEntity entity, Object jsonModel, DocumentAutoGenerationEventType eventType) {

        documentGenerationLogService.createAndUpdateLog(entity, jsonModel, eventType);
    }

    private void logJson(DocumentGenerationLog documentGenerationLog, Object jsonModel) {

        documentGenerationLogService.updateLog(documentGenerationLog, jsonModel);
    }

    @Override
    public byte[] getDecision(Long decisionId, DocumentAutoGenerationEventType eventType) {

        DocumentGenerationLog documentGenerationLog = documentGenerationLogService.createLog(decisionService.getById(decisionId), eventType);

        PdfFile document = pdfService.getDecision(decisionId, documentGenerationLog);

        logJson(documentGenerationLog, document.getModel());

        return document.getContent();
    }

    @Override
    public byte[] getInvoiceForCourt(Long invoiceId, DocumentAutoGenerationEventType eventType) {

        PdfFile document = pdfService.getInvoiceForCourt(invoiceId);

        logJson(invoiceService.findById(invoiceId), document.getModel(), eventType);

        return document.getContent();
    }

    @Override
    public byte[] getProtocol(Long protocolId, DocumentAutoGenerationEventType eventType) {

        PdfFile document = pdfService.getProtocol(protocolId);

        logJson(protocolService.findById(protocolId), document.getModel(), eventType);

        return document.getContent();
    }

    @Override
    public byte[] getDecisionMail(Decision decision, OrganInfo organInfo, String number, DocumentAutoGenerationEventType eventType) {

        PdfFile document = pdfService.getDecisionMail(decision, organInfo, number);

        logJson(decision, document.getModel(), eventType);

        return document.getContent();
    }

    @Override
    public byte[] getMibPresentMail(Decision decision, OrganInfo organInfo, String number, DocumentAutoGenerationEventType eventType) {

        PdfFile document = pdfService.getMibPresentMail(decision, organInfo, number);

        logJson(decision, document.getModel(), eventType);

        return document.getContent();
    }

    @Override
    public byte[] getPersonCard(Long violatorDetailId, DocumentAutoGenerationEventType eventType) {

        PdfFile document = pdfService.getPersonCard(violatorDetailId);

        logJson(violatorDetailService.findById(violatorDetailId), document.getModel(), eventType);

        return document.getContent();
    }

    @Override
    public byte[] getPayments(Long invoiceId, DocumentAutoGenerationEventType eventType) {

        PdfFile document = pdfService.getPayments(invoiceId);

        logJson(invoiceService.findById(invoiceId), document.getModel(), eventType);

        return document.getContent();
    }

    @Override
    public byte[] getSms(Long smsNotificationId, DocumentAutoGenerationEventType eventType) {

        PdfFile document = pdfService.getSms(smsNotificationId);

        logJson(smsNotificationService.getById(smsNotificationId), document.getModel(), eventType);

        return document.getContent();
    }

    @Override
    public byte[] getMibReturnRequest(MibCardMovementReturnRequest returnRequest, DocumentAutoGenerationEventType eventType) {
        PdfFile document = pdfService.getMibReturnRequest(returnRequest.getId());

        logJson(returnRequest, document.getModel(), eventType);

        return document.getContent();
    }
}
