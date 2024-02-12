package uz.ciasev.ubdd_service.mvd_core.api.tax;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;
import uz.ciasev.ubdd_service.mvd_core.api.ApiService;
import uz.ciasev.ubdd_service.mvd_core.api.tax.dto.TaxCompanyCoreDTO;
import uz.ciasev.ubdd_service.mvd_core.api.tax.dto.TaxCompanyTaxDTO;
import uz.ciasev.ubdd_service.mvd_core.api.tax.dto.TaxPersonDTO;
import uz.ciasev.ubdd_service.exception.api.ApiErrorProxy;
import uz.ciasev.ubdd_service.exception.api.ApiClientError;
import uz.ciasev.ubdd_service.exception.api.ApiError;
import uz.ciasev.ubdd_service.exception.api.ApiServerError;

import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
@Service
public class TaxServiceImpl implements TaxService {

    private final static String taxCompanyUrl = "/api/companies";

    private final static String taxPersonUrl = "/api/persons";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${tax-api.base-url}")
    private String taxBaseUrl;

    @Override
    public String getPersonInnByPinpp(String pinpp) {

        String rsl = null;
        try {
            TaxPersonDTO dto = getPersonByPinpp(pinpp);
            rsl = dto.getInn();
        } catch (Exception e) {
            log.error("TAX ERROR FOR PINPP {}: {}", pinpp, e.getMessage());
        }
        return rsl;
    }

    @Override
    public TaxCompanyCoreDTO getJuridicByInn(String inn) {

        String companyUrl = String.format("%s%s/%s", taxBaseUrl, taxCompanyUrl, inn);

        TaxCompanyTaxDTO taxDTO = makeRequest(() -> {
            ResponseEntity<TaxCompanyTaxDTO> response = restTemplate.getForEntity(companyUrl, TaxCompanyTaxDTO.class);
            return response.getBody();
        });

        return Optional.ofNullable(taxDTO).map(TaxCompanyCoreDTO::new).orElse(null);
    }

    @Override
    public TaxPersonDTO getPersonByPinpp(String pinpp) {

        String personUrl = String.format("%s%s/%s", taxBaseUrl, taxPersonUrl, pinpp);

        return makeRequest(() -> {
            ResponseEntity<TaxPersonDTO> response = restTemplate.getForEntity(personUrl, TaxPersonDTO.class);
            return response.getBody();
        });
    }

    private <T> T makeRequest(Supplier<T> request) {
        try {
            return request.get();
        } catch (HttpClientErrorException e) {

            Optional<ErrorResponseBody> errorOpt = getErrorObject(e);
            errorOpt.ifPresent(error -> {
                throw new ApiErrorProxy(e.getStatusCode(), error.getCode(), error.getMessage());
            });

            throw new ApiClientError(ApiService.TAX, e);

        } catch (HttpServerErrorException e) {
            throw new ApiServerError(ApiService.TAX, e);
        } catch (RestClientException e) {
            throw new ApiError(ApiService.TAX, e);
        }
    }

    @Data
    static class ErrorResponseBody {
        private String code;
        private String message;
    }

    private Optional<ErrorResponseBody> getErrorObject(HttpStatusCodeException error) {
        try {
            ErrorResponseBody errorResponseBody = objectMapper.readValue(error.getResponseBodyAsString(), ErrorResponseBody.class);
            return Optional.of(errorResponseBody);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
