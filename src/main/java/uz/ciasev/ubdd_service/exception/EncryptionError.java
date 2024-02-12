package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class EncryptionError extends ApplicationException {
    public EncryptionError(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.ENCRYPTION_ERROR, message);
    }
}
