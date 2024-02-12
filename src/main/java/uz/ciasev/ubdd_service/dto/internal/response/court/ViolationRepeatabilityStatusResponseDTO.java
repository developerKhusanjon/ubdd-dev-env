package uz.ciasev.ubdd_service.dto.internal.response.court;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.dict.response.DictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.court.ViolationRepeatabilityStatus;

@Getter
public class ViolationRepeatabilityStatusResponseDTO extends DictResponseDTO {

    private final Boolean isNeedEarlierViolatedArticleParts;

    public ViolationRepeatabilityStatusResponseDTO(ViolationRepeatabilityStatus entity) {
        super(entity);
        this.isNeedEarlierViolatedArticleParts = entity.isNeedEarlierViolatedArticleParts();
    }
}
