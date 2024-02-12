package uz.ciasev.ubdd_service.exception.internal_api.resource_access;

import uz.ciasev.ubdd_service.mvd_core.api.internal.InternalApiServiceAlias;

public class InternalApiConnectTimeoutError extends InternalApiBaseResourceAccessError {

    public InternalApiConnectTimeoutError(InternalApiServiceAlias service, String message) {
        super(
                service,
                "SRV_CONNECT_TIMEOUT_ERROR",
                message
        );
    }
}
