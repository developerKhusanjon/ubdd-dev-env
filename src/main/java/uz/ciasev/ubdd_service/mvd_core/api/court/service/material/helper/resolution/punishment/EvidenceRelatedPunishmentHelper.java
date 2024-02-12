package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.helper.resolution.punishment;

import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.DefendantRequest;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.EvidenceDecisionRequest;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.court.CourtPunishmentRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentType;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class EvidenceRelatedPunishmentHelper implements PunishmentHelper {

    private void check(DefendantRequest defendant, ThirdCourtRequest request) {
        List<Long> punishmentEvidenceList = getPunishmentEvidenceList(defendant);

        if (punishmentEvidenceList == null)
            throw new CourtValidationException(CourtValidationException.PUNISHMENT_EVIDENCES_REQUIRED);

        List<EvidenceDecisionRequest> evidenceDecisions = request.getEvidenceDecisions();
        if (evidenceDecisions == null) {
            throw new CourtValidationException(CourtValidationException.EVIDENCE_DECISIONS_REQUIRED_FOR_CALCULATE_PUNISHMENT_AMOUNT);
        }

        Set<Long> presentedEvidences = request.getEvidenceDecisions().stream().map(EvidenceDecisionRequest::getCourtId).collect(Collectors.toSet());

        if (!presentedEvidences.containsAll(punishmentEvidenceList)) {
            throw new CourtValidationException(CourtValidationException.PUNISHMENT_EVIDENCE_NOT_PRESENT_IN_EVIDENCE_LIST);
        }
    }

    private CourtPunishmentRequestDTO build(DefendantRequest defendant, ThirdCourtRequest request, PunishmentType punishmentType) {
        List<Long> punishmentEvidenceList = getPunishmentEvidenceList(defendant);

        Long evidencesCost = request.getEvidenceDecisions().stream()
                .filter(ed -> punishmentEvidenceList.contains(ed.getCourtId()))
                .filter(e -> e.getCost() != null)
                .mapToLong(EvidenceDecisionRequest::getCost)
                .sum();

        return CourtPunishmentRequestDTO.builder()
                .punishmentType(punishmentType)
                .amount(evidencesCost)
                .build();
    }

    @Override
    public CourtPunishmentRequestDTO buildAsMain(DefendantRequest defendant, ThirdCourtRequest request) {
        return build(defendant, request, defendant.getMainPunishmentType());
    }

    @Override
    public void checkAsMain(DefendantRequest defendant, ThirdCourtRequest request) {
        check(defendant, request);
    }

    @Override
    public CourtPunishmentRequestDTO buildAsAddition(DefendantRequest defendant, ThirdCourtRequest request) {
        return build(defendant, request, defendant.getAdditionalPunishmentType());
    }

    @Override
    public void checkAsAddition(DefendantRequest defendant, ThirdCourtRequest request) {
        check(defendant, request);
    }

    public abstract List<Long> getPunishmentEvidenceList(DefendantRequest defendant);
}
