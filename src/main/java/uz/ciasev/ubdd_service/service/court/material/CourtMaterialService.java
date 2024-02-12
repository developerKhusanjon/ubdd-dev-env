package uz.ciasev.ubdd_service.service.court.material;

import uz.ciasev.ubdd_service.entity.court.CourtMaterial;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

import java.util.List;
import java.util.Optional;


public interface CourtMaterialService {

    List<CourtMaterial> findByDecisionId(Long decisionId);

    boolean existsByDecisionId(Long decisionId);

    CourtMaterial create(Long claimId, Decision decision);

    CourtMaterial resolved(CourtMaterial courtMaterial);

    Optional<CourtMaterial> findByClaimId(Long claimId);
}
