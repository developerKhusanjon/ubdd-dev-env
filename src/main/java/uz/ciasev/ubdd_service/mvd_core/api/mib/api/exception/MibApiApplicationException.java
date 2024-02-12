package uz.ciasev.ubdd_service.mvd_core.api.mib.api.exception;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.exception.ApplicationException;

public abstract class MibApiApplicationException extends ApplicationException {

    public String getSendResponseMessage() {
        return getMessage();
    }

    public String getSendResponseCode() {
        return "NO CODE";
    }

    protected MibApiApplicationException(String code, String message) {
        super(HttpStatus.SERVICE_UNAVAILABLE, code, message);
    }

    protected MibApiApplicationException(String code, String message, Throwable cause) {
        super(HttpStatus.SERVICE_UNAVAILABLE, code, message, cause);
    }
}
