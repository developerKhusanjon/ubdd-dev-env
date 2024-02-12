package uz.ciasev.ubdd_service.exception.internal_api.resource_access;

import uz.ciasev.ubdd_service.mvd_core.api.internal.InternalApiServiceAlias;

public class InternalApiHostConnectError extends InternalApiBaseResourceAccessError {

    public InternalApiHostConnectError(InternalApiServiceAlias service, String message) {
        super(
                service,
                "SRV_HOST_CONNECT_ERROR",
                message
        );
    }
}
