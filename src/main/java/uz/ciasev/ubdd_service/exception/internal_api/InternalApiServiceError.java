package uz.ciasev.ubdd_service.exception.internal_api;

import org.springframework.http.HttpStatus;

public class InternalApiServiceError extends InternalApiBaseError {

    public InternalApiServiceError(HttpStatus status, String code, String message) {
        super(
                status,
                code,
                message
        );
    }
}
