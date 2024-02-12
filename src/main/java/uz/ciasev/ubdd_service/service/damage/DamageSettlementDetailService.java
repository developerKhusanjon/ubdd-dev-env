package uz.ciasev.ubdd_service.service.damage;

import uz.ciasev.ubdd_service.entity.damage.DamageSettlementDetail;
import uz.ciasev.ubdd_service.entity.violator.Violator;

public interface DamageSettlementDetailService {
    DamageSettlementDetail getById(Long id);
    Violator findViolatorByDamageSettlementDetailId(Long id);
}
