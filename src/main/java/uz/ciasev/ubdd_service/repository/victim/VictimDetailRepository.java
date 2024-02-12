package uz.ciasev.ubdd_service.repository.victim;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.victim.VictimDetail;

import java.util.List;
import java.util.Optional;

public interface VictimDetailRepository extends JpaRepository<VictimDetail, Long> {

    List<VictimDetail> findAllByProtocolId(Long protocolId);

    Optional<VictimDetail> findByVictimIdAndProtocolId(Long victimId, Long protocolId);

    List<VictimDetail> findByVictimId(Long victimId);

    boolean existsByVictimId(Long victimId);

    @Modifying
    @Query(value = "UPDATE VictimDetail SET victimId = :toVictimId WHERE victimId = :fromVictimId")
    void mergeAllTo(@Param("fromVictimId") Long fromVictimId, @Param("toVictimId") Long toVictimId);

    @Query("SELECT pd FROM VictimDetail pd WHERE pd.protocolId = :protocolId AND pd.victim.personId = :personId")
    Optional<VictimDetail> findByProtocolIdAndPersonId(@Param("protocolId") Long protocolId, @Param("personId") Long personId);
}
