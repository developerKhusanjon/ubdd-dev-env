package uz.ciasev.ubdd_service.mvd_core.api.billing;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;
import uz.ciasev.ubdd_service.exception.ErrorCode;

public class BillingServerApplicationException extends BillingResponseStatusException {

    public BillingServerApplicationException(HttpServerErrorException e) {
        super(HttpStatus.SERVICE_UNAVAILABLE, ErrorCode.BILLING_SERVICE_UNAVAILABLE, e);
    }
}
