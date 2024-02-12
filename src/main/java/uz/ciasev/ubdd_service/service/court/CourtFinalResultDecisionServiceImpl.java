package uz.ciasev.ubdd_service.service.court;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.court.CourtFinalResultDecision;
import uz.ciasev.ubdd_service.entity.dict.court.InstancesAliases;
import uz.ciasev.ubdd_service.repository.court.CourtFinalResultDecisionRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourtFinalResultDecisionServiceImpl implements CourtFinalResultDecisionService {

    private final CourtFinalResultDecisionRepository courtFinalResultDecisionRepository;

    @Override
    public CourtFinalResultDecision save(CourtFinalResultDecision finalResult) {
        return courtFinalResultDecisionRepository.save(finalResult);
    }

    @Override
    public List<InstancesAliases> findByCaseId(Long id) {
        return courtFinalResultDecisionRepository
                .findByCaseId(id)
                .stream()
                .filter(Objects::nonNull)
                .map(CourtFinalResultDecision::getInstance)
                .collect(Collectors.toList());
    }
}
