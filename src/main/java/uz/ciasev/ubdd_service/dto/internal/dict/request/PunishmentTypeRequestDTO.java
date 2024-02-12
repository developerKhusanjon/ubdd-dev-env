package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.requests.PunishmentTypeDTOI;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentType;

@EqualsAndHashCode(callSuper = true)
@Data
public class PunishmentTypeRequestDTO extends BackendDictUpdateRequestDTO<PunishmentType> implements PunishmentTypeDTOI {

    private Long courtAdditionalPunishmentId;

    @Override
    public void applyToOld(PunishmentType entity) {
        entity.update(this);
    }
}
