package uz.ciasev.ubdd_service.service.pdf;

import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.invoice.PdfInvoiceForCourtDTO;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.karor_mail.PdfDecisionDTO;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.karor_mail.PdfMailDTO;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.karor_mail.PdfSmsDTO;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.mib.PdfReturnRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.payment.PdfPaymentWrapperDTO;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.person_card.PdfPersonCardForCourtDTO;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.protocol.PdfProtocolDTO;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.requirement.PdfRequirementDTO;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.termination.PdfTerminationDTO;
import uz.ciasev.ubdd_service.entity.invoice.InvoiceOwnerTypeAlias;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolArticlesProjection;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolRequirementProjection;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.settings.OrganInfo;
import uz.ciasev.ubdd_service.entity.document.DocumentGenerationLog;
import uz.ciasev.ubdd_service.entity.document.RequirementGeneration;

import java.util.List;
import java.util.function.Supplier;

public interface PdfModelBuilderService {

    PdfMailDTO buildMailModel(Decision decision, OrganInfo organInfo, String number);

    PdfDecisionDTO buildPenaltyDecisionModel(Decision decision, DocumentGenerationLog documentGenerationLog);

    PdfTerminationDTO buildTerminationDecisionModel(Decision decision, DocumentGenerationLog documentGenerationLog);

    PdfProtocolDTO buildProtocolModel(Long protocolId);

    PdfRequirementDTO buildRequirementModel(RequirementGeneration registration, Supplier<List<ProtocolRequirementProjection>> protocolsSupplier, Supplier<List<ProtocolArticlesProjection>> articlesSupplier);

    PdfPersonCardForCourtDTO buildPersonCardModel(Long violatorDetailId);

    PdfInvoiceForCourtDTO buildInvoiceModel(Long invoiceId);

    PdfPaymentWrapperDTO buildPaymentModel(Long invoiceId);

    PdfPaymentWrapperDTO buildViolatorPaymentModel(Long violatorId, InvoiceOwnerTypeAlias invoiceOwnerTypeAlias);

    PdfSmsDTO buildSmsModel(Long smsNotificationId);

    PdfReturnRequestDTO buildMibReturnRequestModel(Long returnRequestId);
}
