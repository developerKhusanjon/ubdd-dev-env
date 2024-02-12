package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class S3ReadFileException extends ApplicationException {
    public S3ReadFileException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "S_READ_FILE_ERROR", message);
    }
}
