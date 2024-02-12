package uz.ciasev.ubdd_service.service.damage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.damage.DamageSettlement;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.damage.DamageSettlementRepository;

@Service
@RequiredArgsConstructor
public class DamageSettlementServiceImpl implements DamageSettlementService {
    private final DamageSettlementRepository repository;

    @Override
    public DamageSettlement getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityByIdNotFound(DamageSettlement.class, id));
    }
}
