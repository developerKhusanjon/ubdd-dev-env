package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class LateMibSendException extends ApplicationException {

    public LateMibSendException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.LATE_MIB_SEND);
    }
}
