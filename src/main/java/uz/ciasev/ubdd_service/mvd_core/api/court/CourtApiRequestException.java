package uz.ciasev.ubdd_service.mvd_core.api.court;

import uz.ciasev.ubdd_service.exception.ErrorCode;

public class CourtApiRequestException extends CourtApiException {

    public CourtApiRequestException(Exception e) {
        super(ErrorCode.COURT_API_REQUEST_ERROR, e.getMessage());
    }
}
