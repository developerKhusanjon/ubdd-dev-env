package uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.evidence.Currency;
import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceCategory;
import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceResult;
import uz.ciasev.ubdd_service.entity.dict.evidence.Measures;

@Data
public class EvidenceDecisionRequest {

    private Long evidenceId;
    private EvidenceResult evidenceResult;
    private Long courtId;
    private String personDescription;
    private EvidenceCategory evidenceCategory;
    private String name;
    private Double quantity;
    private Measures measure;
    private Currency currency;
    private Long cost;
}
