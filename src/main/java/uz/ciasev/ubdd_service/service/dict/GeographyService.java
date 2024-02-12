package uz.ciasev.ubdd_service.service.dict;

import uz.ciasev.ubdd_service.dto.internal.response.GeographyResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;

public interface GeographyService {

    GeographyResponseDTO getMibPresenceDTO();
    GeographyResponseDTO getCourtPresenceDTO();

    Boolean isMibPresent(Region region);
    Boolean isMibPresent(District district);

    Boolean isCourtPresent(Region region);
    Boolean isCourtPresent(District district);
}
