package uz.ciasev.ubdd_service.service.violation_event.decision;

import uz.ciasev.ubdd_service.mvd_core.api.violation_event.dto.ViolationEventApiDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedSingleResolutionDTO;


interface ResolutionForViolationEventCreateService {
    CreatedSingleResolutionDTO create(User user, ViolationEventApiDTO violationEvent, AdmCase admCase, Protocol protocol);

}
