package uz.ciasev.ubdd_service.service.admcase.deletion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseDeletionDeclineRequestDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRegistration;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRequest;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRequestProjection;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.validation.constraints.NotNull;
import java.util.Map;


public interface AdmCaseDeletionRequestService {

    AdmCaseDeletionRequest getById(Long id);

    void checkForPreExistingRequest(Long admCaseId);

    void makeApproved(User admin, AdmCaseDeletionRequest deletionRequest, AdmCaseDeletionRegistration deletionRegistration);

    void makeApproveCanceled(User admin, AdmCaseDeletionRequest deletionRequest);

    void makeDeclined(User admin, AdmCaseDeletionRequest deletionRequest, AdmCaseDeletionDeclineRequestDTO requestDTO);

    Page<AdmCaseDeletionRequestProjection> findAllByFilters(User user, Map<String, String> filters, Pageable pageable);

    Page<AdmCaseDeletionRequestProjection> findAllByAdmCaseId(@NotNull Long admCaseId, Pageable pageable);

    Page<AdmCaseDeletionRequestProjection> findAllByUserId(User user, Map<String, String> filters, Pageable pageable);
}
