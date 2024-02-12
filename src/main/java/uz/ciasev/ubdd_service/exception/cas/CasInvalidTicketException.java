package uz.ciasev.ubdd_service.exception.cas;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.ErrorCode;

public class CasInvalidTicketException extends ApplicationException {

    public CasInvalidTicketException(String message) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.CAS_INVALID_TICKET, message);
    }
}
