package uz.ciasev.ubdd_service.service.resolution.evidence_decision;

import uz.ciasev.ubdd_service.dto.internal.response.adm.resolution.EvidenceDecisionDetailResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.resolution.EvidenceDecisionListResponseDTO;
import uz.ciasev.ubdd_service.entity.resolution.evidence_decision.EvidenceDecision;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;

import java.util.List;

public interface EvidenceDecisionService {

    EvidenceDecision create(Resolution resolution, EvidenceDecisionCreateRequest evidenceDecision);

    List<EvidenceDecisionListResponseDTO> findAllByFilter(Long admCaseId, Long resolutionId, Boolean isActive);

    EvidenceDecisionDetailResponseDTO findDTOById(Long id);

    EvidenceDecision findById(Long id);

    EvidenceDecision findByEvidenceCourtId(Long resolutionClaimId, Long evidenceCourtId);

    List<EvidenceDecision> findAllByResolutionId(Long resolutionId);

    void saveAll(List<EvidenceDecision> evidenceDecisions);
}
