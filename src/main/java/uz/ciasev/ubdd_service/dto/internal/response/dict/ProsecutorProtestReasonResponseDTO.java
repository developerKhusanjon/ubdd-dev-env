package uz.ciasev.ubdd_service.dto.internal.response.dict;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.dict.response.DictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.prosecutor.ProsecutorProtestReason;

@Getter
public class ProsecutorProtestReasonResponseDTO extends DictResponseDTO {
    private final Long reasonCancellationId;

    public ProsecutorProtestReasonResponseDTO(ProsecutorProtestReason entity) {
        super(entity);
        this.reasonCancellationId = entity.getReasonCancellationId();
    }
}
