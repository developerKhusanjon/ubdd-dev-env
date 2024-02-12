package uz.ciasev.ubdd_service.dto.internal.response.dict.resolution;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.dict.response.DictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.resolution.TerminationReason;

@Getter
public class TerminationReasonResponseDTO extends DictResponseDTO {
    private final Boolean isParticipateOfRepeatability;

    public TerminationReasonResponseDTO(TerminationReason terminationReason) {
        super(terminationReason);
        this.isParticipateOfRepeatability = terminationReason.getIsParticipateOfRepeatability();
    }
}

