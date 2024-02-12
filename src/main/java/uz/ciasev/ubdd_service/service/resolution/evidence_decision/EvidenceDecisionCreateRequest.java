package uz.ciasev.ubdd_service.service.resolution.evidence_decision;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.evidence.Currency;
import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceCategory;
import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceResult;
import uz.ciasev.ubdd_service.entity.dict.evidence.Measures;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;

@Data
public class EvidenceDecisionCreateRequest {
    private Resolution resolution;
    private AdmStatus status;
    private Long evidenceSudId;
    private String personDescription;
    private Double quantity;
    private Long cost;
    private Long evidenceId;
    private EvidenceResult evidenceResult;
    private EvidenceCategory evidenceCategory;
    private Measures measure;
    private Currency currency;
}

