package uz.ciasev.ubdd_service.repository.admcase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseMovement;
import uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseMovementStatusAlias;

import java.util.List;
import java.util.Optional;

public interface AdmCaseMovementRepository extends JpaRepository<AdmCaseMovement, Long> {

    @Query("SELECT acm " +
            " FROM AdmCaseMovement acm " +
            "WHERE acm.admCase.id = :admCaseId" +
            "  AND acm.statusAlias = :statusAlias")
    Optional<AdmCaseMovement> findByCaseIdAndStatusId(Long admCaseId, AdmCaseMovementStatusAlias statusAlias);

    default Optional<AdmCaseMovement> findLastOpenByCaseId(Long admCaseId) {
        return findByCaseIdAndStatusId(admCaseId, AdmCaseMovementStatusAlias.SENT);
    }

    List<AdmCaseMovement> findByAdmCaseId(Long id);
}
