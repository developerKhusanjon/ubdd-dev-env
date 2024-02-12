package uz.ciasev.ubdd_service.mvd_core.api.mib.api.exception;

import uz.ciasev.ubdd_service.exception.ErrorCode;

public class MibApiCaseStatusEmptyException extends MibApiApplicationException {

    public MibApiCaseStatusEmptyException() {
        super(ErrorCode.MIB_CASE_STATUS_IS_EMPTY, "Mib case status is null");
    }
}
