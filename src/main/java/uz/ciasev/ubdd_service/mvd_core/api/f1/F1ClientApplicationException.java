package uz.ciasev.ubdd_service.mvd_core.api.f1;

import org.springframework.http.HttpStatus;

public class F1ClientApplicationException extends F1ApplicationException {

    public F1ClientApplicationException(String code, String message) {
        super(HttpStatus.BAD_REQUEST, code, message);
    }
}
