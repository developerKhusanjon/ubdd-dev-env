package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceCategory;
import uz.ciasev.ubdd_service.entity.dict.requests.EvidenceCategoryCreateDTOI;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class EvidenceCategoryCreateRequestDTO extends EvidenceCategoryUpdateRequestDTO implements EvidenceCategoryCreateDTOI, DictCreateRequest<EvidenceCategory> {

    @NotNull(message = ErrorCode.ID_REQUIRED)
    private Long id;

    @Override
    public void applyToNew(EvidenceCategory entity) {
        entity.construct(this);
    }
}
