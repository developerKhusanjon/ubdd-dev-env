package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class CourtUnauthorizedException extends ApplicationException {

    public CourtUnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.COURT_API_UNAUTHORIZED, message);
    }
}
