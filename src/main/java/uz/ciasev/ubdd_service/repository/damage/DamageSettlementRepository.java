package uz.ciasev.ubdd_service.repository.damage;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.damage.DamageSettlement;

public interface DamageSettlementRepository extends JpaRepository<DamageSettlement, Long> {
}
