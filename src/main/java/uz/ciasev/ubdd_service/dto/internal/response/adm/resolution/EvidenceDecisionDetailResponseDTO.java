package uz.ciasev.ubdd_service.dto.internal.response.adm.resolution;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.response.adm.mib.MibCardResponseDTO;
import uz.ciasev.ubdd_service.entity.resolution.evidence_decision.EvidenceDecision;

@Getter
public class EvidenceDecisionDetailResponseDTO extends EvidenceDecisionListResponseDTO {

    private final MibCardResponseDTO mibCard;

    public EvidenceDecisionDetailResponseDTO(EvidenceDecision evidenceDecision, MibCardResponseDTO mibCard) {
        super(evidenceDecision);
        this.mibCard = mibCard;
    }
}

