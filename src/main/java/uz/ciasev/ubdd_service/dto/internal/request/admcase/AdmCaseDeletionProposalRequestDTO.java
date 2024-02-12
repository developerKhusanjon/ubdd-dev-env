package uz.ciasev.ubdd_service.dto.internal.request.admcase;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseDeletionReason;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;
import uz.ciasev.ubdd_service.utils.validator.ValidFileUri;

import javax.validation.constraints.NotNull;

@Data
public class AdmCaseDeletionProposalRequestDTO {

    @NotNull(message = ErrorCode.ADM_CASE_ID_REQUIRED)
    private Long admCaseId;

    @NotNull(message = ErrorCode.ADM_CASE_DELETION_REASON_REQUIRED)
    @JsonProperty(value = "reasonId")
    @ActiveOnly(message = ErrorCode.ADM_CASE_DELETION_REASON_DEACTIVATED)
    private AdmCaseDeletionReason admCaseDeletionReason;

    @NotNull(message = ErrorCode.ADM_CASE_DELETION_DOCUMENT_BASE_REQUIRED)
    @ValidFileUri(message = ErrorCode.DOCUMENT_URI_INVALID)
    private String documentBaseUri;

    private String signature;

//    public AdmCaseDeletionRequest build() {
//        AdmCaseDeletionRequest deletionRequest = new AdmCaseDeletionRequest();
//
//        return deletionRequest;
//    }
}
