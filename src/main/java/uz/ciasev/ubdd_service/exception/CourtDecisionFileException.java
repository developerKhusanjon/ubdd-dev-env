package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class CourtDecisionFileException extends ApplicationException {

    public CourtDecisionFileException(String message) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.COURT_DECISION_FILE_ERROR, message);
    }
}
