package uz.ciasev.ubdd_service.mvd_core.api.billing;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.ErrorCode;

public class BillingApplicationException extends ApplicationException {

    public BillingApplicationException(Exception e) {
        super(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.BILLING_SERVICE_UNAVAILABLE,
                String.format("%s: %s", e.getClass().getSimpleName(), e.getMessage())
        );
    }

    protected BillingApplicationException(HttpStatus status, String code, String message) {
        super(status, code, message);
    }
}
