package uz.ciasev.ubdd_service.mvd_core.api.mib.api.exception;

import uz.ciasev.ubdd_service.exception.ErrorCode;

public class MibApiException extends MibApiApplicationException {

    public MibApiException(Exception cause) {
        super(ErrorCode.MIB_API_ERROR, cause.getMessage(), cause);
    }
}
