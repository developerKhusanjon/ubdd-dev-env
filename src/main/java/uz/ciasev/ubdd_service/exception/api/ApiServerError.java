package uz.ciasev.ubdd_service.exception.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;
import uz.ciasev.ubdd_service.mvd_core.api.ApiService;

public class ApiServerError extends ApiBaseError {

    public ApiServerError(ApiService service, HttpServerErrorException e) {
        super(
                HttpStatus.SERVICE_UNAVAILABLE,
                service,
                "SERVER_ERROR",
                e.getMessage()
        );
    }
}
