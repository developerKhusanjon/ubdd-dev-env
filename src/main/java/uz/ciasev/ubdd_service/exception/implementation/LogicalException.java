package uz.ciasev.ubdd_service.exception.implementation;

import uz.ciasev.ubdd_service.exception.ErrorCode;

public class LogicalException extends ImplementationException {

    public LogicalException(String message) {
        super(ErrorCode.LOGIC_ERROR, message);
    }
}
