package uz.ciasev.ubdd_service.exception.internal_api.resource_access;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.mvd_core.api.internal.InternalApiServiceAlias;
import uz.ciasev.ubdd_service.exception.internal_api.InternalApiBaseError;

public class InternalApiStatusCodeError extends InternalApiBaseError {

    public InternalApiStatusCodeError(InternalApiServiceAlias service, HttpStatus statusCode, String message) {
        super(
                statusCode.is4xxClientError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.SERVICE_UNAVAILABLE,
                service,
                String.format("SRV_RETURNED_%d_STATUS_CODE", statusCode.value()),
                message
        );
    }
}
