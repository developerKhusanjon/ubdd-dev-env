package uz.ciasev.ubdd_service.mvd_core.api.signature.exception;

import org.springframework.http.HttpStatus;

public class SignatureApiClientException extends SignatureApiException {

    public SignatureApiClientException(HttpStatus status, String code) {
        super(status, code);
    }

    public SignatureApiClientException(HttpStatus status, String code, String message) {
        super(status, code, message);
    }
}
