package uz.ciasev.ubdd_service.dto.internal.request.resolution.organ;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.EvidenceDecisionRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceResult;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.resolution.evidence_decision.EvidenceDecisionCreateRequest;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;

import javax.validation.constraints.NotNull;

@Data
public class OrganEvidenceDecisionRequestDTO implements EvidenceDecisionRequestDTO {

    @NotNull(message = ErrorCode.EVIDENCE_ID_REQUIRED)
    Long evidenceId;

    @NotNull(message = ErrorCode.EVIDENCE_RESULT_ID_REQUIRED)
    @ActiveOnly(message = ErrorCode.EVIDENCE_RESULT_DEACTIVATED)
    @JsonProperty(value = "evidenceResultId")
    EvidenceResult evidenceResult;

    @Override
    public EvidenceDecisionCreateRequest buildEvidenceDecision() {
        EvidenceDecisionCreateRequest evidenceDecision = new EvidenceDecisionCreateRequest();
        evidenceDecision.setEvidenceResult(this.evidenceResult);
        evidenceDecision.setEvidenceId(evidenceId);
        return evidenceDecision;
    }
}
