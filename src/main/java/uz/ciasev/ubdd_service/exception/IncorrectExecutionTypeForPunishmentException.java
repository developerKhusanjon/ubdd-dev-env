package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class IncorrectExecutionTypeForPunishmentException extends ApplicationException {

    public IncorrectExecutionTypeForPunishmentException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.INCORRECT_EXECUTION_TYPE_FOR_PUNISHMENT);
    }
}
