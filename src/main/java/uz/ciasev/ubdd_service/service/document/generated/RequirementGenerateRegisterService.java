package uz.ciasev.ubdd_service.service.document.generated;

import uz.ciasev.ubdd_service.dto.internal.RequirementPrintDTO;
import uz.ciasev.ubdd_service.entity.document.RequirementGeneration;
import uz.ciasev.ubdd_service.entity.user.User;

public interface RequirementGenerateRegisterService {

    RequirementGeneration createRegistration(User user, RequirementPrintDTO requestDTO);
}
