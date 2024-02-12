package uz.ciasev.ubdd_service.exception.internal_api.resource_access;

import uz.ciasev.ubdd_service.mvd_core.api.internal.InternalApiServiceAlias;

public class InternalApiResourceAccessError extends InternalApiBaseResourceAccessError {

    public InternalApiResourceAccessError(InternalApiServiceAlias service, String message) {
        super(
                service,
                "SRV_RESOURCE_ACCESS_ERROR",
                message
        );
    }
}
