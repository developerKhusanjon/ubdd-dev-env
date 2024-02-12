package uz.ciasev.ubdd_service.dto.internal.request.victim;

import uz.ciasev.ubdd_service.dto.internal.request.damage.DamageRequestDTO;
import uz.ciasev.ubdd_service.entity.victim.Victim;

import java.util.List;

public interface VictimRequestDTO {

    String getMobile();

    String getLandline();
    String getInn();

    List<DamageRequestDTO> getDamages();

    Victim applyTo(Victim victim);
}
