package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class MibRequestIdAlreadyExists extends ApplicationException {

    public MibRequestIdAlreadyExists() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.MIB_REQUEST_ID_ALREADY_EXISTS);

    }
}
