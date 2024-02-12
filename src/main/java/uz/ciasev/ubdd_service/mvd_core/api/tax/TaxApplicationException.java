package uz.ciasev.ubdd_service.mvd_core.api.tax;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.exception.ApplicationException;

public class TaxApplicationException extends ApplicationException {

    public TaxApplicationException(HttpStatus status, String code, String message) {
        super(status, code, message);
    }
}
