package uz.ciasev.ubdd_service.service.court.material;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.court.CourtMaterial;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialDecision;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.repository.court.CourtMaterialDecisionRepository;
import uz.ciasev.ubdd_service.repository.court.CourtMaterialRepository;
import uz.ciasev.ubdd_service.service.status.AdmCaseStatusService;
import uz.ciasev.ubdd_service.service.user.SystemUserService;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CourtMaterialServiceImpl implements CourtMaterialService {

    private final CourtMaterialRepository courtMaterialRepository;
    private final AdmCaseStatusService admStatusService;
    private final CourtMaterialDecisionRepository materialDecisionRepository;
    private final SystemUserService systemUserService;

    @Override
    public List<CourtMaterial> findByDecisionId(Long decisionId) {
        return courtMaterialRepository.findAllByDecisionId(decisionId);
    }

    @Override
    public Optional<CourtMaterial> findByClaimId(Long decisionId) {
        return courtMaterialRepository.findByClaimId(decisionId);
    }

    @Override
    public boolean existsByDecisionId(Long decisionId) {
        return !courtMaterialRepository.findAllIdsByDecisionId(decisionId).isEmpty();
    }

    @Override
    public CourtMaterial create(Long claimId, Decision decision) {
        CourtMaterial material = CourtMaterial.builder()
                .claimId(claimId)
                .userId(systemUserService.getCurrentUserOpt().map(User::getId).orElse(null))
                .build();

        material = admStatusService.setStatusAndSave(material, AdmStatusAlias.SENT_TO_COURT);

        materialDecisionRepository.save(new CourtMaterialDecision(decision, material));

        return material;
    }

    @Override
    public CourtMaterial resolved(CourtMaterial courtMaterial) {
        return admStatusService.setStatusAndSave(courtMaterial, AdmStatusAlias.DECISION_MADE);
        // TODO: 06.11.2023  

    }
}
