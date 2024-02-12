package uz.ciasev.ubdd_service.dto.internal.request.mib;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.DocumentType;
import uz.ciasev.ubdd_service.entity.dict.FileFormatAlias;
import uz.ciasev.ubdd_service.entity.mib.MibCardDocument;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;
import uz.ciasev.ubdd_service.utils.validator.AttachableDocument;
import uz.ciasev.ubdd_service.utils.validator.ValidFileUri;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class MibCardDocumentRequestDTO {

    @AttachableDocument
    @NotNull(message = ErrorCode.DOCUMENT_TYPE_REQUIRED)
    @ActiveOnly(message = ErrorCode.DOCUMENT_TYPE_DEACTIVATED)
    @JsonProperty(value = "documentTypeId")
    private DocumentType documentType;

    @NotNull(message = ErrorCode.URI_REQUIRED)
    @ValidFileUri(allow = FileFormatAlias.PDF, message = ErrorCode.URI_INVALID)
    private String uri;

    public MibCardDocument buildMibCardDocument() {
        MibCardDocument document = new MibCardDocument();

        document.setDocumentType(this.documentType);
        document.setUri(this.uri);

        return document;
    }

    public MibCardDocument applyTo(MibCardDocument document) {
        document.setDocumentType(this.documentType);
        document.setUri(this.uri);

        return document;
    }
}
