package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.requests.UBDDConfiscatedCategoryUpdateDTOI;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDConfiscatedCategory;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UBDDConfiscatedCategoryUpdateRequestDTO extends ExternalDictUpdateRequestDTO<UBDDConfiscatedCategory> implements UBDDConfiscatedCategoryUpdateDTOI {

    private List<Long> ids;

    @Override
    public void applyToOld(UBDDConfiscatedCategory entity) {
        entity.update(this);
    }
}
