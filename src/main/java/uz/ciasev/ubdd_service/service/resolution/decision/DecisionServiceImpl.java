package uz.ciasev.ubdd_service.service.resolution.decision;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;
import uz.ciasev.ubdd_service.repository.resolution.decision.DecisionRepository;
import uz.ciasev.ubdd_service.service.generator.AdmDocumentNumber;
import uz.ciasev.ubdd_service.service.generator.DecisionNumberGeneratorService;

import uz.ciasev.ubdd_service.service.status.DecisionStatusCalculatingService;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DecisionServiceImpl implements DecisionService {

    private final DecisionRepository decisionRepository;
    private final DecisionStatusCalculatingService admStatusService;


    @Override
    public Decision create(Resolution resolution, DecisionNumberGeneratorService numberGeneratorService, Decision decision) {

        decision.setResolution(resolution);

        AdmDocumentNumber number = numberGeneratorService.generate(decision);
        decision.setSeriesAndNumber(number);

        decision.setStatus(admStatusService.getStartStatus(decision));

        return decisionRepository.saveAndFlush(decision);
    }

    @Override
    public List<Decision> findByResolutionId(Long resolutionId) {
        return decisionRepository.findByResolutionId(resolutionId);
    }

    @Override
    public List<AdmStatusAlias> findStatusAliasesByResolutionId(Long resolutionId) {
        return decisionRepository.findStatusAliasesByResolutionId(resolutionId);
    }

    @Override
    public Optional<Decision> findActiveByViolatorId(Long violatorId) {
        return decisionRepository.findActiveByViolatorId(violatorId);
    }

    @Override
    public Optional<Decision> findByResolutionAndViolatorIds(Long resolutionId, Long violatorId) {
        return decisionRepository.findByResolutionIdAndViolatorId(resolutionId, violatorId);
    }

    @Override
    public Decision getByResolutionAndViolatorIds(Long resolutionId, Long violatorId) {
        return findByResolutionAndViolatorIds(resolutionId, violatorId)
                .orElseThrow(() -> new EntityByParamsNotFound(Decision.class, "resolutionId", resolutionId, "violatorId", violatorId));

    }

    @Override
    public Decision getById(Long id) {
        return decisionRepository
                .findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(Decision.class, id));
    }

    @Override
    public Optional<Decision> findById(Long id) {
        return decisionRepository.findById(id);
    }

    @Override
    public void checkExistById(Long id) {
        if (!decisionRepository.existsById(id)) {
            throw new EntityByIdNotFound(Decision.class, id);
        }
    }

    @Override
    public Optional<Decision> findBySeriesAndNumber(String series, String number) {
        List<Decision> rsl = decisionRepository.findBySeriesAndNumber(series, number);
        if (rsl.size() > 1) {
            log.error("FILED UNIQUELY IDENTIFY DECISION BY SERIES AND NUMBER ({}, {})", series, number);
            throw new ValidationException(ErrorCode.FILED_UNIQUELY_IDENTIFY_DECISION_BY_SERIES_AND_NUMBER);
        }
        return rsl.stream().findFirst();
    }

    private void saveStatus(Decision decision) {
        decisionRepository.saveStatus(decision.getId(), decision.getStatus().getId());
    }

}
