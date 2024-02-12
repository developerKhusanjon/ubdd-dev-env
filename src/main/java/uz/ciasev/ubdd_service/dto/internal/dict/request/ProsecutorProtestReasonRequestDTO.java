package uz.ciasev.ubdd_service.dto.internal.dict.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.prosecutor.ProsecutorProtestReason;
import uz.ciasev.ubdd_service.entity.dict.requests.ProsecutorProtestReasonDTOI;
import uz.ciasev.ubdd_service.entity.dict.resolution.ReasonCancellation;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;
import uz.ciasev.ubdd_service.service.dict.DictUpdateRequest;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProsecutorProtestReasonRequestDTO extends BaseDictRequestDTO implements ProsecutorProtestReasonDTOI, DictCreateRequest<ProsecutorProtestReason>, DictUpdateRequest<ProsecutorProtestReason> {

    @NotNull(message = ErrorCode.REASON_CANCELLATION_REQUIRED)
    @JsonProperty("reasonCancellationId")
    private ReasonCancellation reasonCancellation;

    @Override
    public void applyToNew(ProsecutorProtestReason entity) {
        entity.construct(this);
    }

    @Override
    public void applyToOld(ProsecutorProtestReason entity) {
        entity.update(this);
    }
}
