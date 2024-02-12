package uz.ciasev.ubdd_service.dto.internal.trans.request.court;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.Country;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.CourtTransGeographyCreateDTOI;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransGeography;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.trans.TransEntityCreateRequest;

import javax.validation.constraints.NotNull;

@Data
public class CourtTransGeographyCreateRequestDTO implements CourtTransGeographyCreateDTOI, TransEntityCreateRequest<CourtTransGeography> {

    @NotNull(message = ErrorCode.EXTERNAL_COUNTRY_ID_REQUIRED)
    private Long externalCountryId;

    private Long externalRegionId;

    private Long externalDistrictId;

    @NotNull(message = ErrorCode.COUNTRY_ID_REQUIRED)
    @JsonProperty(value = "countryId")
    private Country country;

    @JsonProperty(value = "regionId")
    private Region region;

    @JsonProperty(value = "districtId")
    private District district;

    @Override
    public void applyToNew(CourtTransGeography entity) {
        entity.construct(this);
    }
}
