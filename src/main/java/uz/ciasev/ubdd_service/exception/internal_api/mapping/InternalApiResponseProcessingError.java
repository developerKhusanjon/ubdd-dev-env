package uz.ciasev.ubdd_service.exception.internal_api.mapping;

import uz.ciasev.ubdd_service.mvd_core.api.internal.InternalApiServiceAlias;

public class InternalApiResponseProcessingError extends InternalApiMappingBaseError {

    public InternalApiResponseProcessingError(InternalApiServiceAlias service, String message) {
        super(
                service,
                "SRV_RESPONSE_PROCESSING_ERROR",
                message
        );
    }
}
