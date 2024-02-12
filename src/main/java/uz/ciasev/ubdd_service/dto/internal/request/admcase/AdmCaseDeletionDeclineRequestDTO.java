package uz.ciasev.ubdd_service.dto.internal.request.admcase;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRequest;
import uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseDeletionRequestDeclineReason;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AdmCaseDeletionDeclineRequestDTO {

    @NotNull(message = ErrorCode.ADM_CASE_DELETION_DECLINE_REASON_REQUIRED)
    @ActiveOnly(message = ErrorCode.ADM_CASE_DELETION_DECLINE_REASON_DEACTIVATED)
    @JsonProperty(value = "reasonId")
    private AdmCaseDeletionRequestDeclineReason reason;

    @NotBlank(message = ErrorCode.ADM_CASE_DELETION_DECLINE_COMMENT_REQUIRED)
    @Size(max = 4000, message = ErrorCode.ADM_CASE_DELETION_DECLINE_COMMENT_MAX_LEN_4000)
    private String comment;

    public AdmCaseDeletionRequest applyTo(AdmCaseDeletionRequest admCaseDeletionRequest) {
        admCaseDeletionRequest.setDeclineReason(this.reason);
        admCaseDeletionRequest.setDeclineComment(this.comment);

        return admCaseDeletionRequest;
    }
}
