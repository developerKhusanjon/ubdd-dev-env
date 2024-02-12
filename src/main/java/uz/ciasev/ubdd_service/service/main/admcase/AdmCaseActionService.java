package uz.ciasev.ubdd_service.service.main.admcase;

import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseConsiderRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseMoveConsiderTimeRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseTransferResponsibilityRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.admcase.AdmCaseMergeResponseDTO;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.utils.validator.Inspector;

public interface AdmCaseActionService {

    AdmCaseMergeResponseDTO mergeAdmCases(@Inspector User user, Long fromCaseId, Long toCaseId);

    void considerAdmCase(User user, Long admCaseId, AdmCaseConsiderRequestDTO requestDTO);

    void moveConsiderTime(User user, Long admCaseId, AdmCaseMoveConsiderTimeRequestDTO requestDTO);

    void transferResponsibility(User user, Long admCaseId, AdmCaseTransferResponsibilityRequestDTO requestDTO);
}
