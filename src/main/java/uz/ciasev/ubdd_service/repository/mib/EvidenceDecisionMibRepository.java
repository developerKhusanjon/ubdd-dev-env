package uz.ciasev.ubdd_service.repository.mib;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatus;
import uz.ciasev.ubdd_service.entity.mib.EvidenceDecisionMib;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;

import java.util.List;

public interface EvidenceDecisionMibRepository extends JpaRepository<EvidenceDecisionMib, Long> {

    @Query("SELECT DISTINCT c " +
            "FROM EvidenceDecisionMib cm " +
            " JOIN MibExecutionCard c ON cm.cardId = c.id " +
            "RIGHT JOIN EvidenceDecision d ON cm.evidenceDecisionId = d.id " +
            "WHERE cm.isActive = TRUE AND cm.evidenceDecisionId IN :ids")
    List<MibExecutionCard> findActivesByEvidenceDecisions(@Param("ids") List<Long> ids);

    @Query("SELECT DISTINCT edm.evidenceDecisionId " +
            "FROM EvidenceDecisionMib edm " +
            "WHERE edm.isActive = TRUE AND edm.cardId = :id")
    List<Long> findActivesByCard(@Param("id") Long id);

    @Modifying
    @Query("UPDATE EvidenceDecisionMib edm SET edm.isActive = FALSE " +
            " WHERE edm.evidenceDecisionId IN :ids")
    void closeAllByEvidenceDecisions(@Param("ids") List<Long> ids);

    @Query("SELECT DISTINCT mv.mibCaseStatus " +
            "FROM EvidenceDecisionMib edm " +
            "   INNER JOIN MibCardMovement mv " +
            "   ON edm.evidenceDecisionId = :edId " +
            "   AND mv.id <> :movementId " +
            "   AND edm.cardId = mv.cardId ")
    List<MibCaseStatus> findMovementStatuses(@Param("edId") Long edId, @Param("movementId") Long movementId);
}
