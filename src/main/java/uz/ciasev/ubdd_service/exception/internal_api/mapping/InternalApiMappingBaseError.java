package uz.ciasev.ubdd_service.exception.internal_api.mapping;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.mvd_core.api.internal.InternalApiServiceAlias;
import uz.ciasev.ubdd_service.exception.internal_api.InternalApiBaseError;

public class InternalApiMappingBaseError extends InternalApiBaseError {

    public InternalApiMappingBaseError(InternalApiServiceAlias service, String code, String message) {
        super(
                HttpStatus.INTERNAL_SERVER_ERROR,
                service,
                code,
                message
        );
    }
}
