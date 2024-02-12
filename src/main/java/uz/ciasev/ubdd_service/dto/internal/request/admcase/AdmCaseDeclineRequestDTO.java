package uz.ciasev.ubdd_service.dto.internal.request.admcase;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.admcase.DeclineReason;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;

import javax.validation.constraints.NotNull;

@Data
public class AdmCaseDeclineRequestDTO {

    @NotNull(message = ErrorCode.ADM_CASE_ID_REQUIRED)
    private Long admCaseId;

    @NotNull(message = ErrorCode.DECLINE_REASON_REQUIRED)
    @ActiveOnly(message = ErrorCode.DECLINE_REASON_DEACTIVATED)
    @JsonProperty(value = "declineReasonId")
    @JsonAlias(value = "declineReason")
    private DeclineReason declineReason;

    @NotNull(message = ErrorCode.DECLINE_COMMENT_REQUIRED)
    private String declineComment;
}
