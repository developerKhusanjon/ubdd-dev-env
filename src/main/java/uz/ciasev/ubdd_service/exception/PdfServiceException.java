package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class PdfServiceException extends ApplicationException {

    public PdfServiceException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.PDF_SERVICE_ERROR);
    }
}
