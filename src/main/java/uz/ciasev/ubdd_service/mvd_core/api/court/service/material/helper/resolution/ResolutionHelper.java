package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.helper.resolution;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.court.CourtDecisionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.court.CourtEvidenceDecisionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.court.CourtResolutionRequestDTO;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResolutionHelper {

    private final DecisionHelper decisionHelper;
    private final EvidenceDecisionHelper evidenceDecisionHelper;

    public void check(ThirdCourtRequest request) {
        if (request.getJudgeInfo() == null) {
            throw new CourtValidationException("Judge required for resolution");
        }

        if (request.getCourt() == null) {
            throw new CourtValidationException("Court required for resolution");
        }

        if (request.getInstance() == null) {
            throw new CourtValidationException("Instance required for resolution");
        }

        if (request.getCaseNumber() == null) {
            throw new CourtValidationException("Number required for resolution");
        }

        if (request.getHearingTime() == null) {
            throw new CourtValidationException("Hearing time required for resolution");
        }


        evidenceDecisionHelper.check(request);
        decisionHelper.check(request);
    }

    public CourtResolutionRequestDTO build(ThirdCourtRequest request) {
        CourtResolutionRequestDTO resolution = CourtResolutionRequestDTO.builder()
                .judgeInfo(request.getJudgeInfo())
                .region(request.getCourt().getRegion())
                .district(request.getCourt().getDistrict())
                .claimId(request.getClaimId())
                .instance(request.getInstance())
                .isUseVcc(request.isUseVcc())
                .courtNumber(request.getCaseNumber())
                .resolutionTime(request.getHearingTime())
                .build();


        List<CourtDecisionRequestDTO> decisions = decisionHelper.build(request);
        List<CourtEvidenceDecisionRequestDTO> evidenceDecisions = evidenceDecisionHelper.build(request);

        resolution.setDecisions(decisions);
        resolution.setEvidenceDecisions(evidenceDecisions);

        return resolution;
    }
}
