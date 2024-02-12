package uz.ciasev.ubdd_service.service.invoice;

import uz.ciasev.ubdd_service.entity.invoice.InvoiceDeactivateReasonAlias;

import java.util.List;

public interface InvoiceBlockDescriptionService {

    String getDescriptionByReason(InvoiceDeactivateReasonAlias reason, List<Object> params);
}
