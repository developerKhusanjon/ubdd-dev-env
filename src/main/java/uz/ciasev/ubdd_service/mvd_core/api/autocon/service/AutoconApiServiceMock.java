package uz.ciasev.ubdd_service.mvd_core.api.autocon.service;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import uz.ciasev.ubdd_service.mvd_core.api.ApiService;
import uz.ciasev.ubdd_service.mvd_core.api.autocon.dto.AutoconCloseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.autocon.dto.AutoconOpenDTO;
import uz.ciasev.ubdd_service.exception.api.ApiClientError;
import uz.ciasev.ubdd_service.exception.api.ApiError;
import uz.ciasev.ubdd_service.exception.api.ApiResponseFormatInvalid;
import uz.ciasev.ubdd_service.exception.api.ApiServerError;

import java.util.Random;

@Service
@Primary
@Profile({"local", "test"})
public class AutoconApiServiceMock implements AutoconApiService {

    private final Random random = new Random();

    @Override
    public void addDebtor(AutoconOpenDTO requestBody) {
        makeRendom();
    }

    @Override
    public void deleteDebtor(AutoconCloseDTO requestBody) {
        makeRendom();
    }

    @Override
    public String getToken() {
        return "autocon-mock-token";
    }

    private void makeRendom() {

        int r = random.nextInt() % 12;

        switch (r) {
            case 1:
            case 2:
            case 3:
            case 10:
            case 11:
                return;
            case 4:
                throw new ApiClientError(ApiService.AUTOCON, new HttpClientErrorException(HttpStatus.BAD_REQUEST));
            case 5:
            case 9:
                throw new ApiServerError(ApiService.AUTOCON, new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
            case 6:
                throw new ApiError(ApiService.AUTOCON, new RestClientException("Autocon mock connection error"));
            case 7:
                throw new ApiResponseFormatInvalid(ApiService.AUTOCON, "RESPONSE_IS_EMPTY");
            case 8:
                throw new ApiResponseFormatInvalid(ApiService.AUTOCON, "REQUEST_CUSTOM_EXCEPTION", "Autocon mock responce with code != 200");
        }
    }

}
