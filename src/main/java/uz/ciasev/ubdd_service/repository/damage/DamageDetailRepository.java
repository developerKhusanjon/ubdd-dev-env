package uz.ciasev.ubdd_service.repository.damage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.damage.DamageDetail;

import java.util.List;

public interface DamageDetailRepository extends JpaRepository<DamageDetail, Long> {

    List<DamageDetail> findAllByProtocolId(Long protocolId);

    List<DamageDetail> findAllByDamageId(Long damageId);

    @Query("SELECT d " +
            " FROM DamageDetail d " +
            "WHERE (d.damageId = :damageId OR :damageId IS NULL) " +
            "   AND (d.protocolId = :protocolId OR :protocolId IS NULL) ")
    List<DamageDetail> AllByDamageIdAndProtocolId(Long damageId, Long protocolId);

    @Modifying
    @Query(value = "UPDATE DamageDetail SET damageId = :toDamageId WHERE damageId = :fromDamageId")
    void mergeAllTo(@Param("fromDamageId") Long fromDamageId, @Param("toDamageId") Long toDamageId);

    @Query("SELECT dd FROM DamageDetail dd " +
            "WHERE dd.protocolId = :protocolId " +
            "AND dd.damage.victimId = :victimId")
    List<DamageDetail> findByProtocolIdAndVictimId(@Param("protocolId") Long protocolId,
                                                   @Param("victimId") Long victimId);
}
