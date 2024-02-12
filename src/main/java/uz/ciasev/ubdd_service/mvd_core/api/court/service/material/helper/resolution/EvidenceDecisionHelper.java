package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.helper.resolution;

import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.EvidenceDecisionRequest;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.court.CourtEvidenceDecisionRequestDTO;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;

import java.util.ArrayList;
import java.util.List;

@Component
public class EvidenceDecisionHelper {

    public void check(ThirdCourtRequest request) {
        if (request.getEvidenceDecisions() == null || request.getEvidenceDecisions().isEmpty()) return;

        for (EvidenceDecisionRequest evidenceDecision : request.getEvidenceDecisions()) {

            if (evidenceDecision.getCourtId() == null) {
                throw new CourtValidationException(CourtValidationException.EVIDENCE_COURT_ID_REQUIRED);
            }

//  2022-12-26 Убрала валидацию, потаму что суд все равноне неисправит, а решения принимать надо
//            if (evidenceDecision.getEvidenceResult() == null) {
//                throw new CourtValidationException(CourtValidationException.COURT_EVIDENCE_RESULT_REQUIRED);
//            }
//
//            if (evidenceDecision.getEvidenceCategory() == null) {
//                throw new CourtValidationException(CourtValidationException.COURT_EVIDENCE_CATEGORY_REQUIRED);
//            }
//  2022-11-23 Убрала валидацию, потаму что суд все равноне исправит, а решения принимать надо
//            if (evidenceDecision.getEvidenceCategory().is(MONEY_OR_SECURITIES)) {
//                if (evidenceDecision.getCurrency() == null || evidenceDecision.getCost() == null) {
//                    throw new CourtValidationException(COURT_EVIDENCE_CURRENCY_AND_AMOUNT_REQUIRED);
//                }
//            } else {
//                if (evidenceDecision.getMeasure() == null || evidenceDecision.getQuantity() == null) {
//                    throw new CourtValidationException(COURT_EVIDENCE_MEASURE_AND_UNITY_REQUIRED);
//                }
//            }
        }
    }

    public List<CourtEvidenceDecisionRequestDTO> build(ThirdCourtRequest request) {
        List<CourtEvidenceDecisionRequestDTO> evidenceDecisions = new ArrayList<>();

        if (request.getEvidenceDecisions() == null) return evidenceDecisions;

        for (EvidenceDecisionRequest evidenceRequest : request.getEvidenceDecisions()) {

            CourtEvidenceDecisionRequestDTO evidenceDecision = CourtEvidenceDecisionRequestDTO.builder()
                    .evidenceId(evidenceRequest.getEvidenceId())
                    .currency(evidenceRequest.getCurrency())
                    .evidenceCategory(evidenceRequest.getEvidenceCategory())
                    .evidenceResult(evidenceRequest.getEvidenceResult())
                    .evidenceSudId(evidenceRequest.getCourtId())
                    .measure(evidenceRequest.getMeasure())
                    .cost(evidenceRequest.getCost())
                    .quantity(evidenceRequest.getQuantity())
                    .personDescription(evidenceRequest.getPersonDescription())
                    .build();

            evidenceDecisions.add(evidenceDecision);
        }

        return evidenceDecisions;
    }
}

