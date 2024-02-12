package uz.ciasev.ubdd_service.service.resolution.compensation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;
import uz.ciasev.ubdd_service.repository.resolution.compensation.CompensationRepository;
import uz.ciasev.ubdd_service.service.status.StatusService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompensationServiceImpl implements CompensationService {

    private final CompensationRepository compensationRepository;
    private final StatusService admStatusService;

    @Override
    public List<Compensation> create(Decision decision, List<Compensation> compensations) {
        return compensations.stream()
                .map(compensation -> create(decision, compensation))
                .collect(Collectors.toList());
    }

    @Override
    public Compensation create(Decision decision, Compensation compensation) {
        validateGovVictimTypeExistence(decision.getId());
        compensation.setDecision(decision);
        compensation.setStatus(admStatusService.getStatus(compensation));
        compensation.setPaidAmount(0L);

        return compensationRepository.saveAndFlush(compensation);
    }

    @Override
    public Compensation findById(Long id) {
        return compensationRepository
                .findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(Compensation.class, id));
    }

    @Override
    public Decision findDecisionByCompensationId(Long id) {
        return compensationRepository
                .findDecisionByCompensationId(id)
                .orElseThrow(() -> new EntityByIdNotFound(Compensation.class, id));
    }

    @Override
    public Violator findViolatorByCompensationId(Long id) {
        return compensationRepository
                .findViolatorByCompensationId(id)
                .orElseThrow(() -> new EntityByIdNotFound(Compensation.class, id));
    }

    @Override
    public Optional<Compensation> findActiveGovByViolatorId(Long violatorId) {
        return compensationRepository.findActiveGovByViolatorId(violatorId);
    }

    @Override
    public Optional<Compensation> findActiveGovByViolator(Violator violator) {
        return compensationRepository.findActiveGovByViolatorId(violator.getId());
    }

    @Override
    public Optional<Compensation> findGovByViolatorId(Long resolutionId, Long violatorId) {
        return compensationRepository.findGovByViolatorId(violatorId, resolutionId);
    }

    @Override
    public Compensation getGovByViolatorId(Long resolutionId, Long violatorId) {
        return findGovByViolatorId(resolutionId, violatorId)
                .orElseThrow(() -> new EntityByParamsNotFound(Compensation.class, "violatorId", violatorId, "resolutionId", resolutionId));
    }

//    @Override
//    public void update(Long id, Compensation compensation) {
//        compensation.setId(id);
//        compensationRepository.saveAndFlush(compensation);
//    }

    @Override
    public List<Compensation> findAllByDecisionId(Long decisionId) {
        return compensationRepository.findAllByDecision(decisionId);
    }

    @Override
    public Optional<Compensation> findGovByDecisionId(Long decisionId) {
        return compensationRepository.findGovByDecision(decisionId);
    }

    @Override
    public Optional<Compensation> findGovByDecision(Decision decision) {
        return findGovByDecisionId(decision.getId());
    }

    private void validateGovVictimTypeExistence(Long decisionId) {
        if (findGovByDecisionId(decisionId).isPresent()) {
            throw new uz.ciasev.ubdd_service.exception.ValidationException(ErrorCode.COMPENSATION_ALREADY_EXIST, "Decision with id: " + decisionId + "already has \"Government victim typed compensation\"");
        }
    }

}
