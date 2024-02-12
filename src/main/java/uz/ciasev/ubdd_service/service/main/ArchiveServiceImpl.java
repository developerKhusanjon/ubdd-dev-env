package uz.ciasev.ubdd_service.service.main;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.repository.resolution.decision.DecisionRepository;
import uz.ciasev.ubdd_service.repository.violator.ViolatorRepository;
import uz.ciasev.ubdd_service.specifications.DecisionSpecifications;
import uz.ciasev.ubdd_service.specifications.SpecificationsCombiner;
import uz.ciasev.ubdd_service.specifications.ViolatorSpecifications;
import uz.ciasev.ubdd_service.utils.PageUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArchiveServiceImpl implements ArchiveService {

    private final ViolatorRepository violatorRepository;
    private final ViolatorSpecifications violatorSpecifications;
    private final DecisionRepository decisionRepository;
    private final DecisionSpecifications decisionSpecifications;

    @Override
    @Transactional
    public void returnFromArchive(Violator violator) {
        violatorRepository.setArchive(List.of(violator.getId()), false, null);
    }

    @Override
    @Transactional
    public void sendToArchive(Violator violator) {
        violatorRepository.setArchive(List.of(violator.getId()), true, LocalDate.now());
    }

    @Override
    @Transactional
    public void sendToArchiveAll(List<Long> violators) {
        violatorRepository.setArchive(violators, true, LocalDate.now());
    }

    @Override
    public List<Long> findNextNForSendToArchive(int n) {
        return violatorRepository.findAllIdList(
                SpecificationsCombiner.andAll(
                        violatorSpecifications.withIsArchived(false),
                        violatorSpecifications.withDecisionSpec(SpecificationsCombiner.andAll(
                                decisionSpecifications.withIsActive(true),
                                decisionSpecifications.withExecutedDateDateBefore(LocalDate.now().minusYears(1))
                        ))
                ),
                PageUtils.limit(n)
        );
    }

    @Override
    public Optional<Violator> findNextForSendToArchive() {
        return decisionRepository.findFirstId(
                        decisionSpecifications.activeOnly().and(
                                decisionSpecifications.withExecutedDateDateBefore(LocalDate.now().minusYears(1))
                        ),
                        Sort.unsorted()
                ).stream()
                .findFirst()
                .map(decisionRepository::findViolatorByDecisionId);
    }
}