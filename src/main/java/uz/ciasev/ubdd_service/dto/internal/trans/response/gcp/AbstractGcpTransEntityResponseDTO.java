package uz.ciasev.ubdd_service.dto.internal.trans.response.gcp;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.trans.gcp.AbstractGcpTransEntity;

import java.time.LocalDate;

@Getter
public abstract class AbstractGcpTransEntityResponseDTO {
    private final Long id;
    private final LocalDate createdTime;
    private final LocalDate editedTime;
    private final Long externalId;
    private final Boolean isActive;

    public AbstractGcpTransEntityResponseDTO(AbstractGcpTransEntity entity) {
        this.id = entity.getId();
        this.createdTime = entity.getCreatedTime();
        this.editedTime = entity.getEditedTime();
        this.externalId = entity.getExternalId();
        this.isActive = entity.getIsActive();
    }
}
