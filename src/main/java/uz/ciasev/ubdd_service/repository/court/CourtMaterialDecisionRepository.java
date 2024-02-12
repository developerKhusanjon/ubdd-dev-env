package uz.ciasev.ubdd_service.repository.court;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialDecision;

import java.util.List;

public interface CourtMaterialDecisionRepository extends JpaRepository<CourtMaterialDecision, Long> {

    List<CourtMaterialDecision> findAllByCourtMaterialId(Long caseId);
}
