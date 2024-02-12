package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class CourtServiceUnavailableException extends ApplicationException {

    public CourtServiceUnavailableException(String message) {
        super(HttpStatus.SERVICE_UNAVAILABLE, ErrorCode.COURT_SERVICE_UNAVAILABLE, message);
    }
}
