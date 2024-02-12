package uz.ciasev.ubdd_service.dto.internal.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.AdmCaseChangeReason;
import uz.ciasev.ubdd_service.entity.dict.ChangeReasonType;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;
import uz.ciasev.ubdd_service.utils.validator.ValidFileUri;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ChangeReasonRequestDTO {
    
    @NotNull(message = ErrorCode.CHANGE_REASON_TYPE_REQUIRED)
    @ActiveOnly(message = ErrorCode.CHANGE_REASON_TYPE_DEACTIVATED)
    @JsonProperty(value = "changeReasonTypeId")
    @JsonAlias(value = "changeReasonType")
    private ChangeReasonType changeReasonType;
    
    @NotNull(message = ErrorCode.CHANGE_REASON_DOCUMENT_URL_REQUIRED)
    @ValidFileUri(message = ErrorCode.DOCUMENT_URI_INVALID)
    private String documentUri;
    
    @NotNull(message = ErrorCode.CHANGE_REASON_DESCRIPTION_REQUIRED)
    @Size(max = 500, message = ErrorCode.CHANGE_REASON_DESCRIPTION_MAX_LENGTH)
    private String description;


    public AdmCaseChangeReason buildAdmCaseChangeReason() {
        AdmCaseChangeReason reason = new AdmCaseChangeReason();
        
        reason.setChangeReasonType(this.changeReasonType);
        reason.setDocumentUrl(this.documentUri);
        reason.setDescription(this.description);
        
        return reason;
    }
}
