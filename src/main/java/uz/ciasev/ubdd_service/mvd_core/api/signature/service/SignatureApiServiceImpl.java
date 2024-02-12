package uz.ciasev.ubdd_service.mvd_core.api.signature.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;
import uz.ciasev.ubdd_service.mvd_core.api.JsonApiHelper;
import uz.ciasev.ubdd_service.mvd_core.api.signature.dto.*;
import uz.ciasev.ubdd_service.mvd_core.api.signature.exception.SignatureApiClientException;
import uz.ciasev.ubdd_service.mvd_core.api.signature.exception.SignatureApiException;
import uz.ciasev.ubdd_service.mvd_core.api.signature.exception.SignatureApiServerException;
import uz.ciasev.ubdd_service.exception.ErrorCode;


@Service
@Slf4j
public class SignatureApiServiceImpl implements SignatureApiService {

    private final String token;
    private final String baseUrl;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final JsonApiHelper jsonApiHelper;

    @Autowired
    public SignatureApiServiceImpl(@Value("${signature-api.login}") String login,
                                   @Value("${signature-api.password}") String password,
                                   @Value("${signature-api.host}") String host,
                                   RestTemplate restTemplate, ObjectMapper objectMapper, JsonApiHelper jsonApiHelper) {
        this.objectMapper = objectMapper;
        this.token = login + ":" + password;
        this.baseUrl = host + "/api";
        this.restTemplate = restTemplate;
        this.jsonApiHelper = jsonApiHelper;
    }

    @Override
    public CertificateCreateResponseDTO createCertificate(CertificateCreateRequestDTO requestDTO) {
        return makeRequest(
                "/key/",
                HttpMethod.POST,
                requestDTO,
                CertificateCreateResponseDTO.class
        ).getBody();
    }

    @Override
    public CertificateDetailResponseDTO getCertificate(String serial) {
        return makeRequest(
                String.format("/key/%s", serial),
                HttpMethod.GET,
                null,
                CertificateDetailResponseDTO.class
        ).getBody();
    }

    @Override
    public void pauseCertificate(String serial, String reason) {
        makeRequest(
                String.format("/key/%s/pause", serial),
                HttpMethod.PUT,
                new CertificateEditRequestDTO(reason),
                Void.class
        );
    }

    @Override
    public void resumeCertificate(String serial, String reason) {
        makeRequest(
                String.format("/key/%s/resume", serial),
                HttpMethod.PUT,
                new CertificateEditRequestDTO(reason),
                Void.class
        );
    }

    @Override
    public void revokeCertificate(String serial, String reason) {
        makeRequest(
                String.format("/key/%s/revoke", serial),
                HttpMethod.PUT,
                new CertificateEditRequestDTO(reason),
                Void.class
        );
    }

    @Override
    public boolean verifySignatureByCertificate(String serial, String digest, String signature) {
        return makeRequest(
                String.format("/key/%s/verify", serial),
                HttpMethod.POST,
                new CheckSignatureRequestDTO(digest, signature),
                CheckSignatureResponseDTO.class
        ).getBody().getIsVerified();
    }

    @Override
    public String extractSignature(String signature) {
        return makeRequest(
                "/key/extract",
                HttpMethod.POST,
                new CheckSignatureRequestDTO(null, signature),
                CheckSignatureRequestDTO.class
        ).getBody().getSignature();
    }

    @Override
    public String makeDigestOfString(String data) {
        return makeRequest(
                "/tools/digest",
                HttpMethod.POST,
                data,
                String.class
        ).getBody();
    }

    private <T> ResponseEntity<T> makeRequest(String uri,
                                              HttpMethod method,
                                              Object body,
                                              Class<T> responseType) {

        String url = baseUrl + uri;
        HttpHeaders headers = jsonApiHelper.buildHeadersWithBasicToken(token);
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);

        entity.toString();

        try {
            ResponseEntity<T> response = restTemplate.exchange(
                    url,
                    method,
                    entity,
                    responseType);

            return response;

        } catch (HttpClientErrorException e) {
            log.error("SIGNATURE API ERROR (CLIENT) FOR URI \"{}\" : {}", url, e.getMessage());
            ErrorSignatureApiResponseDTO response = getErrorCode(e);
            //throw new SignatureApiClientException(HttpStatus.INTERNAL_SERVER_ERROR, response.getCode(), response.getMessage());
            throw new SignatureApiClientException(HttpStatus.BAD_REQUEST, response.getCode(), response.getMessage());
        } catch (HttpServerErrorException e) {
            log.error("SIGNATURE API ERROR (SERVER) FOR URI \"{}\" : {}", url, e.getMessage());
            throw new SignatureApiServerException(HttpStatus.SERVICE_UNAVAILABLE, ErrorCode.SIGNATURE_API_SERVER_ERROR, e.getMessage());
        } catch (RestClientException e) {
            log.error("SIGNATURE API ERROR FOR URI \"{}\" : {}", url, e.getMessage());
            throw new SignatureApiException(HttpStatus.SERVICE_UNAVAILABLE, ErrorCode.SIGNATURE_API_CONNECTION_ERROR, e.getMessage());
        }
    }

    private ErrorSignatureApiResponseDTO getErrorCode(HttpStatusCodeException error) {
        try {
            return objectMapper.readValue(error.getResponseBodyAsString(), ErrorSignatureApiResponseDTO.class);
        } catch (Exception e) {
            log.error("SIGNATURE API ERROR (PARSE) FOR RESPONSE \"{}\" : {}", error.getResponseBodyAsString(), e.getMessage());
            return new ErrorSignatureApiResponseDTO(ErrorCode.SIGNATURE_API_UNKNOWN_ERROR, error.getResponseBodyAsString());
        }
    }
}
