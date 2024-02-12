package uz.ciasev.ubdd_service.dto.internal.trans.response.court;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransDistrict;

@Getter
public class CourtTransDistrictResponseDTO {

    private final Long id;
    private final Long externalId;
    private final Long regionId;
    private final Long districtId;

    public CourtTransDistrictResponseDTO(CourtTransDistrict entity) {
        this.id = entity.getId();
        this.externalId = entity.getExternalId();
        this.regionId = entity.getRegionId();
        this.districtId = entity.getDistrictId();
    }
}
