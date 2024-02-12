package uz.ciasev.ubdd_service.dto.internal.request.admcase;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.request.PlaceRequest;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;

import javax.validation.constraints.NotNull;

@Data
public class AdmCaseSendRequestDTO implements PlaceRequest {

    @NotNull(message = ErrorCode.ADM_CASE_ID_REQUIRED)
    private Long admCaseId;

    @NotNull(message = ErrorCode.ORGAN_REQUIRED)
    @ActiveOnly(message = ErrorCode.ORGAN_DEACTIVATED)
    @JsonProperty("organId")
    private Organ organ;

    @ActiveOnly(message = ErrorCode.DEPARTMENT_DEACTIVATED)
    @JsonProperty(value = "departmentId")
    private Department department;

    @NotNull(message = ErrorCode.REGION_REQUIRED)
    @ActiveOnly(message = ErrorCode.REGION_DEACTIVATED)
    @JsonProperty(value = "regionId")
    private Region region;

    @ActiveOnly(message = ErrorCode.DISTRICT_DEACTIVATED)
    @JsonProperty(value = "districtId")
    private District district;
}
