package uz.ciasev.ubdd_service.mvd_core.api.signature.exception;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.exception.ApplicationException;

public class SignatureApiException extends ApplicationException {

    public SignatureApiException(HttpStatus status, String code) {
        super(status, code);
    }

    public SignatureApiException(HttpStatus status, String code, String message) {
        super(status, code, message);
    }
}
