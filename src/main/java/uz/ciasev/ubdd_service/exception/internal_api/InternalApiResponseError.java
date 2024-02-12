package uz.ciasev.ubdd_service.exception.internal_api;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.mvd_core.api.internal.InternalApiServiceAlias;

public class InternalApiResponseError extends InternalApiBaseError {

    public InternalApiResponseError(InternalApiServiceAlias service, String message) {
        super(
                HttpStatus.INTERNAL_SERVER_ERROR,
                service,
                "SRV_RESPONSE_OBTAINMENT_ERROR",
                message
        );
    }
}
