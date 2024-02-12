package uz.ciasev.ubdd_service.exception.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import uz.ciasev.ubdd_service.mvd_core.api.ApiService;

public class ApiClientError extends ApiBaseError {

    public ApiClientError(ApiService service, HttpClientErrorException e) {
        super(
                HttpStatus.INTERNAL_SERVER_ERROR,
                service,
                "CLIENT_ERROR",
                e.getMessage()
        );
    }
}
