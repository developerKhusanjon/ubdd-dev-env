package uz.ciasev.ubdd_service.service.damage;

import uz.ciasev.ubdd_service.dto.internal.response.adm.DamageResponseDTO;
import uz.ciasev.ubdd_service.entity.damage.Damage;
import uz.ciasev.ubdd_service.entity.dict.VictimType;
import uz.ciasev.ubdd_service.entity.victim.Victim;
import uz.ciasev.ubdd_service.entity.violator.Violator;

import java.util.List;

public interface DamageService {

    Damage findById(Long id);

//    DamageResponseDTO findDetailById(Long id);

    Damage getOrCreate(Violator violator, Victim victim, VictimType victimType);

    void updateAmount(Damage damage, Long amount);

    List<Damage> findByViolatorId(Long id);

    List<Damage> findByVictimId(Long id);

    List<Damage> findByAdmCaseId(Long id);

    List<Damage> findByViolatorAndAdmCaseIds(Long admCaseId, Long violatorId);

    List<DamageResponseDTO> findAllByAdmCaseId(Long admCaseId);
}
