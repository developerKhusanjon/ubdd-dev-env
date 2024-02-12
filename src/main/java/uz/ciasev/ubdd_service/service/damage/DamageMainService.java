package uz.ciasev.ubdd_service.service.damage;

import uz.ciasev.ubdd_service.dto.internal.request.ChangeReasonRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.damage.DamageCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.damage.DamageRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.damage.DamageUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.damage.Damage;
import uz.ciasev.ubdd_service.entity.damage.DamageDetail;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.victim.Victim;
import uz.ciasev.ubdd_service.entity.violator.Violator;

public interface DamageMainService {

    DamageDetail addDamageToProtocol(User user, DamageCreateRequestDTO requestDTO);

    DamageDetail addGovernmentDamageToProtocol(User user, Protocol protocol, Violator violator, Long amount);

    DamageDetail addVictimDamageToProtocol(User user,
                                           Protocol protocol,
                                           Violator violator,
                                           Victim victim,
                                           DamageRequestDTO requestDTO);

    DamageDetail updateDamageDetail(User user, Long id, DamageUpdateRequestDTO requestDTO);

    DamageDetail deleteDamageDetail(User user, Long id, ChangeReasonRequestDTO reason);

    void recalculateDamageTotalAmount(Damage damage);

    void mergeTo(Damage damage, Violator toViolator, Victim toVictim);
}
