package uz.ciasev.ubdd_service.dto.internal.response.dict.ubdd;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.dict.response.DictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDConfiscatedCategory;

import java.util.List;

@Getter
public class UBDDConfiscatedCategoryResponseDTO extends DictResponseDTO {
    private final List<Long> ids;

    public UBDDConfiscatedCategoryResponseDTO(UBDDConfiscatedCategory entity) {
        super(entity);
        this.ids = entity.getIds();
    }
}
