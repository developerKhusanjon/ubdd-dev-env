package uz.ciasev.ubdd_service.mvd_core.api.mib.api.exception;

import uz.ciasev.ubdd_service.exception.ErrorCode;

public class MibApiRestClientException extends MibApiApplicationException {

    public MibApiRestClientException(Throwable cause) {
        super(ErrorCode.MIB_API_REST_CLIENT_ERROR,
                String.format("%s: %s", cause.getClass().getSimpleName(), cause.getMessage()),
                cause);
    }
}
