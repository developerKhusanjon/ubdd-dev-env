package uz.ciasev.ubdd_service.service.violation_event.decision;

import uz.ciasev.ubdd_service.dto.internal.request.violation_event.ViolationEventDecisionRequestDTO;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.user.User;

public interface ViolationEventDecisionMadeService {

    Decision resolve(User user, ViolationEventDecisionRequestDTO requestDTO);
}
