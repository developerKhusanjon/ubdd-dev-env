package uz.ciasev.ubdd_service.mvd_core.api.signature.exception;

import org.springframework.http.HttpStatus;

public class SignatureApiServerException extends SignatureApiException {

    public SignatureApiServerException(HttpStatus status, String code) {
        super(status, code);
    }

    public SignatureApiServerException(HttpStatus status, String code, String message) {
        super(status, code, message);
    }
}
