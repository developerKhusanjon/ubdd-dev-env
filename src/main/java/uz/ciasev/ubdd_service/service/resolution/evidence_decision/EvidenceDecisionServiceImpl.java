package uz.ciasev.ubdd_service.service.resolution.evidence_decision;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.response.adm.mib.MibCardResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.resolution.EvidenceDecisionDetailResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.resolution.EvidenceDecisionListResponseDTO;
import uz.ciasev.ubdd_service.entity.resolution.evidence_decision.EvidenceDecision;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.exception.FiltersNotSetException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;
import uz.ciasev.ubdd_service.repository.resolution.evidence_decision.EvidenceDecisionRepository;
import uz.ciasev.ubdd_service.service.mib.MibCardService;
import uz.ciasev.ubdd_service.service.status.PunishmentStatusCalculatingService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvidenceDecisionServiceImpl implements EvidenceDecisionService {

    private final MibCardService mibCardService;
    private final EvidenceDecisionRepository evidenceDecisionRepository;
    private final PunishmentStatusCalculatingService admStatusService;

    @Override
    public EvidenceDecision create(Resolution resolution, EvidenceDecisionCreateRequest evidenceDecision) {
        evidenceDecision.setResolution(resolution);
        evidenceDecision.setStatus(admStatusService.getStartStatus(evidenceDecision));

        return evidenceDecisionRepository.saveAndFlush(new EvidenceDecision(evidenceDecision));
    }

    @Override
    public List<EvidenceDecisionListResponseDTO> findAllByFilter(Long admCaseId, Long resolutionId, Boolean isActive) {
        if (admCaseId == null && resolutionId == null) {
            throw FiltersNotSetException.onOfRequired("admCaseId", "resolutionId");
        }

        return evidenceDecisionRepository
                .findAllByFilter(
                        Optional.ofNullable(admCaseId).orElse(-1L),
                        Optional.ofNullable(resolutionId).orElse(-1L),
                        isActive
                )
                .stream()
                .map(EvidenceDecisionListResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public EvidenceDecisionDetailResponseDTO findDTOById(Long id) {
        EvidenceDecision evidenceDecision = findById(id);
        MibCardResponseDTO mibExecutionCard = null;
//        try {
//            mibExecutionCard = mibCardService.getCardDTOForDecision(evidenceDecision.getId());
//        } catch (Exception ex) {
//            mibExecutionCard = null;
//        }

        return new EvidenceDecisionDetailResponseDTO(evidenceDecision, mibExecutionCard);
    }

    @Override
    public EvidenceDecision findById(Long id) {
        return evidenceDecisionRepository
                .findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(EvidenceDecision.class, id));
    }

    @Override
    public EvidenceDecision findByEvidenceCourtId(Long resolutionClaimId, Long evidenceCourtId) {
        return evidenceDecisionRepository
                .findByEvidenceCourtId(resolutionClaimId, evidenceCourtId)
                .orElseThrow(() -> new EntityByParamsNotFound(EvidenceDecision.class, "climeId", resolutionClaimId, "courtId", evidenceCourtId));
    }

    @Override
    public List<EvidenceDecision> findAllByResolutionId(Long resolutionId) {
        return evidenceDecisionRepository.findByResolutionId(resolutionId);
    }

    @Override
    @Transactional
    public void saveAll(List<EvidenceDecision> evidenceDecisions) {
        evidenceDecisionRepository.saveAll(evidenceDecisions);
    }
}
