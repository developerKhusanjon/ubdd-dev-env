package uz.ciasev.ubdd_service.dto.internal.request.document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.dto.internal.request.ChangeReasonRequestDTO;
import uz.ciasev.ubdd_service.entity.document.Document;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class DocumentUpdateRequestDTO extends DocumentRequestDTO {

    @NotNull(message = ErrorCode.CHANGE_REASON_REQUIRED)
    @Valid
    private ChangeReasonRequestDTO changeReason;

    public Document applyTo(Document document) {
        document.setDocumentType(this.getDocumentType());
        document.setDescription(this.getDescription());
        document.setUrl(this.getUri());

        return document;
    }
}
