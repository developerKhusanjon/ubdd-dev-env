package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class BarcodeGenerationError extends ApplicationException {
    public BarcodeGenerationError(String code, String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, code, message);
    }
}
