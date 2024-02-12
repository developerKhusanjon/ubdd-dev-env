package uz.ciasev.ubdd_service.service.violation_event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violation_event.ViolationEventAnnulment;
import uz.ciasev.ubdd_service.entity.violation_event.ViolationEventResult;
import uz.ciasev.ubdd_service.repository.violation_event.ViolationEventResultRepository;
import uz.ciasev.ubdd_service.specifications.ViolationEventResultSpecifications;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViolationEventResultServiceImpl implements ViolationEventResultService {
    private final ViolationEventResultRepository repository;
    private final ViolationEventResultSpecifications spec;


    @Override
    public Optional<ViolationEventResult> findActiveByViolationEventId(Long violationEventId) {
        return repository.findOne(
                spec.withIsActive(true).and(spec.withViolationEventId(violationEventId))
        );
    }

    @Override
    @Transactional
    public ViolationEventResult create(User user, Long violationEventId, ViolationEventAnnulment annulment) {
        ViolationEventResult r = new ViolationEventResult(user, violationEventId, annulment);
        return create(r);
    }

    @Override
    @Transactional
    public ViolationEventResult create(User user, Long violationEventId, Decision decision) {
        ViolationEventResult r = new ViolationEventResult(user, violationEventId, decision);
        return create(r);
    }

    private ViolationEventResult create(ViolationEventResult r) {
        repository.setActiveToFalseForAllByViolationEventId(r.getViolationEventId());
        repository.save(r);
        return r;
    }
}
