package uz.ciasev.ubdd_service.service.court.files;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.court.CourtFile;
import uz.ciasev.ubdd_service.entity.court.CourtFile_;
import uz.ciasev.ubdd_service.repository.court.CourtFileRepository;
import uz.ciasev.ubdd_service.utils.PageUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourtFileServiceImpl implements CourtFileService {

    private final CourtFileRepository repository;

    @Override
    public CourtFile create(Long caseId, Long claimId, Long fileId, String fileUri) {
        CourtFile file = CourtFile.builder()
                .caseId(caseId)
                .claimId(claimId)
                .externalId(fileId)
                .uri(fileUri)
                .build();

        return repository.save(file);
    }

    @Override
    public List<CourtFile> getByCaseId(Long caseId) {
        return repository.findByCaseId(caseId, Sort.by(CourtFile_.CLAIM_ID).descending());
    }

    @Override
    public Optional<CourtFile> findLastByCaseIdAndClimeId(Long caseId, Long climeId) {
        return repository.findByCaseIdAndClaimId(caseId, climeId, PageUtils.oneWithMaxId())
                .stream()
                .findFirst();

    }
}
