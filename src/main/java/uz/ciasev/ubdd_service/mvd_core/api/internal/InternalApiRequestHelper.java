package uz.ciasev.ubdd_service.mvd_core.api.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.*;
import uz.ciasev.ubdd_service.exception.internal_api.*;
import uz.ciasev.ubdd_service.exception.internal_api.mapping.InternalApiResponseMappingError;
import uz.ciasev.ubdd_service.exception.internal_api.mapping.InternalApiResponseProcessingError;
import uz.ciasev.ubdd_service.exception.internal_api.resource_access.*;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.stream.Collectors;


public class InternalApiRequestHelper {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final InternalApiServiceAlias service;

    public InternalApiRequestHelper(RestTemplate restTemplate,
                                    ObjectMapper objectMapper,
                                    InternalApiServiceAlias serviceAlias) {
        restTemplate.setErrorHandler(new Error());

        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.service = serviceAlias;
    }

    public <T> T sendGetRequest(String url, Class<T> clazz) {
        String responseBody = fetchGetResponseString(url);

        try {
            return objectMapper.readValue(responseBody, clazz);

        } catch (JsonMappingException e) {
            throw new InternalApiResponseMappingError(service, e.getMessage());

        } catch (JsonProcessingException e) {
            throw new InternalApiResponseProcessingError(service, e.getMessage());

        }
    }

    public <T> List<T> sendGetListRequest(String url, Class<T> clazz) {
        String responseBody = fetchGetResponseString(url);

        try {
            return objectMapper.readValue(responseBody, new TypeReference<List<JsonNode>>() {
                    })
                    .stream()
                    .map(object -> objectMapper.convertValue(object, clazz))
                    .collect(Collectors.toList());

        } catch (JsonProcessingException e) {
            throw new InternalApiResponseProcessingError(service, e.getMessage());

        }
    }

    public void sendHeadRequest(String url) {
        fetchResponse(url, Void.class, HttpMethod.HEAD);
    }

    private String fetchGetResponseString(String url) {
        ResponseEntity<String> response = fetchResponse(url, String.class, HttpMethod.GET);

        return response.getBody();
    }

    private <T> ResponseEntity<T> fetchResponse(String url, Class<T> clazz, HttpMethod method) {
        ResponseEntity<T> response;

        try {
            response = restTemplate.exchange(url, method, RequestEntity.EMPTY, clazz);
//            RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(clazz);
//            ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(clazz);
//            response = restTemplate.execute(url, method, requestCallback, responseExtractor);

        } catch (ResourceAccessException e) {
            Throwable cause = e.getCause();

            if (cause instanceof SocketTimeoutException) {
                throw new InternalApiSocketTimeoutError(service, cause.getMessage());
            }
            if (cause instanceof HttpHostConnectException) {
                throw new InternalApiHostConnectError(service, cause.getMessage());
            }
            if (cause instanceof ConnectTimeoutException) {
                throw new InternalApiConnectTimeoutError(service, cause.getMessage());
            }

            throw new InternalApiResourceAccessError(service, e.getMessage());

        } catch (RestClientException e) {
            throw new InternalApiResponseError(service, e.getMessage());
        }

        return response;
    }

    @Data
    private static class ApiErrorResponseDTO {
        private String code;
        private String message;
    }

    private class Error implements ResponseErrorHandler {
        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return !response.getStatusCode().is2xxSuccessful();
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            String responseTest = IOUtils.toString(response.getBody());

            try {
                ApiErrorResponseDTO error = objectMapper.readValue(responseTest, ApiErrorResponseDTO.class);
                if (error.getCode() != null && !error.getCode().isBlank()) {
                    throw new InternalApiServiceError(response.getStatusCode(), error.getCode(), error.getMessage());
                }
            } catch (JsonProcessingException ignored) {}

            throw new InternalApiStatusCodeError(service, response.getStatusCode(), responseTest);
        }
    }


}
