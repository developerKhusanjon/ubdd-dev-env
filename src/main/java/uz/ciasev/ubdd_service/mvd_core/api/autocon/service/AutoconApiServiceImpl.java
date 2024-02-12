package uz.ciasev.ubdd_service.mvd_core.api.autocon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import uz.ciasev.ubdd_service.mvd_core.api.ApiService;
import uz.ciasev.ubdd_service.mvd_core.api.autocon.dto.AutoconDebtorResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.autocon.dto.AutoconResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.autocon.dto.AutoconCloseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.autocon.dto.AutoconOpenDTO;
import uz.ciasev.ubdd_service.exception.api.ApiClientError;
import uz.ciasev.ubdd_service.exception.api.ApiError;
import uz.ciasev.ubdd_service.exception.api.ApiResponseFormatInvalid;
import uz.ciasev.ubdd_service.exception.api.ApiServerError;

import java.util.Map;

@Service
public class AutoconApiServiceImpl implements AutoconApiService {

    private static String LOGIN_URI = "/login";
    private static String ADD_URI = "/debt";
    private static String DELETE_URI = "/debt";
    private static Integer SUCCESS_CODE = 200;

    private final String loginUrl;
    private final String addUrl;
    private final String deleteUrl;

    private final RestTemplate autoconRestTemplate;
    private final AutoconTokenManagerService authService;
    private final String login;
    private final String password;

    @Autowired
    public AutoconApiServiceImpl(@Qualifier("autoconRestTemplate") RestTemplate autoconRestTemplate,
                                 AutoconTokenManagerService authService,
                                 @Value("autocon-api.host") String host,
                                 @Value("autocon-api.login") String login,
                                 @Value("autocon-api.password") String password) {
        this.autoconRestTemplate = autoconRestTemplate;
        this.authService = authService;
        this.login = login;
        this.password = password;

        loginUrl = host + LOGIN_URI;
        addUrl = host + ADD_URI;
        deleteUrl = host + DELETE_URI;
    }

    @Override
    public void addDebtor(AutoconOpenDTO requestBody) {
        request(
                HttpMethod.POST,
                addUrl,
                new HttpEntity<>(requestBody, getHeaders()),
                AutoconDebtorResponseDTO.class
        );
    }

    @Override
    public void deleteDebtor(AutoconCloseDTO requestBody) {
        request(
                HttpMethod.DELETE,
                deleteUrl,
                new HttpEntity<>(requestBody, getHeaders()),
                AutoconDebtorResponseDTO.class
        );
    }

    @Override
    public String getToken() {
        ResponseEntity<AutoconResponseDTO> response = request(
                HttpMethod.POST,
                loginUrl,
                new HttpEntity<>(Map.of(
                        "username", login,
                        "password", password
                )),
                AutoconResponseDTO.class
        );

        HttpHeaders headers = response.getHeaders();

        String token = headers.getFirst("Token");

        if (token == null) {
            throw new ApiResponseFormatInvalid(ApiService.AUTOCON, "TOKEN_FETCH_ERROR", response.getBody().getMessage());
        }

        return token;
    }

    private <T extends AutoconResponseDTO> ResponseEntity<T> request(HttpMethod method, String url, HttpEntity requestBody, Class<T> responseType) {
        ResponseEntity<T> response;

        try {
            response = autoconRestTemplate.exchange(url, method, requestBody, responseType);
        } catch (HttpClientErrorException e) {
            throw new ApiClientError(ApiService.AUTOCON, e);
        } catch (HttpServerErrorException e) {
            throw new ApiServerError(ApiService.AUTOCON, e);
        } catch (RestClientException e) {
            throw new ApiError(ApiService.AUTOCON, e);
        }

        AutoconResponseDTO responseDTO = response.getBody();

        if (responseDTO == null) {
            throw new ApiResponseFormatInvalid(ApiService.AUTOCON, "RESPONSE_IS_EMPTY");
        }

        if (!SUCCESS_CODE.equals(responseDTO.getStatus())) {
            throw new ApiResponseFormatInvalid(ApiService.AUTOCON, "REQUEST_CUSTOM_EXCEPTION", responseDTO.getMessage());
        }

        return response;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + authService.getToken(this::getToken));
        return headers;
    }

}
