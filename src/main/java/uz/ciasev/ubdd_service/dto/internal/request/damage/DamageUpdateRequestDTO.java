package uz.ciasev.ubdd_service.dto.internal.request.damage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.dto.internal.request.ChangeReasonRequestDTO;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class DamageUpdateRequestDTO extends DamageRequestDTO {

    @NotNull(message = ErrorCode.CHANGE_REASON_REQUIRED)
    @Valid
    private ChangeReasonRequestDTO changeReason;
}
