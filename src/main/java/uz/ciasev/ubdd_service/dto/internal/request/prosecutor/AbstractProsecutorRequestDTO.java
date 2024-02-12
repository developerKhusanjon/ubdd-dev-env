package uz.ciasev.ubdd_service.dto.internal.request.prosecutor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.request.RegionDistrictRequest;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.user.Position;
import uz.ciasev.ubdd_service.entity.dict.user.Rank;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.prosecutor.AbstractProsecutor;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public abstract class AbstractProsecutorRequestDTO implements RegionDistrictRequest {

    @NotNull(message = ErrorCode.REGION_REQUIRED)
    @ActiveOnly(message = ErrorCode.REGION_DEACTIVATED)
    @JsonProperty(value = "regionId")
    private Region region;

    @NotNull(message = ErrorCode.DISTRICT_REQUIRED)
    @ActiveOnly(message = ErrorCode.DISTRICT_DEACTIVATED)
    @JsonProperty(value = "districtId")
    private District district;

    @NotBlank(message = ErrorCode.PROSECUTOR_INFO_REQUIRED)
    @Size(max = 300, message = ErrorCode.PROSECUTOR_INFO_MORE_THAN_300_CHARACTERS)
    private String prosecutorInfo;

    @NotNull(message = ErrorCode.RANK_REQUIRED)
    @ActiveOnly(message = ErrorCode.RANK_DEACTIVATED)
    @JsonProperty(value = "rankId")
    private Rank rank;

    @NotNull(message = ErrorCode.POSITION_REQUIRED)
    @ActiveOnly(message = ErrorCode.POSITION_DEACTIVATED)
    @JsonProperty(value = "positionId")
    private Position position;

    @NotBlank(message = ErrorCode.DESCRIPTION_REQUIRED)
    @Size(max = 4000, message = ErrorCode.DESCRIPTION_MORE_THAN_4000_CHARACTERS)
    private String description;

    public AbstractProsecutor applyTo(AbstractProsecutor prosecutor) {
        prosecutor.setRegion(this.region);
        prosecutor.setDistrict(this.district);
        prosecutor.setProsecutorInfo(this.prosecutorInfo);
        prosecutor.setRank(this.rank);
        prosecutor.setPosition(this.position);
        prosecutor.setDescription(this.description);
        return prosecutor;
    }
}
