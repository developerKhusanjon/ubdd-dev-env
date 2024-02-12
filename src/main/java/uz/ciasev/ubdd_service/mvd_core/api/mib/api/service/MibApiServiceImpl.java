package uz.ciasev.ubdd_service.mvd_core.api.mib.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import uz.ciasev.ubdd_service.mvd_core.api.JsonApiHelper;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.request.CourtMibCardMovementSubscribeRequestApiDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.request.ReturnRequestApiDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.response.MibApiResponse2DTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.response.MibApiResponseDTOI;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.exception.*;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.types.MibResult;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.response.MibSverkaResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.envelop.MibApiResponseWrapper;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.envelop.MibSendDecisionRequestApiDTO;
import uz.ciasev.ubdd_service.entity.dict.mib.MibSendStatus;
import uz.ciasev.ubdd_service.service.dict.mib.MibSendStatusService;

import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class MibApiServiceImpl implements MibApiService {

    private final String SEND_DOC_URI = "api/app/send-doc";

    private final String SEND_ENVELOPE_URI = "api/app/send-envelope";

    private final String GET_CASE_URI = "api/app/get-actions";

    private final String SEND_DOC_RETURN_REQUEST = "api/app/get-return";

    private final String baseUrl;

    private final String token;

    private final RestTemplate restTemplate;

    private final MibSendStatusService mibSendStatusService;

    private final JsonApiHelper jsonApiHelper;

    private final ObjectMapper objectMapper;

    public MibApiServiceImpl(MibSendStatusService mibSendStatusService,
                             JsonApiHelper jsonApiHelper,
                             @Qualifier("mibRestTemplate") RestTemplate restTemplate,
                             @Value("${mib-api.base-url}") String baseUrl,
                             @Value("${mib-api.token}") String token, ObjectMapper objectMapper) {
        this.baseUrl = baseUrl;
        this.token = token;
        this.restTemplate = restTemplate;
        this.mibSendStatusService = mibSendStatusService;
        this.jsonApiHelper = jsonApiHelper;
        this.objectMapper = objectMapper;
    }

    @Override
    public MibResult sendExecutionCard(Long cardId, MibSendDecisionRequestApiDTO requestDTO) {
        return makeRequest(cardId, SEND_DOC_URI, requestDTO);

    }


    @Override
    public MibResult sendSubscribeOnCourtEnvelope(Long cardId, CourtMibCardMovementSubscribeRequestApiDTO requestDTO) {
        return makeRequest(cardId, SEND_ENVELOPE_URI, requestDTO);
    }

    @Override
    public MibSverkaResponseDTO getMibCase(Long cardId, String serial, String number) {
        return makeRequest(
                cardId,
                GET_CASE_URI,
                Map.of("protocolSeries", serial, "protocolNumber", number),
                MibSverkaResponseDTO.class
        );
    }

    @Override
    public MibResult sendExecutionCardReturnRequest(Long cardId, ReturnRequestApiDTO requestDTO) {
//        return makeRequest(cardId, SEND_DOC_RETURN_REQUEST, requestDTO);
        MibApiResponse2DTO responseBody = makeRequest(cardId, SEND_DOC_RETURN_REQUEST, requestDTO, MibApiResponse2DTO.class);
        return buildMibResult(responseBody);
    }

    private MibResult makeRequest(Long cardId, String uri, Object requestBodyDTO) {
        MibApiResponseWrapper responseBody = makeRequest(cardId, uri, requestBodyDTO, MibApiResponseWrapper.class);

        return buildMibResult(responseBody.getResult());
//        if (Optional.ofNullable(responseBody.getResult()).map(MibApiResponseResultDTO::getResultCode).isEmpty()) {
//            throw new MibApiEmptyResultCodeException();
//        }
//
//        MibApiResponseResultDTO mibResponse = responseBody.getResult();
//        MibSendStatus status = mibSendStatusService.getOrCreate(mibResponse.getResultCode(), mibResponse.getResultMsg());
//
//        return MibResult.builder()
//                .status(status)
//                .requestId(mibResponse.getEnvelopeId())
//                .message(mibResponse.getResultMsg())
//                .build();
    }

    private <T> T makeRequest(Long cardId, String uri, Object requestBodyDTO, Class<T> responseType) {
        JsonNode requestBody = objectMapper.convertValue(requestBodyDTO, JsonNode.class);

        String url = String.format("%s/%s?cardId=%s", baseUrl, uri, cardId);
        HttpEntity<JsonNode> request = new HttpEntity<>(
                requestBody,
                jsonApiHelper.buildHeadersWithBasicToken(token)
        );

        T responseBody;

        try {
            log.debug("MIB API METHOD {} : ATTEMPT {}", uri, cardId);
            responseBody = restTemplate.postForObject(
                    url,
                    request,
                    responseType
            );
        } catch (ResourceAccessException e) {
            log.error("MIB API METHOD {} : ResourceAccessException {} , {}", uri, cardId, e.getMessage(), e);

            Throwable cause = e.getCause();
            if (cause instanceof SocketTimeoutException) {
                throw new MibApiSocketTimeoutException((SocketTimeoutException) cause);
            }
            if (cause instanceof HttpHostConnectException) {
                throw new MibApiHttpHostConnectException((HttpHostConnectException) cause);
            }
            if (cause instanceof ConnectTimeoutException) {
                throw new MibApiConnectTimeoutException((ConnectTimeoutException) cause);
            }

            throw new MibApiRestClientException(e);
        } catch (HttpStatusCodeException e) {
            log.error("MIB API METHOD {} : HttpStatusCodeException {} , {}", uri, cardId, e.getMessage(), e);
            throw new MibApiResponseHttpStatusCodeException(e);
        } catch (RestClientException e) {
            log.error("MIB API METHOD {} : RestClientException {} , {}", uri, cardId, e.getMessage(), e);
            throw new MibApiRestClientException(e);
        } catch (Exception e) {
            log.error("MIB API METHOD {} : Exception {} , {}", uri, cardId, e.getMessage(), e);
            throw new MibApiException(e);
        }

        if (responseBody == null) {
            throw new MibApiEmptyResultCodeException();
        }

        return responseBody;
    }

    private MibResult buildMibResult(MibApiResponseDTOI mibResponse) {

        if (Optional.ofNullable(mibResponse).map(MibApiResponseDTOI::getResultCode).isEmpty()) {
            throw new MibApiEmptyResultCodeException();
        }

        MibSendStatus status = mibSendStatusService.getOrCreate(mibResponse.getResultCode(), mibResponse.getResultMsg());

        return MibResult.builder()
                .status(status)
                .requestId(mibResponse.getEnvelopeId())
                .message(mibResponse.getResultMsg())
                .build();
    }

}
