package uz.ciasev.ubdd_service.mvd_core.api.billing;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class BillingResponseStatusException extends BillingApplicationException {

    public BillingResponseStatusException(HttpStatus status, String code, HttpStatusCodeException e) {
        super(
                status,
                code,
                String.format("Bulling API response %s: [%s]", e.getRawStatusCode(), e.getResponseBodyAsString())
        );
    }
}
