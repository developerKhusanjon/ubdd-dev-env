package uz.ciasev.ubdd_service.dto.internal.response.dict;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.dict.response.DictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceCategory;

@Getter
public class EvidenceCategoryResponseDTO extends DictResponseDTO {
    private final Boolean isMoney;

    public EvidenceCategoryResponseDTO(EvidenceCategory entity) {
        super(entity);
        this.isMoney = entity.getIsMoney();
    }
}
