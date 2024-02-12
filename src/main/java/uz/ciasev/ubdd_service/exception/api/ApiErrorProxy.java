package uz.ciasev.ubdd_service.exception.api;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.exception.ApplicationException;

public class ApiErrorProxy extends ApplicationException {

    public ApiErrorProxy(HttpStatus status, String code, String message) {
        super(
                status,
                code,
                message
        );
    }
}
