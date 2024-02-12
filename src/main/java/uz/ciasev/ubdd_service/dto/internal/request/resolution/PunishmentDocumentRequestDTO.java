package uz.ciasev.ubdd_service.dto.internal.request.resolution;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.DocumentType;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PunishmentDocument;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;

import javax.validation.constraints.NotNull;

@Data
public class PunishmentDocumentRequestDTO {

    @NotNull(message = ErrorCode.DOCUMENT_TYPE_REQUIRED)
    @ActiveOnly(message = ErrorCode.DOCUMENT_TYPE_DEACTIVATED)
    @JsonProperty(value = "documentTypeId")
    private DocumentType documentType;

    @NotNull(message = ErrorCode.URI_REQUIRED)
    private String uri;

    public PunishmentDocument buildDocument() {

        PunishmentDocument document = new PunishmentDocument();

        document.setDocumentType(this.documentType);
        document.setUri(this.uri);

        return document;
    }

    public PunishmentDocument applyTo(PunishmentDocument document) {

        document.setDocumentType(this.documentType);
        document.setUri(this.uri);

        return document;
    }
}
