package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class CourtTooManyRequestsException extends ApplicationException {

    public CourtTooManyRequestsException(String message) {
        super(HttpStatus.TOO_MANY_REQUESTS, ErrorCode.COURT_API_TOO_MANY_REQUESTS, message);
    }
}
