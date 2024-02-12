package uz.ciasev.ubdd_service.dto.internal.response.dict.resolution;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.dict.response.AliasedDictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentType;

@Getter
public class PunishmentTypeResponseDTO extends AliasedDictResponseDTO {
    private final Long courtAdditionalPunishmentId;

    public PunishmentTypeResponseDTO(PunishmentType entity) {
        super(entity);
        this.courtAdditionalPunishmentId = entity.getCourtAdditionalPunishmentId();
    }
}

