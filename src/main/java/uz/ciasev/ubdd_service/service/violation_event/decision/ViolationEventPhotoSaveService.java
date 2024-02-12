package uz.ciasev.ubdd_service.service.violation_event.decision;

import uz.ciasev.ubdd_service.mvd_core.api.violation_event.dto.ViolationEventApiDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.user.User;

interface ViolationEventPhotoSaveService {

    void save(User user, ViolationEventApiDTO event, AdmCase admCase);
}
