package uz.ciasev.ubdd_service.exception.signature;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.ErrorCode;

public class UserHasNoActiveDigitalSignatureError extends ApplicationException {

    public UserHasNoActiveDigitalSignatureError() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.USER_HAS_NO_ACTIVE_SIGNATURE_CERTIFICATE_ERROR);
    }
}
