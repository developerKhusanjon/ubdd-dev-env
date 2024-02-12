package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class ResolutionInAdmCaseAlreadyExists extends ApplicationException {

    public ResolutionInAdmCaseAlreadyExists() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.DUPLICATE_REQUEST_EXCEPTION);
    }
}
