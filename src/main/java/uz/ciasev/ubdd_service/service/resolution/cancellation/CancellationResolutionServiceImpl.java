package uz.ciasev.ubdd_service.service.resolution.cancellation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.court.CourtFile;
import uz.ciasev.ubdd_service.entity.resolution.cancellation.CancellationResolution;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.repository.resolution.cancellation.CancellationResolutionRepository;
import uz.ciasev.ubdd_service.specifications.CancellationResolutionSpecifications;
import uz.ciasev.ubdd_service.utils.PageUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CancellationResolutionServiceImpl implements CancellationResolutionService {

    private final CancellationResolutionRepository repository;
    private final CancellationResolutionSpecifications specifications;

    @Override
    public CancellationResolution create(Resolution resolution, CancellationResolution cancellationResolution) {
        cancellationResolution.setResolution(resolution);
        return repository.save(cancellationResolution);
    }

    @Override
    public void setCourtFile(CancellationResolution cancellation, CourtFile file) {
        cancellation.setFileUri(file.getUri());
        repository.save(cancellation);
    }

    @Override
    public Optional<CancellationResolution> findByResolutionId(Long resId) {
        return repository.findByResolutionId(resId, PageUtils.oneWithMaxId()).stream().findFirst();
    }

    @Override
    public List<CancellationResolution> findByCaseAndClaimIds(Long caseId, Long claimId) {
        return repository.findAllByCaseAndClaimIds(caseId, claimId);
    }

    @Override
    public List<CancellationResolution> findAllByResolutionId(Long resolutionId) {
        return repository.findAllByResolutionIdOrderByIdDesc(resolutionId);
    }

    @Override
    public Optional<CancellationResolution> findLastByCaseId(Long caseId) {
        return repository.findAll(specifications.withAdmCaseId(caseId), PageUtils.oneWithMaxId()).stream().findFirst();
    }
}
