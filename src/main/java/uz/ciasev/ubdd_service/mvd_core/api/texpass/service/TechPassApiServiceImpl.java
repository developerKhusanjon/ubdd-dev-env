package uz.ciasev.ubdd_service.mvd_core.api.texpass.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.ciasev.ubdd_service.mvd_core.api.internal.InternalApiRequestHelper;
import uz.ciasev.ubdd_service.mvd_core.api.texpass.dto.UbddTechPassApiDTO;
import uz.ciasev.ubdd_service.mvd_core.api.internal.InternalApiServiceAlias;

@Service
public class TechPassApiServiceImpl implements TechPassApiService {
    private final InternalApiRequestHelper requestHelper;
    private final String host;

    public TechPassApiServiceImpl(@Value("${internal-api.host}") String host,
                                  @Qualifier("techpassRestTemplate") RestTemplate restTemplate,
                                  ObjectMapper objectMapper) {
        this.requestHelper = new InternalApiRequestHelper(restTemplate, objectMapper, InternalApiServiceAlias.TECH_PASS);
        this.host = host;
    }

    @Override
    public UbddTechPassApiDTO getTechPassById(Long vehicleId) {
       return requestHelper.sendGetRequest(
                String.format("%s/internal/ubdd-data-api/texpass/%d", host, vehicleId),
                UbddTechPassApiDTO.class);
    }
}
