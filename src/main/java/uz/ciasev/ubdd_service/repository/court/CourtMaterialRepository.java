package uz.ciasev.ubdd_service.repository.court;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.court.CourtMaterial;

import java.util.List;
import java.util.Optional;

public interface CourtMaterialRepository extends JpaRepository<CourtMaterial, Long> {

    @Query("SELECT DISTINCT cmd.courtMaterial " +
            "FROM CourtMaterialDecision cmd " +
            "WHERE cmd.decisionId = :decisionId ")
    List<CourtMaterial> findAllByDecisionId(Long decisionId);

    @Query("SELECT DISTINCT cmd.courtMaterial.id " +
            "FROM CourtMaterialDecision cmd " +
            "WHERE cmd.decisionId = :decisionId ")
    List<Long> findAllIdsByDecisionId(Long decisionId);

    Optional<CourtMaterial> findByClaimId(Long claimId);

}
