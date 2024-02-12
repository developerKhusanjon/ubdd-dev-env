package uz.ciasev.ubdd_service.service.resolution.cancellation;

import uz.ciasev.ubdd_service.entity.court.CourtFile;
import uz.ciasev.ubdd_service.entity.resolution.cancellation.CancellationResolution;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;

import java.util.List;
import java.util.Optional;

public interface CancellationResolutionService {

    CancellationResolution create(Resolution resolution, CancellationResolution cancellationResolution);

    void setCourtFile(CancellationResolution cancellationResolution, CourtFile file);

    Optional<CancellationResolution> findByResolutionId(Long resId);

    List<CancellationResolution> findByCaseAndClaimIds(Long caseId, Long claimId);

    List<CancellationResolution> findAllByResolutionId(Long resId);

    Optional<CancellationResolution> findLastByCaseId(Long caseId);
}
