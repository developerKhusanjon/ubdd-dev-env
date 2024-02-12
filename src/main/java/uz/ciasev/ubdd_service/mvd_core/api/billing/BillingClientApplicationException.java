package uz.ciasev.ubdd_service.mvd_core.api.billing;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import uz.ciasev.ubdd_service.exception.ErrorCode;

public class BillingClientApplicationException extends BillingResponseStatusException {

    public BillingClientApplicationException(HttpClientErrorException e) {
        super(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.BILLING_ARGUMENTS_NOT_VALID,
                e
        );
    }
}
