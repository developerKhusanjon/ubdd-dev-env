package uz.ciasev.ubdd_service.repository.damage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.damage.Damage;

import java.util.List;
import java.util.Optional;


public interface DamageRepository extends JpaRepository<Damage, Long> {

    Optional<Damage> findByViolatorIdAndVictimTypeIdAndVictimId(Long violatorId, Long victimTypeId, Long victimId);

    @Query("SELECT d " +
            " FROM Damage d " +
            "WHERE d.violatorId IN (" +
            "   SELECT v.id " +
            "     FROM Violator v " +
            "    WHERE v.admCaseId = :admCaseId) " +
            "  AND d.isActive = TRUE")
    List<Damage> findByAdmCaseId(@Param("admCaseId") Long admCaseId);

    @Query("SELECT d " +
            " FROM Damage d " +
            " JOIN Violator v " +
            "   ON d.violatorId = v.id " +
            "WHERE d.violatorId = :violatorId " +
            "  AND v.admCaseId = :admCaseId " +
            "  AND d.isActive = TRUE")
    List<Damage> findByViolatorAndAdmCaseIds(@Param("admCaseId") Long admCaseId,
                                             @Param("violatorId") Long violatorId);

    List<Damage> findByVictimId(Long victimId);

    List<Damage> findByViolatorId(Long violatorId);

}
