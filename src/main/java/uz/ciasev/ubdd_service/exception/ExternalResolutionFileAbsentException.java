package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class ExternalResolutionFileAbsentException extends ApplicationException {

    public ExternalResolutionFileAbsentException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.EXTERNAL_RESOLUTION_FILE_ABSENT);
    }
}
