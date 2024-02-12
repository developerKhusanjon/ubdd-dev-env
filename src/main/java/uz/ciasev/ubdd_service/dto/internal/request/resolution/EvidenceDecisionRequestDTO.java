package uz.ciasev.ubdd_service.dto.internal.request.resolution;

import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceResult;
import uz.ciasev.ubdd_service.service.resolution.evidence_decision.EvidenceDecisionCreateRequest;

public interface EvidenceDecisionRequestDTO {

    Long getEvidenceId();

    EvidenceResult getEvidenceResult();

    EvidenceDecisionCreateRequest buildEvidenceDecision();
}
