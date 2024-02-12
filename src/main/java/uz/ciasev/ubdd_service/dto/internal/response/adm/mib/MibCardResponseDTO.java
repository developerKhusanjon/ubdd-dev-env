package uz.ciasev.ubdd_service.dto.internal.response.adm.mib;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.notification.DecisionNotification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
public class MibCardResponseDTO {

    private final Long id;
    private final LocalDateTime createdTime;
    private final LocalDateTime editedTime;
    private final Long ownerTypeId;
    private final String outNumber;
    private final LocalDate outDate;
    private final Long regionId;
    private final Long districtId;

    private final Long notificationTypeId;
    @JsonUnwrapped
    private final InnerMibCardNotificationResponseDTO notification;

    @JsonUnwrapped
    private final InnerMibCardMovementResponseDTO lastMovement;

    public MibCardResponseDTO(MibExecutionCard card) {
        this(card, null, null);
    }

    public MibCardResponseDTO(MibExecutionCard card, MibCardMovement lastMovement) {
        this(card, lastMovement, null);
    }

    public MibCardResponseDTO(MibExecutionCard card, MibCardMovement lastMovement, DecisionNotification notification) {

        this.id = card.getId();
        this.createdTime = card.getCreatedTime();
        this.editedTime = card.getEditedTime();
        this.ownerTypeId = card.getOwnerTypeId();
        this.outNumber = card.getOutNumber();
        this.outDate = card.getOutDate();
        this.regionId = card.getRegionId();
        this.districtId = card.getDistrictId();

        this.notificationTypeId = card.getNotificationTypeId();
        this.notification = Optional.ofNullable(notification).map(InnerMibCardNotificationResponseDTO::new).orElse(new InnerMibCardNotificationResponseDTO());

        this.lastMovement = Optional.ofNullable(lastMovement).map(InnerMibCardMovementResponseDTO::new).orElse(new InnerMibCardMovementResponseDTO());
    }
}
