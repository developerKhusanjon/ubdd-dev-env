package uz.ciasev.ubdd_service.dto.internal.request.execution;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.request.RegionDistrictRequest;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;

import javax.validation.constraints.NotNull;

@Data
//@ConsistGeography
public class CourtMibCardRequestDTO implements RegionDistrictRequest {

    @NotNull(message = ErrorCode.REGION_REQUIRED)
    @ActiveOnly(message = ErrorCode.REGION_DEACTIVATED)
    @JsonProperty(value = "regionId")
    private Region region;

    @NotNull(message = ErrorCode.DISTRICT_REQUIRED)
    @ActiveOnly(message = ErrorCode.DISTRICT_DEACTIVATED)
    @JsonProperty(value = "districtId")
    private District district;

    public MibExecutionCard buildMibExecutionCard() {
        MibExecutionCard card = new MibExecutionCard();

        card.setRegion(this.region);
        card.setDistrict(this.district);

        return card;
    }

    public MibExecutionCard applyTo(MibExecutionCard card) {
        card.setRegion(this.region);
        card.setDistrict(this.district);

        return card;
    }
}
