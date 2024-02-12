package uz.ciasev.ubdd_service.service.admcase;

import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseCourtDeclarationRequestDTO;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.user.User;

public interface CourtPreparingService {

    void startPreparing(User user, Long admCaseId);

    void updateCourtDeclaration(User user, Long admCaseId, AdmCaseCourtDeclarationRequestDTO requestDTO);

    void validateCourtSend(User user, Long admCaseId, ActionAlias actionAlias);
}
