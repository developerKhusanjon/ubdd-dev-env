package uz.ciasev.ubdd_service.exception.signature;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.ErrorCode;

public class UserAlreadyHasAliveDigitalSignatureError extends ApplicationException {

    public UserAlreadyHasAliveDigitalSignatureError() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.USER_ALREADY_HAS_ALIVE_SIGNATURE_CERTIFICATE_ERROR);
    }
}
