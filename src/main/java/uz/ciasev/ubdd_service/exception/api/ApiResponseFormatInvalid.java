package uz.ciasev.ubdd_service.exception.api;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.mvd_core.api.ApiService;

public class ApiResponseFormatInvalid extends ApiBaseError {

    public ApiResponseFormatInvalid(ApiService service, String codeDetailPostfix, String message) {
        super(
                HttpStatus.SERVICE_UNAVAILABLE,
                service,
                codeDetailPostfix,
                message
        );
    }

    public ApiResponseFormatInvalid(ApiService service, String codeDetailPostfix) {
        this(
                service,
                codeDetailPostfix,
                null
        );
    }
}
