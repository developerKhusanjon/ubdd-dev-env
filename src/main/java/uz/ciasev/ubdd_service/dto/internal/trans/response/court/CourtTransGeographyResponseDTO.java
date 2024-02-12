package uz.ciasev.ubdd_service.dto.internal.trans.response.court;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransGeography;

@Getter
public class CourtTransGeographyResponseDTO {
    private final Long id;
    private final Long externalCountryId;
    private final Long externalRegionId;
    private final Long externalDistrictId;
    private final Long countryId;
    private final Long regionId;
    private final Long districtId;


    public CourtTransGeographyResponseDTO(CourtTransGeography entity) {
        this.id = entity.getId();
        this.externalCountryId = entity.getExternalCountryId();
        this.externalRegionId = entity.getExternalRegionId();
        this.externalDistrictId = entity.getExternalDistrictId();
        this.countryId = entity.getCountryId();
        this.regionId = entity.getRegionId();
        this.districtId = entity.getDistrictId();
    }
}
