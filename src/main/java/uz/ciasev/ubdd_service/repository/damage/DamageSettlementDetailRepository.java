package uz.ciasev.ubdd_service.repository.damage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.damage.DamageSettlementDetail;
import uz.ciasev.ubdd_service.entity.violator.Violator;

import java.util.Optional;

public interface DamageSettlementDetailRepository extends JpaRepository<DamageSettlementDetail, Long> {

    @Query("SELECT dsd.damageSettlement.violator " +
            "FROM DamageSettlementDetail dsd " +
            "WHERE dsd.id = :damageSettlementDetailId ")
    Optional<Violator> findViolatorByDamageSettlementDetailId(Long damageSettlementDetailId);
}
