package uz.ciasev.ubdd_service.service;

import uz.ciasev.ubdd_service.dto.internal.request.ChangeReasonRequestDTO;
import uz.ciasev.ubdd_service.entity.AdmCaseChangeReason;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.user.User;

public interface AdmCaseChangeReasonService {

    AdmCaseChangeReason create(AdmCaseChangeReason admCaseChangeReason);

    AdmCaseChangeReason create(User user, AdmCase admCase, AdmEntity entity, ChangeReasonRequestDTO changeReasonRequestDTO);
}
