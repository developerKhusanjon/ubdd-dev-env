package uz.ciasev.ubdd_service.exception.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;
import uz.ciasev.ubdd_service.mvd_core.api.ApiService;

public class ApiError extends ApiBaseError {

    public ApiError(ApiService service, RestClientException e) {
        super(
                HttpStatus.SERVICE_UNAVAILABLE,
                service,
                "CONNECTION_ERROR",
                e.getMessage()
        );
    }
}
