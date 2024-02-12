package uz.ciasev.ubdd_service.exception.settings;

import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ServerException;

public class MultipleActiveBrvException extends ServerException {

    public MultipleActiveBrvException() {
        super(ErrorCode.MULTIPLE_ACTIVE_BRV_FOUND);
    }
}
