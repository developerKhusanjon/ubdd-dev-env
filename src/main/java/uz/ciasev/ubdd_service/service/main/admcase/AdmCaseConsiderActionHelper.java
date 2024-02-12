package uz.ciasev.ubdd_service.service.main.admcase;

import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseConsiderRequestDTO;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.user.User;

public interface AdmCaseConsiderActionHelper {

    void apply(User user, AdmCase admCase, AdmCaseConsiderRequestDTO requestDTO);

    ActionAlias getActionName(User user, AdmCase admCase, AdmCaseConsiderRequestDTO requestDTO);

}
