package uz.ciasev.ubdd_service.repository.resolution.compensation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.violator.Violator;

import java.util.List;
import java.util.Optional;

public interface CompensationRepository extends JpaRepository<Compensation, Long> {

    @Query("SELECT c " +
            " FROM Compensation c " +
            "WHERE c.decision.violatorId = :violatorId " +
            "  AND c.decision.resolutionId = :resolutionId " +
            "  AND c.victimType.alias = 'GOVERNMENT' ")
    Optional<Compensation> findGovByViolatorId(Long violatorId, Long resolutionId);

    @Query("SELECT c " +
            " FROM Compensation c " +
            "WHERE c.decision.violatorId = :violatorId " +
            "  AND c.victimType.alias = 'GOVERNMENT' " +
            "  AND c.decision.resolution.isActive = TRUE")
    Optional<Compensation> findActiveGovByViolatorId(Long violatorId);


    @Modifying
    @Query("UPDATE Compensation c SET victimId = :newId WHERE c.victimId = :oldId")
    void changVictimId(@Param("oldId") Long oldId, @Param("newId") Long newId);


    @Query("SELECT c.decision " +
            " FROM Compensation c " +
            "WHERE c.id = :id ")
    Optional<Decision> findDecisionByCompensationId(Long id);


    @Query("SELECT c.decision.violator " +
            " FROM Compensation c " +
            "WHERE c.id = :id ")
    Optional<Violator> findViolatorByCompensationId(Long id);

    @Query("SELECT c " +
            " FROM Compensation c " +
            "WHERE c.decision.id = :decisionId ")
    List<Compensation> findAllByDecision(Long decisionId);

    @Query("SELECT c " +
            " FROM Compensation c " +
            "WHERE c.decision.id = :decisionId " +
            "  AND c.victimType.alias = 'GOVERNMENT' ")
    Optional<Compensation> findGovByDecision(Long decisionId);

    @Query("SELECT c.decision.resolution.admCase.organ " +
            " FROM Compensation c " +
            "WHERE c = :compensation ")
    Organ findAdmCaseOrganByCompensation(Compensation compensation);

    @Query("SELECT c.decision.resolution.admCase " +
            " FROM Compensation c " +
            "WHERE c = :compensation ")
    AdmCase findAdmCaseByCompensation(Compensation compensation);

    @Query("SELECT c.decision.resolution.admCase.id " +
            " FROM Compensation c " +
            "WHERE c = :compensation ")
    Long findAdmCaseIdByCompensation(Compensation compensation);

    @Query("SELECT c.decision.violator.id " +
            " FROM Compensation c " +
            "WHERE c = :compensation ")
    Long findViolatorIdByCompensation(Compensation compensation);

}
