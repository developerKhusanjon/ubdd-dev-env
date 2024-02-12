package uz.ciasev.ubdd_service.dto.internal.request.settings;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.request.PlaceRequest;
import uz.ciasev.ubdd_service.entity.Place;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;

@Getter
@EqualsAndHashCode(callSuper = true)
public class OrganInfoCreateRequestDTO extends OrganInfoUpdateRequestDTO implements PlaceRequest, Place {

    @NotNull(message = ErrorCode.ORGAN_REQUIRED)
    @JsonProperty(value = "organId")
    private Organ organ;

    @JsonProperty(value = "departmentId")
    private Department department;

    @JsonProperty(value = "regionId")
    private Region region;

    @JsonProperty(value = "districtId")
    private District district;
}
