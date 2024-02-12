package uz.ciasev.ubdd_service.mvd_core.api.violation_event.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.ciasev.ubdd_service.mvd_core.api.internal.InternalApiRequestHelper;
import uz.ciasev.ubdd_service.mvd_core.api.internal.InternalApiServiceAlias;
import uz.ciasev.ubdd_service.mvd_core.api.violation_event.dto.ViolationEventApiDTO;

@Service
public class ViolationEventApiServiceImpl implements ViolationEventApiService {
    private final InternalApiRequestHelper requestHelper;
    private final String host;

    public ViolationEventApiServiceImpl(@Value("${internal-api.host}") String host,
                                        @Qualifier("violationEventRestTemplate") RestTemplate restTemplate,
                                        ObjectMapper objectMapper) {
        this.requestHelper = new InternalApiRequestHelper(restTemplate, objectMapper, InternalApiServiceAlias.TECH_PASS);
        this.host = host;
    }

    @Override
    public ViolationEventApiDTO getById(Long id) {
        return requestHelper.sendGetRequest(
                String.format("%s/internal/violation-event-api/violation-events/%d", host, id),
                ViolationEventApiDTO.class);
    }

    @Override
    public void existById(Long id) {
        requestHelper.sendHeadRequest(String.format("%s/internal/violation-event-api/violation-events/%d", host, id));
    }
}
