package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.requests.DistrictUpdateDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictUpdateRequest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Getter
public class DistrictUpdateRequestDTO extends BaseDictRequestDTO implements DistrictUpdateDTOI, DictUpdateRequest<District> {

    @NotNull(message = ErrorCode.IS_STATE_REQUIRED)
    private Boolean isState;

    private Boolean isNotDistrict;

    @NotNull(message = ErrorCode.REPORT_NAME_REQUIRED)
    @Size(min = 1, max = 100, message = ErrorCode.REPORT_NAME_MIN_MAX_LENGTH)
    private String reportName;

    @Override
    public void applyToOld(District entity) {
        entity.update(this);
    }
}
