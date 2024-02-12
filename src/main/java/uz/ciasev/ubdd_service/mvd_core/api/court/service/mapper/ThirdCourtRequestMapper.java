package uz.ciasev.ubdd_service.mvd_core.api.court.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.CourtTransferService;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.*;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ThirdCourtRequestMapper implements CourtRequestMapper<ThirdCourtResolutionRequestDTO, ThirdCourtRequest> {
    private final CourtTransferService courtTransferService;

    @Override
    public ThirdCourtRequest map(ThirdCourtResolutionRequestDTO request) {
        ThirdCourtRequest mappedRequest = new ThirdCourtRequest();

        mapCase(mappedRequest, request);
        mapMovement(mappedRequest, request);
        mapEvidences(mappedRequest, request);
        mapDefendants(mappedRequest, request);

        return mappedRequest;
    }

    void mapCase(ThirdCourtRequest mappedRequest, ThirdCourtResolutionRequestDTO request) {
        mappedRequest.setCaseId(request.getCaseId());
        mappedRequest.setMaterialId(request.getMaterialId());
        mappedRequest.setClaimId(request.getClaimId());
        mappedRequest.setCourt(Optional.ofNullable(request.getCourt()).map(courtTransferService::transferCourt).orElse(null));
        mappedRequest.setInstance(request.getInstance());
        mappedRequest.setEditing(request.isEditing());
        mappedRequest.setHearingTime(request.getHearingDate());
        mappedRequest.setUseVcc(request.isUseVcc());
        mappedRequest.setProtest(request.isProtest());
        mappedRequest.setStatus(Optional.ofNullable(request.getStatus()).map(courtTransferService::transferStatus).orElse(null));
        mappedRequest.setCaseNumber(request.getCaseNumber());
        mappedRequest.setJudgeInfo(request.getJudge());
        mappedRequest.setPaused(request.isPaused());
        mappedRequest.setIsMaterial(request.isMaterial());
        mappedRequest.setMaterialType(Optional.ofNullable(request.getMaterialType()).map(courtTransferService::transferMaterialType).orElse(null));
        mappedRequest.setMaterialBaseClaimId(request.getMaterialPreviousClaimId());
    }

    void mapMovement(ThirdCourtRequest mappedRequest, ThirdCourtResolutionRequestDTO request) {

        ThirdCourtCaseMovementRequestDTO movement = request.getCaseMovement();
        if (movement != null) {

            MovementRequest mappedMovement = new MovementRequest();
            mappedMovement.setClaimId(movement.getOtherCourtClaimId());
            Optional.ofNullable(movement.getOtherCourtId()).map(courtTransferService::transferCourt).ifPresent(mappedMovement::setCourt);

            List<SeparationRequest> mappedSeparations = null;
            List<ThirdCourtCaseSeparationRequestDTO> separations = movement.getCaseSeparation();
            if (separations != null) {
                mappedSeparations = separations
                        .stream()
                        .map(s -> new SeparationRequest(s.getClaimSeparationId(), s.getCaseSeparationViolatorId()))
                        .collect(Collectors.toList());
            }


            mappedRequest.setMovement(mappedMovement);
            mappedRequest.setSeparations(mappedSeparations);
            mappedRequest.setClaimReviewId(movement.getClaimReviewId());
            mappedRequest.setClaimMergeId(movement.getClaimMergeId());
        }
    }

    void mapEvidences(ThirdCourtRequest mappedRequest, ThirdCourtResolutionRequestDTO request) {
        List<EvidenceDecisionRequest> mappedEvidences = new ArrayList<>();
        List<ThirdCourtEvidenceRequestDTO> evidences = request.getEvidenceList();
        if (evidences != null) {
            for (ThirdCourtEvidenceRequestDTO evidence : evidences) {
                EvidenceDecisionRequest mappedEvidence = new EvidenceDecisionRequest();
                mappedEvidence.setEvidenceId(evidence.getEvidenceId());
                mappedEvidence.setEvidenceResult(Optional.ofNullable(evidence.getEvidenceResult()).map(courtTransferService::transferEvidenceResult).orElse(null));
                mappedEvidence.setCourtId(evidence.getEvidenceCourtId());
                mappedEvidence.setPersonDescription(evidence.getPersonDescription());
                mappedEvidence.setEvidenceCategory(Optional.ofNullable(evidence.getEvidenceCategory()).map(courtTransferService::transferEvidenceCategory).orElse(null));
                mappedEvidence.setName(evidence.getEvidenceName());
                mappedEvidence.setQuantity(evidence.getEvidenceCountAndUnity());
                mappedEvidence.setMeasure(Optional.ofNullable(evidence.getMeasureId()).map(courtTransferService::transferMeasure).orElse(null));
                mappedEvidence.setCurrency(Optional.ofNullable(evidence.getCurrencyId()).map(courtTransferService::transferCurrency).orElse(null));
                mappedEvidence.setCost(evidence.getAmount());

                mappedEvidences.add(mappedEvidence);
            }
        }

        mappedRequest.setEvidenceDecisions(mappedEvidences);
    }

    void mapDefendants(ThirdCourtRequest mappedRequest, ThirdCourtResolutionRequestDTO request) {

        List<DefendantRequest> mappedDefendants = new ArrayList<>();
        List<ThirdCourtDefendantRequestDTO> defendants = request.getDefendant();
        if (defendants != null) {
            for (ThirdCourtDefendantRequestDTO defendant : defendants) {

                DefendantRequest mappedDefendant = new DefendantRequest();
                mappedDefendant.setDefendantId(defendant.getDefendantId());
                mappedDefendant.setViolatorId(defendant.getViolatorId());
                mappedDefendant.setParticipated(defendant.getIsParticipated());
                mappedDefendant.setFinalResult(Optional.ofNullable(defendant.getFinalResult()).map(courtTransferService::transferFinalResult).orElse(null));
                mappedDefendant.setMainPunishmentType(Optional.ofNullable(defendant.getMainPunishment()).map(courtTransferService::transferMainPunishmentType).orElse(null));
                mappedDefendant.setAdditionalPunishmentType(Optional.ofNullable(defendant.getAdditionalPunishment()).map(courtTransferService::transferAdditionPunishmentType).orElse(null));
                mappedDefendant.setTerminationReason(Optional.ofNullable(defendant.getEndBase()).map(courtTransferService::transferTerminationReason).orElse(null));
                mappedDefendant.setReturnReason(Optional.ofNullable(defendant.getReturnReason()).map(courtTransferService::transferReturnReason).orElse(null));
                mappedDefendant.setProsecutorDistrict(Optional.ofNullable(defendant.getProsecutorRegionId()).map(courtTransferService::transferDistrict));
                mappedDefendant.setPenaltyAmount(defendant.getFineTotal());
                mappedDefendant.setArticle33Applied(defendant.isArticle33Applied());
                mappedDefendant.setArticle34Applied(defendant.isArticle34Applied());
                mappedDefendant.setArrestDurationDay(defendant.getArrest());
                mappedDefendant.setPunishmentDurationYear(defendant.getPunishmentDurationYear());
                mappedDefendant.setPunishmentDurationMonth(defendant.getPunishmentDurationMonth());
                mappedDefendant.setPunishmentDurationDay(defendant.getPunishmentDurationDay());
                mappedDefendant.setAdditionalPunishmentDurationYear(defendant.getAdditionalPunishmentDurationYear());
                mappedDefendant.setAdditionalPunishmentDurationMonth(defendant.getAdditionalPunishmentDurationMonth());
                mappedDefendant.setAdditionalPunishmentDurationDay(defendant.getAdditionalPunishmentDurationDay());
                mappedDefendant.setExecuteBeforeDate(defendant.getDelayDate());
                mappedDefendant.setWithdrawalEvidences(defendant.getWithdrawalEvidences());
                mappedDefendant.setConfiscationEvidences(defendant.getConfiscationEvidences());
                mappedDefendant.setCassationAdditionalResult(defendant.getCassationAdditionalResult());
                mappedDefendant.setCancellingReason(defendant.getCancellingReason());
                mappedDefendant.setChangingReason(defendant.getChangingReason());
                mappedDefendant.setMaterialIsGranted(defendant.getIsGranted());
                mappedDefendant.setMaterialRejectBase(Optional.ofNullable(defendant.getMaterialRejectBase()).map(courtTransferService::transferMaterialRejectBase).orElse(null));
                mappedDefendant.setResolutionSeries(defendant.getResolutionSeries());
                mappedDefendant.setResolutionNumber(defendant.getResolutionNumber());

                List<CompensationRequest> mappedCompensations = new ArrayList<>();
                List<ThirdExactedDamageRequestDTO> compensations = defendant.getExactedDamage();
                if (compensations != null) {
                    for (ThirdExactedDamageRequestDTO compensation : compensations) {
                        CompensationRequest mappedCompensation = new CompensationRequest();

                        mappedCompensation.setAmount(compensation.getExactedDamageTotal());
                        mappedCompensation.setCurrency(Optional.ofNullable(compensation.getExactedDamageCurrency()).map(courtTransferService::transferCurrency).orElse(null));
                        mappedCompensation.setPayerType(Optional.ofNullable(compensation.getFromWhom()).map(courtTransferService::transferPayerType).orElse(null));
                        mappedCompensation.setPayerInn(compensation.getFromWhomInn());
                        mappedCompensation.setVictimType(Optional.ofNullable(compensation.getInFavorType()).map(courtTransferService::transferVictimType).orElse(null));
                        mappedCompensation.setVictimId(compensation.getVictimId());
                        mappedCompensation.setVictimInn(compensation.getInn());

                        mappedCompensations.add(mappedCompensation);
                    }
                }

                mappedDefendant.setCompensations(mappedCompensations);
                mappedDefendant.setArticleResults(defendant.getArticleResults());

                mappedDefendants.add(mappedDefendant);
            }
        }

        mappedRequest.setDefendants(mappedDefendants);
    }
}
