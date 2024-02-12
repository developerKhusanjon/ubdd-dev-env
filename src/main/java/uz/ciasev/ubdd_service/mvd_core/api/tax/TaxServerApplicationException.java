package uz.ciasev.ubdd_service.mvd_core.api.tax;

import org.springframework.http.HttpStatus;

public class TaxServerApplicationException extends TaxApplicationException {

    public TaxServerApplicationException(String code, String message) {
        super(HttpStatus.SERVICE_UNAVAILABLE, code, message);
    }
}
