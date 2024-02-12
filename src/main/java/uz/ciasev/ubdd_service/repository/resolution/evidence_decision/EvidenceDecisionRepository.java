package uz.ciasev.ubdd_service.repository.resolution.evidence_decision;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.resolution.evidence_decision.EvidenceDecision;

import java.util.List;
import java.util.Optional;

public interface EvidenceDecisionRepository extends JpaRepository<EvidenceDecision, Long> {


    @Query("SELECT c " +
            " FROM EvidenceDecision c " +
            " JOIN Resolution r ON r.id = c.resolutionId " +
            "WHERE (r.admCaseId = :admCaseId OR :admCaseId = -1) " +
            " AND (r.id = :resolutionId OR :resolutionId = -1) " +
            " AND (r.isActive = :isActive OR :isActive is NULL)")
    List<EvidenceDecision> findAllByFilter(@Param("admCaseId") long admCaseId,
                                           @Param("resolutionId") long resolutionId,
                                           @Param("isActive") Boolean isActive);

    @Query("SELECT e " +
            " FROM EvidenceDecision e " +
            "WHERE e.evidenceSudId = :evidenceCourtId " +
            "  AND e.resolution.claimId = :resolutionClaimId " +
            "  AND e.resolution.isActive = TRUE")
    Optional<EvidenceDecision> findByEvidenceCourtId(Long resolutionClaimId, Long evidenceCourtId);

    @Modifying
    @Query(value = "UPDATE EvidenceDecision ed SET statusId = :statusId WHERE ed.id IN :ids", nativeQuery = true)
    void updateAllStatusByIds(@Param("ids") List<Long> ids, @Param("statusId") Long statusId);

    List<EvidenceDecision> findByResolutionId(Long resolutionId);
}
