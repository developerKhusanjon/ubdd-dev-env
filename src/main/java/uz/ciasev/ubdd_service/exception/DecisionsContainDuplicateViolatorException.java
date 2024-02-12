package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class DecisionsContainDuplicateViolatorException extends ApplicationException {

    public DecisionsContainDuplicateViolatorException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.DECISIONS_CONTAIN_DUPLICATE_VIOLATOR);
    }
}
