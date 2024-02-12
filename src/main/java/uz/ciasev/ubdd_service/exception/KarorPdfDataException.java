package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class KarorPdfDataException extends ApplicationException {

    public KarorPdfDataException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.KAROR_PDF_DATA);
    }
}
