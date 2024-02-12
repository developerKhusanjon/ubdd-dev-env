package uz.ciasev.ubdd_service.mvd_core.api.court;

import org.springframework.web.client.HttpClientErrorException;
import uz.ciasev.ubdd_service.exception.ErrorCode;

public class CourtApiClientException extends CourtApiException {

    public CourtApiClientException(HttpClientErrorException e) {
        super(ErrorCode.COURT_API_CLIENT_ERROR, e.getMessage());
    }
}
