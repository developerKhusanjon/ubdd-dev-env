package uz.ciasev.ubdd_service.service.main.admcase;

import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseDeletionDeclineRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseDeletionProposalRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseDeletionRequestDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRequest;
import uz.ciasev.ubdd_service.entity.user.User;

public interface AdmCaseDeletionService {

    AdmCase deleteAdmCase(User user, Long id, AdmCaseDeletionRequestDTO reasonDTO);

    void recoverAdmCase(User user, Long id);

    AdmCaseDeletionRequest approveDeletionRequest(User admin, Long id);

    void cancelApproveDeletionRequest(User admin, Long id);

    AdmCaseDeletionRequest requestAdmCaseDeletion(User user, AdmCaseDeletionProposalRequestDTO requestDTO);

    void declineDeletionRequest(User user, Long id, AdmCaseDeletionDeclineRequestDTO requestDTO);
}
