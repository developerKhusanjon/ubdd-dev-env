package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class CourtResolutionException extends ApplicationException {

    public CourtResolutionException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.COURT_RESOLUTION);
    }
}
