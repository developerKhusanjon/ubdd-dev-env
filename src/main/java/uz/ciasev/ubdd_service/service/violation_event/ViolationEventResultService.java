package uz.ciasev.ubdd_service.service.violation_event;

import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violation_event.ViolationEventAnnulment;
import uz.ciasev.ubdd_service.entity.violation_event.ViolationEventResult;

import java.util.Optional;

public interface ViolationEventResultService {

    Optional<ViolationEventResult> findActiveByViolationEventId(Long violationEventId);

    ViolationEventResult create(User user, Long violationEventId, ViolationEventAnnulment annulment);
    ViolationEventResult create(User user, Long violationEventId, Decision decision);
}
