package uz.ciasev.ubdd_service.dto.internal.request.resolution.court;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.dto.internal.request.evidence.EvidenceData;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.EvidenceDecisionRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.evidence.Currency;
import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceCategory;
import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceResult;
import uz.ciasev.ubdd_service.entity.dict.evidence.Measures;
import uz.ciasev.ubdd_service.service.resolution.evidence_decision.EvidenceDecisionCreateRequest;

//  2022-12-26 Убрала валидацию, потаму что суд все равноне неисправит, а решения принимать надо
//@ValidEvidenceData
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourtEvidenceDecisionRequestDTO implements EvidenceDecisionRequestDTO, EvidenceData {

    private Long evidenceId;

//  2022-12-26 Убрала валидацию, потаму что суд все равноне неисправит, а решения принимать надо
//    @NotNull(message = ErrorCode.EVIDENCE_RESULT_ID_REQUIRED)
    private EvidenceResult evidenceResult;

//  2022-12-26 Убрала валидацию, потаму что суд все равноне неисправит, а решения принимать надо
//    @NotNull(message = ErrorCode.EVIDENCE_CATEGORY_REQUIRED)
    private EvidenceCategory evidenceCategory;

//    @NotNull(message = ErrorCode.MEASURE_REQUIRED)
    private Measures measure;

    private Double quantity;

    //  2022-11-23 Убрала валидацию, потаму что суд все равноне исправит, а решения принимать надо
    //    @MoneyAmount(required = false, message = ErrorCode.EVIDENCE_COST_INVALID)
    private Long cost;

    private Currency currency;

    private String personDescription;

    private Long evidenceSudId;

    @Override
    public EvidenceDecisionCreateRequest buildEvidenceDecision() {
        EvidenceDecisionCreateRequest evidenceDecision = new EvidenceDecisionCreateRequest();

        evidenceDecision.setEvidenceSudId(this.evidenceSudId);
        evidenceDecision.setEvidenceResult(this.evidenceResult);
        evidenceDecision.setEvidenceId(evidenceId);
        evidenceDecision.setEvidenceCategory(this.evidenceCategory);
        evidenceDecision.setMeasure(this.measure);
        evidenceDecision.setQuantity(this.quantity);
        evidenceDecision.setCost(this.cost);
        evidenceDecision.setCurrency(this.currency);
        evidenceDecision.setPersonDescription(this.personDescription);

        return evidenceDecision;
    }
}
