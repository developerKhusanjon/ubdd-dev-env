package uz.ciasev.ubdd_service.exception.security;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.ErrorCode;

public class IssuerNotFoundException extends ApplicationException {

    public IssuerNotFoundException() {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.ISSUER_NOT_REGISTERED);
    }
}
