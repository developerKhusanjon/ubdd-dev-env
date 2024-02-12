package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.entity.invoice.InvoiceDeactivateReasonAlias;

public class InvoiceDeactivatedException extends ApplicationException {

    private final InvoiceDeactivateReasonAlias deactivatedReason;

    public InvoiceDeactivatedException(InvoiceDeactivateReasonAlias deactivatedReason) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.INVOICE_DEACTIVATED);
        this.deactivatedReason = deactivatedReason;
    }
}
