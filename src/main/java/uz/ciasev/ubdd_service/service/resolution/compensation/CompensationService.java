package uz.ciasev.ubdd_service.service.resolution.compensation;

import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.violator.Violator;

import java.util.List;
import java.util.Optional;

@Validated
public interface CompensationService {

    List<Compensation> create(Decision decision, List<Compensation> compensations);

    Compensation create(Decision decision, Compensation compensation);

    Compensation findById(Long id);

    Decision findDecisionByCompensationId(Long id);

    Violator findViolatorByCompensationId(Long id);

    Optional<Compensation> findActiveGovByViolatorId(Long violatorId);

    Optional<Compensation> findActiveGovByViolator(Violator violator);

    Optional<Compensation> findGovByViolatorId(Long resolutionId, Long violatorId);

    Compensation getGovByViolatorId(Long resolutionId, Long violatorId);

    List<Compensation> findAllByDecisionId(Long decisionId);

    Optional<Compensation> findGovByDecisionId(Long decisionId);

    Optional<Compensation> findGovByDecision(Decision decision);
}
