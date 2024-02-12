package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.person.AgeCategory;
import uz.ciasev.ubdd_service.entity.dict.requests.AgeCategoryDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;
import uz.ciasev.ubdd_service.service.dict.DictUpdateRequest;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class AgeCategoryRequestDTO extends BaseDictRequestDTO implements AgeCategoryDTOI, DictCreateRequest<AgeCategory>, DictUpdateRequest<AgeCategory> {
    @NotNull(message = ErrorCode.AGE_FROM_REQUIRED)
    @Min(value = 0, message = ErrorCode.AGE_FROM_MIN_MAX_SIZE)
    @Max(value = 199, message = ErrorCode.AGE_FROM_MIN_MAX_SIZE)
    private Integer ageFrom;

    @NotNull(message = ErrorCode.AGE_TO_REQUIRED)
    @Min(value = 1, message = ErrorCode.AGE_TO_MIN_MAX_SIZE)
    @Max(value = 200, message = ErrorCode.AGE_TO_MIN_MAX_SIZE)
    private Integer ageTo;

    @NotNull(message = ErrorCode.IS_JUVENILE_REQUIRED)
    private Boolean isJuvenile;

    @NotNull(message = ErrorCode.IS_VIOLATOR_ONLY_REQUIRED)
    private Boolean isViolatorOnly;

    @Override
    public void applyToNew(AgeCategory entity) {
        entity.construct(this);
    }

    @Override
    public void applyToOld(AgeCategory entity) {
        entity.update(this);
    }
}
