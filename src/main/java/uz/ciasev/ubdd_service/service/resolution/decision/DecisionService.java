package uz.ciasev.ubdd_service.service.resolution.decision;

import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.service.generator.DecisionNumberGeneratorService;

import java.util.List;
import java.util.Optional;

public interface DecisionService {

    Decision create(Resolution resolution, DecisionNumberGeneratorService numberGeneratorService, Decision decision);

    Decision getByResolutionAndViolatorIds(Long resolutionId, Long violatorId);

    Decision getById(Long id);

    Optional<Decision> findById(Long id);

    void checkExistById(Long id);

    List<Decision> findByResolutionId(Long resolutionId);

    List<AdmStatusAlias> findStatusAliasesByResolutionId(Long resolutionId);

    Optional<Decision> findActiveByViolatorId(Long violator);

    Optional<Decision> findByResolutionAndViolatorIds(Long resolutionId, Long violatorId);

    Optional<Decision> findBySeriesAndNumber(String series, String number);
}
