package uz.ciasev.ubdd_service.mvd_core.api.mib.api.exception;

import uz.ciasev.ubdd_service.exception.ErrorCode;

public class MibApiEmptyResultCodeException extends MibApiApplicationException {

    public MibApiEmptyResultCodeException() {
        super(ErrorCode.MIB_API_RETURN_EMPTY_RESULT_CODE, "EMI: Mib service response does not contain 'resultCode' field");
    }
}
