package uz.ciasev.ubdd_service.dto.internal.response.adm.mib;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionStatusDocument;
import uz.ciasev.ubdd_service.utils.types.LocalFileUrl;

import java.time.LocalDateTime;

@Data
public class MibExecutionStatusDocumentResponseDTO {

    private Long id;
    private LocalDateTime createdTime;
    private Long movementId;
    private Long cardMovementId;
    private Long executionStatusId;
    private LocalFileUrl url;

    public MibExecutionStatusDocumentResponseDTO(MibExecutionStatusDocument document) {
        this.id = document.getId();
        this.createdTime = document.getCreatedTime();
        this.movementId = document.getCardMovementId();
        this.cardMovementId = document.getCardMovementId();
        this.executionStatusId = document.getExecutionStatusId();
        this.url = LocalFileUrl.ofNullable(document.getUri());
    }
}
