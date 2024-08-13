package uz.ciasev.ubdd_service.dto;

import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.QualificationRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.CancellationResolutionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.SingleResolutionRequestDTO;
import uz.ciasev.ubdd_service.dto.ubdd.UbddInvoiceRequest;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.BillingPaymentDTO;

public class PostOperationPatchDto {
    public ProtocolRequestDTO protocol;

    public Long protocolId;
    public QualificationRequestDTO qualification;

    public Long fromAdmCaseId;
    public Long toAdmCaseId;
    public Long separatorId;

    public SingleResolutionRequestDTO singleResolution;
    public CancellationResolutionRequestDTO cancellationResolution;

    public UbddInvoiceRequest ubddInvoice;
    public BillingPaymentDTO billingPayment;
}
