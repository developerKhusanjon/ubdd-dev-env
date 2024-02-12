package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceCategory;
import uz.ciasev.ubdd_service.entity.dict.requests.EvidenceCategoryUpdateDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class EvidenceCategoryUpdateRequestDTO extends ExternalDictUpdateRequestDTO<EvidenceCategory> implements EvidenceCategoryUpdateDTOI {

    @NotNull(message = ErrorCode.IS_MONEY_REQUIRED)
    private Boolean isMoney;

    @Override
    public void applyToOld(EvidenceCategory entity) {
        entity.update(this);
    }
}
