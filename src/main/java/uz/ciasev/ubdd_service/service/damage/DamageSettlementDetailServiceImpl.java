package uz.ciasev.ubdd_service.service.damage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.damage.DamageSettlementDetail;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.damage.DamageSettlementDetailRepository;

@Service
@RequiredArgsConstructor
public class DamageSettlementDetailServiceImpl implements DamageSettlementDetailService {
    private final DamageSettlementDetailRepository repository;

    @Override
    public DamageSettlementDetail getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityByIdNotFound(DamageSettlementDetail.class, id));
    }

    @Override
    public Violator findViolatorByDamageSettlementDetailId(Long id) {
        return repository.findViolatorByDamageSettlementDetailId(id).orElseThrow(() -> new EntityByIdNotFound(DamageSettlementDetail.class, id));
    }
}
