package uz.ciasev.ubdd_service.repository.court;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.court.CourtCaseChancelleryData;

import java.util.Optional;

public interface CourtCaseChancelleryDataRepository extends JpaRepository<CourtCaseChancelleryData, Long> {

    Optional<CourtCaseChancelleryData> findByCaseIdAndClaimId(Long caseId, Long claimId);
}
