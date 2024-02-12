package uz.ciasev.ubdd_service.mvd_core.api.f1;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.exception.ApplicationException;

public class F1ApplicationException extends ApplicationException {

    public F1ApplicationException(HttpStatus status, String code, String message) {
        super(status, code, message);
    }
}
