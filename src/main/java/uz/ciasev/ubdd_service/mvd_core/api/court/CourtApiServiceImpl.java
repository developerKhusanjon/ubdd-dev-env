package uz.ciasev.ubdd_service.mvd_core.api.court;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.token.CourtTokenService;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.first.FirstCourtAdmCaseRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.fourth.FourthCourtPaymentDTO;
import uz.ciasev.ubdd_service.service.court.CourtLogService;

import java.net.SocketTimeoutException;

import static uz.ciasev.ubdd_service.mvd_core.api.court.CourtMethod.COURT_FIRST;

@Slf4j
@Service
public class CourtApiServiceImpl implements CourtApiService {

    private final String courtBaseUrl;
    private final RestTemplate restTemplate;
    private final CourtTokenService courtTokenService;
    private final CourtLogService courtLogService;

    public CourtApiServiceImpl(@Value("${court-api.base-url}") String courtBaseUrl,
                               @Qualifier("courtRestTemplate") RestTemplate restTemplate,
                               CourtTokenService courtTokenService, CourtLogService courtLogService) {
        this.courtBaseUrl = courtBaseUrl;
        this.restTemplate = restTemplate;
        this.courtTokenService = courtTokenService;
        this.courtLogService = courtLogService;
    }

    @Override
    public CourtSendResult sendAdmCase(CourtRequestDTO<FirstCourtAdmCaseRequestDTO> requestBody) {

        CourtSendResult res = postForCourtResult(
                "/adm/api/mvd-case/case/save?caseId=" + requestBody.getSendDocumentRequest().getCaseId(),
                requestBody
        );

        courtLogService.save(requestBody.getSendDocumentRequest().getCaseId(), COURT_FIRST, res);

        return res;
    }

    @Override
    public CourtSendResult sendInvoiceWithPayment(CourtRequestDTO<FourthCourtPaymentDTO> requestBody) {
        return postForCourtResult(
                "/adm/api/mvd-case/receipt/save",
                requestBody
        );
    }

//    @Override
//    public byte[] getResolutionFileContent(String fileUri) {
//
//        HttpEntity<CourtRequestDTO> httpEntity = new HttpEntity<>(
//                courtTokenService.getHeadersForCourt()
//        );
//
//        try {
//
//            ResponseEntity<byte[]> response = restTemplate.exchange(
//                    courtBaseUrl + fileUri,
//                    HttpMethod.GET,
//                    httpEntity,
//                    byte[].class);
//
//            return response.getBody();
//
//        } catch (HttpClientErrorException e) {
//            log.error("COURT CLIENT ERROR [EXECUTION] : {}", e.getLocalizedMessage());
//            throw new CourtApiClientException(e);
//        } catch (HttpServerErrorException e) {
//            log.error("COURT SERVER ERROR [EXECUTION] : {}", e.getLocalizedMessage());
//            throw new CourtApiServerException(e);
//        } catch (Exception e) {
//            log.error("COURT REQUEST ERROR [EXECUTION] : {}", e.getLocalizedMessage());
//            throw new CourtApiRequestException(e);
//        }
//    }

    @Override
    public byte[] getByteByUri(String uri) {

        HttpEntity<Object> httpEntity = new HttpEntity<>(courtTokenService.getHeadersForCourt());

        try {

            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(
                    courtBaseUrl + uri,
                    HttpMethod.GET,
                    httpEntity,
                    byte[].class);

            return responseEntity.getBody();

        } catch (HttpClientErrorException e) {
            log.error("COURT CLIENT ERROR: {}", e.getLocalizedMessage());
            throw new CourtApiClientException(e);
        } catch (HttpServerErrorException e) {
            log.error("COURT SERVER ERROR: {}", e.getLocalizedMessage());
            if (e.getStatusCode().value() == 504) {
                throw new CourtApiTimeoutException(e);
            }
            throw new CourtApiServerException(e);
        } catch (ResourceAccessException e) {
            log.error("COURT REQUEST ERROR: {}", e.getMessage());
            Throwable cause = e.getCause();
            if (cause instanceof SocketTimeoutException) {
                throw new CourtApiTimeoutException(e);
            }
            throw new CourtApiRequestException(e);
        } catch (RestClientException e) {
            log.error("COURT REQUEST ERROR: {}", e.getMessage());
            throw new CourtApiRequestException(e);
        } catch (Exception e) {
            log.error("COURT ERROR: {}", e.getLocalizedMessage());
            throw new CourtApiRequestException(e);
        }
    }

    private CourtSendResult postForCourtResult(String uri, CourtRequestDTO requestBody) {

        HttpEntity<CourtRequestDTO> httpEntity = new HttpEntity<>(
                requestBody,
                courtTokenService.getHeadersForCourt()
        );

        try {

            ResponseEntity<CourtResponseDTO> responseEntity = restTemplate.postForEntity(
                    courtBaseUrl + uri,
                    httpEntity,
                    CourtResponseDTO.class);

            return new CourtSendResult(responseEntity.getBody());

        } catch (HttpClientErrorException e) {
            log.error("COURT CLIENT ERROR: {}", e.getLocalizedMessage());
            throw new CourtApiClientException(e);
        } catch (HttpServerErrorException e) {
            log.error("COURT SERVER ERROR: {}", e.getLocalizedMessage());
            throw new CourtApiServerException(e);
        } catch (RestClientException e) {
            log.error("COURT REQUEST ERROR: {}", e.getMessage());
            throw new CourtApiRequestException(e);
        } catch (Exception e) {
            log.error("COURT ERROR: {}", e.getLocalizedMessage());
            throw new CourtApiRequestException(e);
        }
    }
}
