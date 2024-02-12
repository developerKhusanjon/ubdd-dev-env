package uz.ciasev.ubdd_service.dto.internal.request.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.document.Document;
import uz.ciasev.ubdd_service.entity.dict.DocumentType;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;
import uz.ciasev.ubdd_service.utils.validator.AttachableDocument;
import uz.ciasev.ubdd_service.utils.validator.ValidFileUri;

import javax.validation.constraints.NotNull;

@Data
public class DocumentRequestDTO {

    private Long personId;

    @AttachableDocument
    @NotNull(message = ErrorCode.DOCUMENT_TYPE_REQUIRED)
    @ActiveOnly(message = ErrorCode.DOCUMENT_TYPE_DEACTIVATED)
    @JsonProperty(value = "documentTypeId")
    private DocumentType documentType;

    @NotNull(message = ErrorCode.DOCUMENT_DESCRIPTION_REQUIRED)
    private String description;

    @NotNull(message = ErrorCode.DOCUMENT_URL_REQUIRED)
    @ValidFileUri(message = ErrorCode.DOCUMENT_URI_INVALID)
    private String uri;

    public Document buildDocument() {
        Document document = new Document();

        document.setDocumentType(this.documentType);
        document.setDescription(this.description);
        document.setUrl(this.uri);

        return document;
    }
}
