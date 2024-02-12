package uz.ciasev.ubdd_service.exception.internal_api.resource_access;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.mvd_core.api.internal.InternalApiServiceAlias;
import uz.ciasev.ubdd_service.exception.internal_api.InternalApiBaseError;

public class InternalApiBaseResourceAccessError extends InternalApiBaseError {

    public InternalApiBaseResourceAccessError(InternalApiServiceAlias service, String code, String message) {
        super(
                HttpStatus.SERVICE_UNAVAILABLE,
                service,
                code,
                message
        );
    }
}
