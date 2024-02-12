package uz.ciasev.ubdd_service.service.violation_event.build;

import uz.ciasev.ubdd_service.mvd_core.api.violation_event.dto.ViolationEventApiDTO;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolRequestDTO;

public interface ViolationEventProtocolBuildingService {

    ProtocolRequestDTO build(ViolationEventApiDTO violationEvent);
}
