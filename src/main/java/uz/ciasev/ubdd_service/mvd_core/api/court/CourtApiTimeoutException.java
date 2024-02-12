package uz.ciasev.ubdd_service.mvd_core.api.court;

import uz.ciasev.ubdd_service.exception.ErrorCode;

public class CourtApiTimeoutException extends CourtApiException {

    public CourtApiTimeoutException(Exception e) {
        super(ErrorCode.COURT_API_TIMEOUT_ERROR, e.getMessage());
    }
}
