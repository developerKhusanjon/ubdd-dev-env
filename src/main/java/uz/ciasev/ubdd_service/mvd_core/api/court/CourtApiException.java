package uz.ciasev.ubdd_service.mvd_core.api.court;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.exception.ApplicationException;

public class CourtApiException extends ApplicationException {

    public CourtApiException(String code, String message) {
        super(HttpStatus.SERVICE_UNAVAILABLE, code, message);
    }
}
