package uz.ciasev.ubdd_service.dto.internal.request.mib;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.mib.MibReturnRequestReason;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovementReturnRequest;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class MibReturnRequestDTO {

    @NotNull(message = ErrorCode.REASON_REQUIRED)
    @ActiveOnly(message = ErrorCode.REASON_DEACTIVATED)
    @JsonProperty(value = "reasonId")
    private MibReturnRequestReason reason;

    @Size(max = 500, message = ErrorCode.COMMENT_MAX_LEN_500)
    private String comment;

    public MibCardMovementReturnRequest.CreateRequest build() {
        MibCardMovementReturnRequest.CreateRequest entity = new MibCardMovementReturnRequest.CreateRequest();

        entity.setComment(comment);
        entity.setReason(reason);

        return entity;
    }
}
