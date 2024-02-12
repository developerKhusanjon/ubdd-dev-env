package uz.ciasev.ubdd_service.service.main.admcase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseDeletionDeclineRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseDeletionProposalRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseDeletionRequestDTO;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRegistration;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRequest;
import uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseDeletionRequestStatusAlias;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.AdmCaseDeletionRequestStatusNoSuitableException;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.repository.admcase.deletion.AdmCaseDeletionRequestRepository;
import uz.ciasev.ubdd_service.repository.protocol.ProtocolRepository;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.admcase.deletion.AdmCaseDeletionRegistrationService;
import uz.ciasev.ubdd_service.service.admcase.deletion.AdmCaseDeletionRequestService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.aop.signature.DigitalSignatureCheck;
import uz.ciasev.ubdd_service.entity.signature.SignatureEvent;
import uz.ciasev.ubdd_service.service.dict.AdmCaseDeletionReasonService;

import static uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseDeletionRequestStatusAlias.*;

@Service
@RequiredArgsConstructor
public class AdmCaseDeletionServiceImpl implements AdmCaseDeletionService {

    private final AdmCaseService admCaseService;
    private final AdmCaseAccessService admCaseAccessService;
    private final AdmCaseDeletionRegistrationService admCaseDeletionRegistrationService;
    private final ProtocolRepository protocolRepository;
    private final AdmCaseDeletionRequestService admCaseDeletionRequestService;
    private final AdmCaseDeletionReasonService admCaseDeletionReasonService;
    private final AdmCaseDeletionRequestRepository requestRepository;

    @Override
    @Transactional
    @DigitalSignatureCheck(event = SignatureEvent.ADM_CASE_DELETE)
    public AdmCase deleteAdmCase(User user, Long id, AdmCaseDeletionRequestDTO reasonDTO) {

        AdmCase admCase = admCaseService.getById(id);

        admCaseAccessService.checkAccessibleUserActionWithAdmCase(user, ActionAlias.DELETE_ADM_CASE, admCase);

        delete(admCase, user, reasonDTO);

        return admCase;
    }

    @Override
    @Transactional
    public void recoverAdmCase(User user, Long id) {
        AdmCase admCase = admCaseService.getById(id);
        admCaseAccessService.checkAccessOnAdmCase(user, admCase);

        AdmCaseDeletionRegistration registration = admCaseDeletionRegistrationService.getActiveByAdmCase(admCase);
        recovery(user, admCase, registration);
    }

    @Override
    @Transactional
    @DigitalSignatureCheck(event = SignatureEvent.ADM_CASE_DELETE_REQUEST_APPROVE)
    public AdmCaseDeletionRequest approveDeletionRequest(User admin, Long id) {
        AdmCaseDeletionRequest deletionRequest = admCaseDeletionRequestService.getById(id);
        checkStatusForAction(deletionRequest, CREATED, "approve");

        AdmCase admCase = admCaseService.getById(deletionRequest.getAdmCaseId());
        admCaseAccessService.checkAccessibleUserActionWithAdmCase(admin, ActionAlias.APPROVE_ADM_CASE_DELETION_REQUEST, admCase);

        AdmCaseDeletionRegistration deletionRegistration = delete(admCase, deletionRequest.getUser(), buildDeletionRequest(deletionRequest));
        admCaseDeletionRequestService.makeApproved(admin, deletionRequest, deletionRegistration);

        return deletionRequest;
    }

    @Override
    @Transactional
    public void cancelApproveDeletionRequest(User admin, Long id) {
        AdmCaseDeletionRequest deletionRequest = admCaseDeletionRequestService.getById(id);
        checkStatusForAction(deletionRequest, APPROVED, "cancel approve");

        AdmCase admCase = admCaseService.getById(deletionRequest.getAdmCaseId());
        admCaseAccessService.checkAccessOnAdmCase(admin, admCase);

        AdmCaseDeletionRegistration registration = admCaseDeletionRegistrationService.getById(deletionRequest.getRegistrationId());
        recovery(admin, admCase, registration);
        admCaseDeletionRequestService.makeApproveCanceled(admin, deletionRequest);
    }

    @Override
    @Transactional
    public AdmCaseDeletionRequest requestAdmCaseDeletion(User user, AdmCaseDeletionProposalRequestDTO requestDTO) {
        admCaseDeletionRequestService.checkForPreExistingRequest(requestDTO.getAdmCaseId());

        AdmCase admCase = admCaseService.getById(requestDTO.getAdmCaseId());
        admCaseAccessService.checkAccessibleUserActionWithAdmCase(user, ActionAlias.REQUEST_ADM_CASE_DELETION, admCase);

        AdmCaseDeletionRequest deletionRequest = new AdmCaseDeletionRequest(
                admCase,
                user,
                requestDTO.getAdmCaseDeletionReason(),
                requestDTO.getDocumentBaseUri(),
                requestDTO.getSignature()
        );
        requestRepository.save(deletionRequest);

        return deletionRequest;
    }

    @Override
    public void declineDeletionRequest(User admin, Long id, AdmCaseDeletionDeclineRequestDTO requestDTO) {
        AdmCaseDeletionRequest deletionRequest = admCaseDeletionRequestService.getById(id);

        checkStatusForAction(deletionRequest, CREATED, "decline");

        AdmCase admCase = admCaseService.getById(deletionRequest.getAdmCaseId());
        admCaseAccessService.checkAccessOnAdmCase(admin, admCase);

        admCaseDeletionRequestService.makeDeclined(admin, deletionRequest, requestDTO);
    }

    private void recovery(User actor, AdmCase admCase, AdmCaseDeletionRegistration registration) {
        if (!admCase.isDeleted()) {
            throw new ValidationException(ErrorCode.RECOVER_NOT_DELETED_ADM_CASE_UNACCEPTABLE);
        }

        protocolRepository.setDeletedForAllByAdmCaseId(admCase.getId(), false);

        admCase.setDeleted(false);
        admCaseService.update(admCase.getId(), admCase);

        admCaseDeletionRegistrationService.registerRecovery(actor, registration);
    }

    private AdmCaseDeletionRegistration delete(AdmCase admCase, User actor, AdmCaseDeletionRequestDTO reasonDTO) {
        if (admCase.isDeleted()) {
            throw new ValidationException(ErrorCode.DELETE_DELETED_ADM_CASE_UNACCEPTABLE);
        }

        protocolRepository.setDeletedForAllByAdmCaseId(admCase.getId(), true);

        admCase.setDeleted(true);
        admCaseService.update(admCase.getId(), admCase);

        return admCaseDeletionRegistrationService.registerDeletion(admCase, actor, reasonDTO);

    }

    private void checkStatusForAction(AdmCaseDeletionRequest deletionRequest, AdmCaseDeletionRequestStatusAlias needStatus, String actionName) {
        if (!needStatus.equals(deletionRequest.getStatus())) {
            throw new AdmCaseDeletionRequestStatusNoSuitableException(deletionRequest, actionName, needStatus);
        }
    }

    private AdmCaseDeletionRequestDTO buildDeletionRequest(AdmCaseDeletionRequest deletionRequest) {
        AdmCaseDeletionRequestDTO rsl = new AdmCaseDeletionRequestDTO();
        rsl.setReason(admCaseDeletionReasonService.getById(deletionRequest.getDeleteReasonId()));
        rsl.setDocumentBaseUri(deletionRequest.getDocumentBaseUri());
        rsl.setSignature(deletionRequest.getSignature());
        return rsl;
    }
}
