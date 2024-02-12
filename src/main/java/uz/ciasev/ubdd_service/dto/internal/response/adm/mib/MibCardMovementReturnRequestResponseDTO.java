package uz.ciasev.ubdd_service.dto.internal.response.adm.mib;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovementReturnRequest;

import java.time.LocalDateTime;

@Getter
public class MibCardMovementReturnRequestResponseDTO {

    private final Long id;
    private final LocalDateTime createdTime;
    private final LocalDateTime editedTime;
    private final Long userId;
    private final Long movementId;
    private final Long reasonId;
    private final String comment;
    private final Long sendStatusId;
    private final String sendMessage;
    private final Boolean isApiErrorHappened;

    public MibCardMovementReturnRequestResponseDTO(MibCardMovementReturnRequest entity) {
        this.id = entity.getId();
        this.createdTime = entity.getCreatedTime();
        this.editedTime = entity.getEditedTime();
        this.userId = entity.getUserId();
        this.movementId = entity.getMovementId();
        this.reasonId = entity.getReasonId();
        this.comment = entity.getComment();
        this.sendStatusId = entity.getSendStatusId();
        this.sendMessage = entity.getSendMessage();
        this.isApiErrorHappened = entity.getIsApiErrorHappened();
    }
}
