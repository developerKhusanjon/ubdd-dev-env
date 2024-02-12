package uz.ciasev.ubdd_service.service.violation_event.build;

import uz.ciasev.ubdd_service.mvd_core.api.violation_event.dto.ViolationEventApiDTO;

public interface ViolationTypeDescriptionBuildingService {

    String getViolationTypeDescription(ViolationEventApiDTO violationEventApiDTO);
}
