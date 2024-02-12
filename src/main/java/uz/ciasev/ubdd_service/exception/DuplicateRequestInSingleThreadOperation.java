package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class DuplicateRequestInSingleThreadOperation extends ApplicationException {

    public DuplicateRequestInSingleThreadOperation() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.REQUESTED_OPERATION_CURRENTLY_IN_EXECUTION);
    }
}
