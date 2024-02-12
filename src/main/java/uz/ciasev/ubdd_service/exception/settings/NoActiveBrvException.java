package uz.ciasev.ubdd_service.exception.settings;

import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ServerException;

public class NoActiveBrvException extends ServerException {

    public NoActiveBrvException() {
        super(ErrorCode.NO_ACTIVE_BRV_FOUND);
    }
}
