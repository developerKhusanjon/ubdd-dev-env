package uz.ciasev.ubdd_service.mvd_core.api.texpass.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import uz.ciasev.ubdd_service.mvd_core.api.f1.service.F1Service;
import uz.ciasev.ubdd_service.dto.internal.response.F1DocumentListDTO;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class TexPassServiceImpl implements TexPassService {

    @Autowired
    @Qualifier("texPassServiceRestTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private F1Service f1Service;

    @Value("${tex-pass-api.person-url}")
    private String personUrl;

    @Override
    public List<F1DocumentListDTO> findPersonByChip(String number) {

        List<String> pinpps = getPinppList(number);

        return pinpps.stream()
                .filter(Objects::nonNull)
                .map(f1Service::findGcpInfoByPinpp)
                .map(f1Service::getF1Document)
                .collect(Collectors.toList());
    }

    @Override
    public F1DocumentListDTO getPersonByChip(String number) {
        return findPersonByChip(number)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ValidationException(ErrorCode.ID_CARD_BY_CHIP_NOT_FOUND));
    }

    private List<String> getPinppList(String number) {

        String params = String.format("?number=%s", number);

        return makeRequest(() -> {

            ResponseEntity<List<String>> response = restTemplate.exchange(personUrl + params,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<String>>() {});

            return response.getBody();

//            ResponseEntity<JsonNode> response = restTemplate.exchange(personUrl + params,
//                    HttpMethod.GET,
//                    null,
//                    JsonNode.class);
//
//            if (response.getBody().isArray() && response.getBody().size() == 0) {
//                return List.of();
//            }
//
//            return objectMapper.convertValue(response.getBody(), new TypeReference<List<String>>() {});
        });
    }

    private <T> T makeRequest(Supplier<T> request) {
        try {
            return request.get();
        } catch (HttpClientErrorException e) {
            ErrorResponseBody error = getErrorObject(e);
            throw new ValidationException(error.getCode(), error.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("TEX_PASS_SERVICE_ERROR", e.getLocalizedMessage());
        }
    }

    @Data
    static class ErrorResponseBody {
        private String code;
        private String message;
    }

    private ErrorResponseBody getErrorObject(HttpStatusCodeException error) {
        ErrorResponseBody errorResponseBody = new ErrorResponseBody();
        try {
            errorResponseBody = objectMapper.readValue(error.getResponseBodyAsString(), ErrorResponseBody.class);
        } catch (Exception e) {
            errorResponseBody.setMessage(e.getLocalizedMessage());
        }
        return errorResponseBody;
    }
}
