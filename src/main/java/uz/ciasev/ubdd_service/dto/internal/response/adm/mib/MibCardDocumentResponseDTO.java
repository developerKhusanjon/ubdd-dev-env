package uz.ciasev.ubdd_service.dto.internal.response.adm.mib;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.mib.MibCardDocument;
import uz.ciasev.ubdd_service.utils.types.LocalFileUrl;

import java.time.LocalDateTime;

@Data
public class MibCardDocumentResponseDTO {

    private final Long id;
    private final LocalDateTime createdTime;
    private final LocalDateTime editedTime;
    private final Long userId;
    private final Long cardId;
    private final Long documentTypeId;
    private final LocalFileUrl url;

    public MibCardDocumentResponseDTO(MibCardDocument document) {
        this.id = document.getId();
        this.createdTime = document.getCreatedTime();
        this.editedTime = document.getEditedTime();
        this.userId = document.getUserId();
        this.cardId = document.getCardId();
        this.documentTypeId = document.getDocumentTypeId();
        this.url = new LocalFileUrl(document.getUri());
    }

}
