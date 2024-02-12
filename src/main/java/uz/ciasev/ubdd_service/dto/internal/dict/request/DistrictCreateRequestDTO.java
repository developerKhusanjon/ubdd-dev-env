package uz.ciasev.ubdd_service.dto.internal.dict.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.requests.DistrictCreateDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Getter
public class DistrictCreateRequestDTO extends DistrictUpdateRequestDTO implements DistrictCreateDTOI, DictCreateRequest<District> {

    @NotNull(message = ErrorCode.REGION_REQUIRED)
    @JsonProperty(value = "regionId")
    private Region region;

    @Override
    public void applyToNew(District entity) {
        entity.construct(this);
    }
}
