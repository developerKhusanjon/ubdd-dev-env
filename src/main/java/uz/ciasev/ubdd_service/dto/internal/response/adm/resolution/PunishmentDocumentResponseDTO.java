package uz.ciasev.ubdd_service.dto.internal.response.adm.resolution;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PunishmentDocument;
import uz.ciasev.ubdd_service.utils.types.LocalFileUrl;

import java.time.LocalDateTime;

@Data
public class PunishmentDocumentResponseDTO {

    private final Long id;
    private final LocalDateTime createdTime;
    private final LocalDateTime editedTime;
    private final Long userId;
    private final Long punishmentId;
    private final Long documentTypeId;
    private final LocalFileUrl url;

    public PunishmentDocumentResponseDTO(PunishmentDocument document) {

        this.id = document.getId();
        this.createdTime = document.getCreatedTime();
        this.editedTime = document.getEditedTime();
        this.userId = document.getUser().getId();
        this.punishmentId = document.getPunishment().getId();
        this.documentTypeId = document.getDocumentType().getId();
        this.url = LocalFileUrl.ofNullable(document.getUri());
    }
}
