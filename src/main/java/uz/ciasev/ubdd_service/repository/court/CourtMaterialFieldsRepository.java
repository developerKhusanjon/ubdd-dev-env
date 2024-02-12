package uz.ciasev.ubdd_service.repository.court;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;

import java.util.Optional;

public interface CourtMaterialFieldsRepository extends JpaRepository<CourtMaterialFields, Long> {

    Optional<CourtMaterialFields> findByClaimId(Long claimId);

    boolean existsByCaseNumber(String number);
}
