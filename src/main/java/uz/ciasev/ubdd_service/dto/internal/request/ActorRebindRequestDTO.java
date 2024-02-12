package uz.ciasev.ubdd_service.dto.internal.request;

import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class ActorRebindRequestDTO implements ActorRequest {

    String pinpp;

    @Valid
    private PersonRequestDTO person;

    @Valid
    private PersonDocumentRequestDTO document;

    @Valid
    @NotNull(message = ErrorCode.CHANGE_REASON_REQUIRED)
    private ChangeReasonRequestDTO changeReason;

    @Override
    public String getPinpp() {
        return pinpp;
    }
}
