package uz.ciasev.ubdd_service.mvd_core.api.billing;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uz.ciasev.ubdd_service.exception.ErrorCode;

public class BillingInvalidResponseException extends BillingApplicationException {

    public BillingInvalidResponseException(String message) {
        super(HttpStatus.SERVICE_UNAVAILABLE, ErrorCode.BILLING_SERVICE_INVALID_RESPONSE, message);
    }

    public BillingInvalidResponseException(ResponseEntity<?> response) {
        this(String.format("Billing API response %s: [%s]", response.getStatusCodeValue(), response.getBody()));
    }
}
