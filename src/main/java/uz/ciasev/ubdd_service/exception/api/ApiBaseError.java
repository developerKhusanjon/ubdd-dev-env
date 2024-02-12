package uz.ciasev.ubdd_service.exception.api;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.mvd_core.api.ApiService;
import uz.ciasev.ubdd_service.exception.ApplicationException;

public class ApiBaseError extends ApplicationException {

    protected ApiBaseError(HttpStatus status, ApiService service, String codePostfix, String message) {
        super(
                status,
                String.format("%s_%s", service.name().toUpperCase(), codePostfix),
                message
        );
    }
}
