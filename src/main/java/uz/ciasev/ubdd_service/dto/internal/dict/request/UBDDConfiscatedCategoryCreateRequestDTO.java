package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.requests.UBDDConfiscatedCategoryCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDConfiscatedCategory;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class UBDDConfiscatedCategoryCreateRequestDTO extends UBDDConfiscatedCategoryUpdateRequestDTO implements UBDDConfiscatedCategoryCreateDTOI, DictCreateRequest<UBDDConfiscatedCategory> {

    @NotNull(message = ErrorCode.ID_REQUIRED)
    private Long id;

    @Override
    public void applyToNew(UBDDConfiscatedCategory entity) {
        entity.construct(this);
    }
}
