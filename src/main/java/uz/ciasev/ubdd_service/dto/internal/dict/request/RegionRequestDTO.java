package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.requests.RegionDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;
import uz.ciasev.ubdd_service.service.dict.DictUpdateRequest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
public class RegionRequestDTO extends BaseDictRequestDTO implements RegionDTOI, DictCreateRequest<Region>, DictUpdateRequest<Region> {

    @NotNull(message = ErrorCode.IS_STATE_REQUIRED)
    private Boolean isState;

    @NotNull(message = ErrorCode.SERIAL_NAME_REQUIRED)
    @Size(min= 1, max = 3, message = ErrorCode.SERIAL_NAME_MIN_MAX_LENGTH)
    private String serialName;

    @Override
    public void applyToNew(Region entity) {
        entity.construct(this);
    }

    @Override
    public void applyToOld(Region entity) {
        entity.update(this);
    }
}
