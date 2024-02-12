package uz.ciasev.ubdd_service.exception.internal_api;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.mvd_core.api.internal.InternalApiServiceAlias;
import uz.ciasev.ubdd_service.exception.ApplicationException;

public class InternalApiBaseError extends ApplicationException {

    protected InternalApiBaseError(HttpStatus status, String code, String message) {
        super(
                status,
                code,
                message
        );
    }

    public InternalApiBaseError(HttpStatus status, InternalApiServiceAlias service, String code, String message) {
        this(
                status,
                String.format("%s_%s", service.getCodePrefix(), code),
                message
        );
    }
}
