package uz.ciasev.ubdd_service.exception.implementation;

import uz.ciasev.ubdd_service.exception.ErrorCode;

public class NotImplementedException extends ImplementationException {

    public NotImplementedException(String message) {
        super(
                ErrorCode.NOT_IMPLEMENTED,
                "Not implemented action: " + message
        );
    }
}
