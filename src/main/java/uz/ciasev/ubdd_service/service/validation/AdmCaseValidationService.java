package uz.ciasev.ubdd_service.service.validation;

import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseConsiderRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseCourtDeclarationRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseMoveConsiderTimeRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseSendRequestDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseMovement;
import uz.ciasev.ubdd_service.entity.user.User;

public interface AdmCaseValidationService {

    void validateSend(User user, AdmCase admCase, AdmCaseSendRequestDTO sendRequestDTO);

    void validateInterSend(User user,
                           AdmCase admCase,
                           AdmCaseSendRequestDTO sendRequestDTO);

    void validateConsider(User user, AdmCase admCase, AdmCaseConsiderRequestDTO requestDTO);

    void validateMoveConsiderTime(User user, AdmCase admCase, AdmCaseMoveConsiderTimeRequestDTO requestDTO);

    void validateCourtDeclaration(User user, AdmCase admCase, AdmCaseCourtDeclarationRequestDTO requestDTO);

    void validateMovementCancellation(AdmCaseMovement movement, User user);
}
