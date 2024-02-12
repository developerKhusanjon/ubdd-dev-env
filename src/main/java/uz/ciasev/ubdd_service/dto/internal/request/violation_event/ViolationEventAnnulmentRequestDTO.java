package uz.ciasev.ubdd_service.dto.internal.request.violation_event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.violation_event.ViolationEventAnnulmentReason;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;
import uz.ciasev.ubdd_service.utils.validator.ValidFileUri;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ViolationEventAnnulmentRequestDTO {

    @NotNull(message = ErrorCode.VIOLATION_EVENT_ID_REQUIRED)
    private Long violationEventId;

    @NotNull(message = ErrorCode.VIOLATION_EVENT_ANNULMENT_REASON_REQUIRED)
    @ActiveOnly(message = ErrorCode.VIOLATION_EVENT_ANNULMENT_REASON_DEACTIVATED)
    @JsonProperty(value = "reasonId")
    private ViolationEventAnnulmentReason reason;

    @Size(max = 4000, message = ErrorCode.VIOLATION_EVENT_ANNULMENT_REASON_COMMENT_MAX_LEN_4000)
    private String comment;

    @ValidFileUri(message = ErrorCode.DOCUMENT_URI_INVALID)
    private String documentBaseUri;
}
