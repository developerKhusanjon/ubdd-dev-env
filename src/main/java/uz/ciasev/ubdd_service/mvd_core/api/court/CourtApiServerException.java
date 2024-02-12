package uz.ciasev.ubdd_service.mvd_core.api.court;

import uz.ciasev.ubdd_service.exception.ErrorCode;

public class CourtApiServerException extends CourtApiException {

    public CourtApiServerException(Exception e) {
        super(ErrorCode.COURT_API_SERVER_ERROR, e.getMessage());
    }
}
