package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class NotOldObjectException extends ApplicationException {

    public NotOldObjectException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.OBJECT_CREATED_AFTER_27_04_21);
    }
}
