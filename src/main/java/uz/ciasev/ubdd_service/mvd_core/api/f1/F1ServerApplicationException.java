package uz.ciasev.ubdd_service.mvd_core.api.f1;

import org.springframework.http.HttpStatus;

public class F1ServerApplicationException extends F1ApplicationException {

    public F1ServerApplicationException(String code, String message) {
        super(HttpStatus.SERVICE_UNAVAILABLE, code, message);
    }
}
