package uz.ciasev.ubdd_service.service.admcase.deletion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseDeletionRequestDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRegistration;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.Optional;

public interface AdmCaseDeletionRegistrationService {

    AdmCaseDeletionRegistration getById(Long registrationId);

    Page<AdmCaseDeletionRegistration> findAllByAdmCase(AdmCase admCase, Pageable pageable);

    Optional<AdmCaseDeletionRegistration> findActiveByAdmCase(AdmCase admCase);

    Optional<AdmCaseDeletionRegistration> findLatestByAdmCase(AdmCase admCase);

    AdmCaseDeletionRegistration getActiveByAdmCase(AdmCase admCase);

    Page<AdmCaseDeletionRegistration> findAllByUser(User user, Pageable pageable);

    AdmCaseDeletionRegistration registerDeletion(AdmCase admCase, User user, AdmCaseDeletionRequestDTO reasonDTO);

    void registerRecovery(User user, AdmCaseDeletionRegistration registration);

}
