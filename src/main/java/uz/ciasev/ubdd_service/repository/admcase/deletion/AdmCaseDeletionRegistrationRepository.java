package uz.ciasev.ubdd_service.repository.admcase.deletion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRegistration;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.Optional;

public interface AdmCaseDeletionRegistrationRepository extends JpaRepository<AdmCaseDeletionRegistration, Long> {

    Page<AdmCaseDeletionRegistration> findAllByAdmCase(AdmCase admCase, Pageable pageable);

    Page<AdmCaseDeletionRegistration> findAllByUser(User user, Pageable pageable);

    @Query("SELECT d FROM AdmCaseDeletionRegistration d WHERE d.isActive = TRUE AND d.admCaseId = :admCaseId")
    Optional<AdmCaseDeletionRegistration> findActiveByAdmCase(Long admCaseId);

//    @Query("SELECT d FROM AdmCaseDeletionRegistration d WHERE d.admCaseId = :admCaseId ORDER BY d.createdTime DESC LIMIT 1")
    Optional<AdmCaseDeletionRegistration> findTopByAdmCaseIdOrderByCreatedTimeDesc(Long admCaseId);

}
