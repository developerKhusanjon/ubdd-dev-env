package uz.ciasev.ubdd_service.dto.internal.request.evidence;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.dto.internal.request.ChangeReasonRequestDTO;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class EvidenceUpdateRequestDTO extends EvidenceRequestDTO {

    @NotNull(message = ErrorCode.CHANGE_REASON_REQUIRED)
    @Valid
    private ChangeReasonRequestDTO changeReason;
}
