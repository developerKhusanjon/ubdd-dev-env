package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class EarlyMibSendException extends ApplicationException {

    public EarlyMibSendException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.EARLY_MIB_SEND);
    }
}
