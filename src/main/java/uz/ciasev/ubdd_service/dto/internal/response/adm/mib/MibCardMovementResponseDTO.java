package uz.ciasev.ubdd_service.dto.internal.response.adm.mib;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;

@Getter
public class MibCardMovementResponseDTO extends InnerMibCardMovementResponseDTO {

    private final Long id;

    public MibCardMovementResponseDTO(MibCardMovement lastMovement) {
        super(lastMovement);
        this.id = lastMovement.getId();
    }
}
