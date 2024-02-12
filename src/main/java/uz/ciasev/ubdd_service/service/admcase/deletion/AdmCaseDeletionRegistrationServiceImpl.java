package uz.ciasev.ubdd_service.service.admcase.deletion;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseDeletionRequestDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRegistration;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;
import uz.ciasev.ubdd_service.repository.admcase.deletion.AdmCaseDeletionRegistrationRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdmCaseDeletionRegistrationServiceImpl implements AdmCaseDeletionRegistrationService {

    private final AdmCaseDeletionRegistrationRepository repository;

    @Override
    public AdmCaseDeletionRegistration getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(AdmCaseDeletionRegistration.class, id));
    }

    @Override
    public Page<AdmCaseDeletionRegistration> findAllByAdmCase(AdmCase admCase, Pageable pageable) {
        return repository.findAllByAdmCase(admCase, pageable);
    }

    @Override
    public Optional<AdmCaseDeletionRegistration> findActiveByAdmCase(AdmCase admCase) {
        return repository.findActiveByAdmCase(admCase.getId());
    }

    @Override
    public Optional<AdmCaseDeletionRegistration> findLatestByAdmCase(AdmCase admCase) {
        return repository.findTopByAdmCaseIdOrderByCreatedTimeDesc(admCase.getId());
    }

    @Override
    public AdmCaseDeletionRegistration getActiveByAdmCase(AdmCase admCase) {
        return repository.findActiveByAdmCase(admCase.getId())
                .orElseThrow(() -> new EntityByParamsNotFound(AdmCaseDeletionRegistration.class, "admCaseId", admCase.getId(), "isActive", true));
    }

    @Override
    public Page<AdmCaseDeletionRegistration> findAllByUser(User user, Pageable pageable) {
        return repository.findAllByUser(user, pageable);
    }

    @Override
    public AdmCaseDeletionRegistration registerDeletion(AdmCase admCase, User user, AdmCaseDeletionRequestDTO reasonDTO) {
        return repository.save(
                makeEntity(admCase, user, reasonDTO)
        );
    }

    @Override
    public void registerRecovery(User user, AdmCaseDeletionRegistration registration) {
        registration.setIsActive(false);
        registration.setRecoveredUser(user);
        registration.setRecoveredTime(LocalDateTime.now());

        save(registration);

    }

    private AdmCaseDeletionRegistration makeEntity(AdmCase admCase, User user, AdmCaseDeletionRequestDTO reasonDTO) {
        return AdmCaseDeletionRegistration.builder()
                .admCase(admCase)
                .user(user)
                .reason(reasonDTO.getReason())
                .documentBaseUri(reasonDTO.getDocumentBaseUri())
                .signature(reasonDTO.getSignature())
                .build();
    }

    private AdmCaseDeletionRegistration save(AdmCaseDeletionRegistration registration) {
        return repository.save(registration);
    }
}
