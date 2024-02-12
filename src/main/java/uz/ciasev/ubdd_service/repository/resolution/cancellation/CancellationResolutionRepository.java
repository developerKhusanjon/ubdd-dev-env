package uz.ciasev.ubdd_service.repository.resolution.cancellation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.resolution.cancellation.CancellationResolution;

import java.util.List;

public interface CancellationResolutionRepository extends JpaRepository<CancellationResolution, Long>, JpaSpecificationExecutor<CancellationResolution> {

    Page<CancellationResolution> findByResolutionId(Long resolutionId, Pageable pageable);

    @Query("SELECT cr FROM CancellationResolution cr WHERE cr.resolution.admCase.id = :caseId AND cr.claimId = :claimId")
    List<CancellationResolution> findAllByCaseAndClaimIds(Long caseId, Long claimId);

    List<CancellationResolution> findAllByResolutionIdOrderByIdDesc(Long resolutionId);
}
