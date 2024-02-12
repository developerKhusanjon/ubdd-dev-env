package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class F1PhotoTypeLengthException extends ApplicationException {

    public F1PhotoTypeLengthException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.F1_MAX_PHOTO_TYPE_LENGTH);
    }
}
