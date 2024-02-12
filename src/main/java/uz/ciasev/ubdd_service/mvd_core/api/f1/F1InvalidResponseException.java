package uz.ciasev.ubdd_service.mvd_core.api.f1;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.exception.ErrorCode;

public class F1InvalidResponseException extends F1ApplicationException {

    public F1InvalidResponseException(String message) {
        super(HttpStatus.SERVICE_UNAVAILABLE, ErrorCode.F1_SERVICE_RESPONSE_FORMAT_ERROR, message);
    }
}
