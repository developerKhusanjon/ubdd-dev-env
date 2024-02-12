package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class FileSizeTooLargeException extends ApplicationException {

    public FileSizeTooLargeException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.FILE_SIZE_TOO_LARGE);
    }
}
