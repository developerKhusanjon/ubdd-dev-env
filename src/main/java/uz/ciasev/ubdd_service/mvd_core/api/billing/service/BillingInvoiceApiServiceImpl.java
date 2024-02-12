package uz.ciasev.ubdd_service.mvd_core.api.billing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;
import org.springframework.web.util.UriComponentsBuilder;
import uz.ciasev.ubdd_service.mvd_core.api.JsonApiHelper;
import uz.ciasev.ubdd_service.mvd_core.api.billing.BillingClientApplicationException;
import uz.ciasev.ubdd_service.mvd_core.api.billing.BillingApplicationException;
import uz.ciasev.ubdd_service.mvd_core.api.billing.BillingInvalidResponseException;
import uz.ciasev.ubdd_service.mvd_core.api.billing.BillingServerApplicationException;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.BillingInvoiceAmountRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.request.BiInvoiceDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.request.ContractDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.request.PayerDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.response.BiInvoiceResponseDTO;

import java.util.List;
import java.util.function.Function;

@Slf4j
@Service
public class BillingInvoiceApiServiceImpl implements BillingInvoiceApiService {
    private final String billingToken;
    private final String billingBaseUrl;
    private final RestTemplate restTemplate;
    private final JsonApiHelper jsonApiHelper;
    private final ObjectMapper objectMapper;

    @Autowired
    public BillingInvoiceApiServiceImpl(@Value("${billing-api.host}") String host,
                                        @Value("${billing-api.token}") String billingToken,
                                        @Qualifier("billingRestTemplate") RestTemplate restTemplate,
                                        JsonApiHelper jsonApiHelper,
                                        ObjectMapper objectMapper) {
        this.billingToken = billingToken + ":";
        this.restTemplate = restTemplate;
        this.billingBaseUrl = host + "/v2";
        this.jsonApiHelper = jsonApiHelper;
        this.objectMapper = objectMapper;
    }

    /**
     * 1.1 Create payer
     */
    @Override
    public PayerDTO createPayer(PayerDTO payerDTO) {
        ResponseEntity<PayerDTO> response = request(
                "/payer",
                HttpMethod.POST,
                payerDTO,
                PayerDTO.class);

        PayerDTO payer = response.getBody();

        if (payer == null) throw new BillingInvalidResponseException("Create payer response body empty");

        return payer;
    }

    /**
     * 1.2 Create contract
     */
    @Override
    public ContractDTO createContract(ContractDTO contractDTO) {

        ResponseEntity<ContractDTO> response = request(
                "/contract",
                HttpMethod.POST,
                contractDTO,
                ContractDTO.class);

        ContractDTO contract = response.getBody();

        if (contract == null) throw new BillingInvalidResponseException("Create contract response body is empty");

        return contract;
    }

    /**
     * 1.2 Create invoice
     */
    @Override
    public BiInvoiceResponseDTO createInvoice(BiInvoiceDTO invoiceDTO) {

        ResponseEntity<BiInvoiceResponseDTO> response = request(
                "/invoice",
                HttpMethod.POST,
                invoiceDTO,
                BiInvoiceResponseDTO.class);

        return response.getBody();
    }

    @Override
    public ContractDTO getContractByNumber(String number) {

        var builder = UriComponentsBuilder
                .fromUriString("/contract")
                .queryParam("number", number);

        ResponseEntity<ContractDTO> response = request(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                ContractDTO.class);

        var responseDTO = response.getBody();

        return responseDTO;
    }

    /**
     * 1.7 Cancel invoice
     */
    @Override
    public void cancelInvoice(Long invoiceId, String declineReason) {

        var builder = UriComponentsBuilder
                .fromUriString("/invoice/reject")
                .queryParam("id", invoiceId)
                .queryParam("cancel_reason", declineReason);

        ResponseEntity<Void> response = request(
                builder.toUriString(),
                HttpMethod.DELETE,
                null,
                Void.class,
                e -> e.getMessage().contains("Invoice is already closed!") //  При попытке открыть открытый инвойс болинг кидает ошибку.
        );
    }

    @Override
    public void cancelInvoiceBatch(List<Long> ids, String reason) {

        ResponseEntity<String> response = request(
                "/invoice/reject/batch",
                HttpMethod.DELETE,
                ids,
                String.class);

//        if (!response.getStatusCode().equals(HttpStatus.ACCEPTED)) {
//            log.error("CANCEL BATCH INVOICES {}", response);
//            throw new BillingInvalidResponseException(response);
//        }
    }

    /**
     * Recover invoice
     */
    @Override
    public void openInvoice(Long invoiceId) {

        var builder = UriComponentsBuilder
                .fromUriString("/invoice/open")
                .queryParam("id", invoiceId);

        //todo как то обрабатывать ошибку уже открыто
        ResponseEntity<Void> response = request(
                builder.toUriString(),
                HttpMethod.PUT,
                null,
                Void.class,
                e -> e.getMessage().contains("Invoice is open") //  При попытке открыть открытый инвойс болинг кидает ошибку.
        );
    }

    /**
     * Update invoice
     */
    @Override
    public void updateInvoice(String invoiceSerial, BillingInvoiceAmountRequestDTO invoiceAmount) {

        ResponseEntity<String> response = request(
                "/invoice/" + invoiceSerial,
                HttpMethod.PUT,
                invoiceAmount,
                String.class
        );

        if (!response.getStatusCode().equals(HttpStatus.OK))
            throw new BillingInvalidResponseException(response);
    }

    @Override
    public BiInvoiceResponseDTO getInvoiceBySerial(String invoiceSerial) {

        ResponseEntity<BiInvoiceResponseDTO> response = request(
                "/invoice/" + invoiceSerial,
                HttpMethod.GET,
                null,
                BiInvoiceResponseDTO.class);

        if (response.getBody() == null) throw new BillingInvalidResponseException(response);

        return response.getBody();
    }

    private <T> ResponseEntity<T> request(String uri, HttpMethod method, Object body, Class<T> responseType) {
        return request(uri, method, body, responseType, e -> false);
    }

    @Data
    static class ErrorResponseBody {
        private String message;
    }

    private String getErrorMessage(HttpStatusCodeException error) {
        try {
            ErrorResponseBody errorResponseBody = objectMapper.readValue(error.getResponseBodyAsString(), ErrorResponseBody.class);
            return errorResponseBody.getMessage();
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }

    private <T> ResponseEntity<T> request(String uri,
                                          HttpMethod method,
                                          Object body,
                                          Class<T> responseType,
                                          Function<HttpClientErrorException, Boolean> clientErrorPreHandler) {

        String url = billingBaseUrl + uri;
        HttpHeaders headers = jsonApiHelper.buildHeadersWithBasicToken(billingToken);
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<T> response = restTemplate.exchange(
                    url,
                    method,
                    entity,
                    responseType);

            return response;

        } catch (HttpClientErrorException e) {
            if (clientErrorPreHandler.apply(e)) {
                return ResponseEntity.ok(null);
            }
            log.error("BILLING CLIENT ERROR {} FOR {} ON \"{}\"", getErrorMessage(e), method, uri, e);
            throw new BillingClientApplicationException(e);
        } catch (HttpServerErrorException e) {
            log.error("BILLING SERVER ERROR FOR {} ON \"{}\"", method, uri, e);
            throw new BillingServerApplicationException(e);
        } catch (RestClientException e) {
            log.error("BILLING APPLICATION ERROR FOR {} ON \"{}\"", method, uri, e);
            throw new BillingApplicationException(e);
        }
    }
}
