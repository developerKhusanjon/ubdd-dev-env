package uz.ciasev.ubdd_service.service.violation_event.annulment;

import uz.ciasev.ubdd_service.dto.internal.request.violation_event.ViolationEventAnnulmentRequestDTO;
import uz.ciasev.ubdd_service.entity.violation_event.ViolationEventAnnulment;

public interface ViolationEventAnnulmentService {

    ViolationEventAnnulment create(ViolationEventAnnulmentRequestDTO requestDTO);
}
