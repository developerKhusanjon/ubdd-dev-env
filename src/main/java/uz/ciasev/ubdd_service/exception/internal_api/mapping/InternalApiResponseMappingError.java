package uz.ciasev.ubdd_service.exception.internal_api.mapping;

import uz.ciasev.ubdd_service.mvd_core.api.internal.InternalApiServiceAlias;

public class InternalApiResponseMappingError extends InternalApiMappingBaseError {

    public InternalApiResponseMappingError(InternalApiServiceAlias service, String message) {
        super(
                service,
                "SRV_RESPONSE_BODY_MAPPING_ERROR",
                message
        );
    }
}
