package uz.ciasev.ubdd_service.exception.security;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.ErrorCode;

public class UserDeactivatedException extends ApplicationException {

    public UserDeactivatedException() {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.USER_DEACTIVATED);
    }
}
