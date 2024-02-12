package uz.ciasev.ubdd_service.service.ubdd_data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.castoms.service.CustomsVehicleApiService;
import uz.ciasev.ubdd_service.mvd_core.api.texpass.service.TechPassApiService;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.MultiSystemTechPassIdRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddTexPassDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;

@Service
@RequiredArgsConstructor
public class MultiSystemTechPassSupplier {

    private final TechPassApiService texPassService;
    private final CustomsVehicleApiService customsVehicleService;


    public UbddTexPassDTOI getByExternalId(MultiSystemTechPassIdRequestDTO requestDTO) {
        if (requestDTO.getVehicleId() != null && requestDTO.getCustomsEventId() != null) {
            throw new ValidationException(ErrorCode.MULTIPLE_TECH_PASS_IDENTIFIERS_PRESENTED);
        }

        if (requestDTO.getVehicleId() != null) {
            return texPassService.getTechPassById(requestDTO.getVehicleId());
        }

        if (requestDTO.getCustomsEventId() != null) {
            return customsVehicleService.getVehicleEventById(requestDTO.getCustomsEventId());
        }

        throw new ValidationException(ErrorCode.NO_ONE_TECH_PASS_IDENTIFIERS_PRESENTED);
    }
}
