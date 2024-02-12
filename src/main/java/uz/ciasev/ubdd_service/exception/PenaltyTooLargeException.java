package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class PenaltyTooLargeException extends ApplicationException {

    public PenaltyTooLargeException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.PENALTY_TOO_LARGE);
    }
}
