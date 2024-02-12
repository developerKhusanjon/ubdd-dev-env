package uz.ciasev.ubdd_service.repository.court;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.dto.internal.response.court.CourtAdmCaseMovementCountResponse;
import uz.ciasev.ubdd_service.entity.court.CourtAdmCaseMovement;

import java.util.List;

public interface CourtAdmCaseMovementRepository extends JpaRepository<CourtAdmCaseMovement, Long> {

    @Query("SELECT m, " +
            "CASE " +
            "  WHEN m.statusTime IS NULL THEN m.createdTime " +
            "  ELSE m.statusTime " +
            " END AS t " +
            "FROM CourtAdmCaseMovement m " +
            "WHERE m.caseId = :caseId " +
            "ORDER BY t DESC ")
    List<CourtAdmCaseMovement> findAllByCaseIdOrderByTimeDesc(Long caseId);

    List<CourtAdmCaseMovement> findAllByCaseIdOrderByCreatedTimeDesc(Long caseId);

    List<CourtAdmCaseMovement> findAllByCaseIdAndClaimId(Long caseId, Long claimId);

    List<CourtAdmCaseMovement> findByCaseIdAndClaimIdAndStatusIdIn(Long caseId, Long claimId, List<Long> statusesIs, Pageable pageable);

    @Query("SELECT COUNT(distinct m.claimId) as sentCount FROM CourtAdmCaseMovement m WHERE m.caseId = :caseId and m.isSentToCourt = true")
    CourtAdmCaseMovementCountResponse countByCaseId(Long caseId);
}
