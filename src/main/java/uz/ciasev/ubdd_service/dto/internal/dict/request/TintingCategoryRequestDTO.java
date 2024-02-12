package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.requests.TintingCategoryDTOI;
import uz.ciasev.ubdd_service.entity.dict.ubdd.TintingCategory;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;
import uz.ciasev.ubdd_service.service.dict.DictUpdateRequest;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class TintingCategoryRequestDTO extends BaseDictRequestDTO implements TintingCategoryDTOI, DictCreateRequest<TintingCategory>, DictUpdateRequest<TintingCategory> {

    @NotNull(message = ErrorCode.PERCENTAGE_REQUIRED)
    @DecimalMin(value = "0", message = ErrorCode.PERCENTAGE_MIN_MAX_SIZE)
    @DecimalMax(value = "100", message = ErrorCode.PERCENTAGE_MIN_MAX_SIZE)
    private BigDecimal percentage;

    @Override
    public void applyToNew(TintingCategory entity) {
        entity.construct(this);
    }

    @Override
    public void applyToOld(TintingCategory entity) {
        entity.update(this);
    }
}
