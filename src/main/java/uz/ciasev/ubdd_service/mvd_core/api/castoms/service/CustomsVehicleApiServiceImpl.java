package uz.ciasev.ubdd_service.mvd_core.api.castoms.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.ciasev.ubdd_service.mvd_core.api.castoms.dto.CustomsVehicleApiDTO;
import uz.ciasev.ubdd_service.mvd_core.api.internal.InternalApiRequestHelper;
import uz.ciasev.ubdd_service.mvd_core.api.internal.InternalApiServiceAlias;
import uz.ciasev.ubdd_service.service.dict.ubdd.UBDDVehicleOwnerTypeService;

@Service
public class CustomsVehicleApiServiceImpl implements CustomsVehicleApiService {
    private final UBDDVehicleOwnerTypeService ownerTypeService;
    private final InternalApiRequestHelper requestHelper;
    private final String host;

    public CustomsVehicleApiServiceImpl(@Value("${internal-api.host}") String host,
                                        @Qualifier("customsRestTemplate") RestTemplate restTemplate,
                                        UBDDVehicleOwnerTypeService ownerTypeService,
                                        ObjectMapper objectMapper) {
        this.requestHelper = new InternalApiRequestHelper(restTemplate, objectMapper, InternalApiServiceAlias.CUSTOMS_VEHICLE);
        this.host = host;
        this.ownerTypeService = ownerTypeService;
    }

    @Override
    public CustomsVehicleApiDTO getVehicleEventById(Long eventId) {
        CustomsVehicleApiDTO dto = requestHelper.sendGetRequest(
                String.format("%s/internal/customs-vehicle-api/events/%d", host, eventId),
                CustomsVehicleApiDTO.class);

        dto.setVehicleOwnerType(ownerTypeService.getUnknown());

        return dto;
    }
}
