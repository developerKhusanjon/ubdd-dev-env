package uz.ciasev.ubdd_service.service.violation_event.annulment;

import uz.ciasev.ubdd_service.dto.internal.request.violation_event.ViolationEventAnnulmentRequestDTO;
import uz.ciasev.ubdd_service.entity.user.User;

public interface ViolationEventAnnulmentMadeService {

    void annul(User user, ViolationEventAnnulmentRequestDTO requestDTO);
}
