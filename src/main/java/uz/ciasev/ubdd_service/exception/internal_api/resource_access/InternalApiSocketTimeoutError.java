package uz.ciasev.ubdd_service.exception.internal_api.resource_access;


import uz.ciasev.ubdd_service.mvd_core.api.internal.InternalApiServiceAlias;

public class InternalApiSocketTimeoutError extends InternalApiBaseResourceAccessError {

    public InternalApiSocketTimeoutError(InternalApiServiceAlias service, String message) {
        super(
                service,
                "SRV_SOCKET_TIMEOUT_ERROR",
                message
        );
    }
}
