package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.helper.resolution;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.helper.resolution.punishment.*;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.DefendantRequest;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.court.CourtCompensationRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.court.CourtDecisionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.court.CourtPunishmentRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.resolution.DecisionTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentType;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

import static uz.ciasev.ubdd_service.entity.dict.court.CourtFinalResultAlias.*;

@Component
@RequiredArgsConstructor
public class DecisionHelper {

    private final CompensationHelper compensationHelper;
    private final EnumMap<PunishmentTypeAlias, PunishmentHelper> punishmentHelpers = new EnumMap<>(PunishmentTypeAlias.class);

    {
        punishmentHelpers.put(PunishmentTypeAlias.ARREST, new ArrestPunishmentHelper());
        punishmentHelpers.put(PunishmentTypeAlias.PENALTY, new PenaltyPunishmentHelper());
        punishmentHelpers.put(PunishmentTypeAlias.DEPORTATION, new DurationPunishmentHelper(false));
        punishmentHelpers.put(PunishmentTypeAlias.MEDICAL_PENALTY, new DurationPunishmentHelper(true));
        punishmentHelpers.put(PunishmentTypeAlias.LICENSE_REVOCATION, new DurationPunishmentHelper(true));
        punishmentHelpers.put(PunishmentTypeAlias.CONFISCATION, new EvidenceRelatedPunishmentHelper() {
            @Override
            public List<Long> getPunishmentEvidenceList(DefendantRequest defendant) {
                return defendant.getConfiscationEvidences();
            }
        });
        punishmentHelpers.put(PunishmentTypeAlias.WITHDRAWAL, new EvidenceRelatedPunishmentHelper() {
            @Override
            public List<Long> getPunishmentEvidenceList(DefendantRequest defendant) {
                return defendant.getWithdrawalEvidences();
            }
        });
    }

    public void check(ThirdCourtRequest request) {
        if (request.getDefendants() == null) return;

        for (DefendantRequest defendant : request.getDefendants()) {

            if (defendant.getFinalResult() == null) {
                throw new CourtValidationException(CourtValidationException.FINAL_RESULT_REQUIRED);
            }

            switch (defendant.getFinalResult().getAlias()) {
                case TERMINATION: {
                    checkTermination(defendant);
                    break;
                }
                case PUNISHMENT: {
                    checkMainPunishment(defendant, request);
                    break;
                }
                case APPEAL: {
                    if (defendant.getTerminationReason() != null && defendant.getMainPunishmentType() != null) {
                        throw new CourtValidationException(CourtValidationException.APPEAL_RESULT_NOT_CLEAR);
                    }

                    if (defendant.getTerminationReason() != null) {
                        checkTermination(defendant);
                        return;
                    }

                    if (defendant.getMainPunishmentType() != null) {
                        checkMainPunishment(defendant, request);
                        return;
                    }

                    compensationHelper.check(request);

                    throw new CourtValidationException(CourtValidationException.APPEAL_RESULT_UNKNOWN);
                }
                case RE_QUALIFICATION:
                    throw new CourtValidationException(CourtValidationException.RE_QUALIFICATION_NOT_ALLOW_FOR_DEFENDANTS);
                case NOT_RELATE:
                case RETURNING:
                case SEPARATING:
                case SENT_TO_OTHER_COURT:
                    break;
                default:
                    throw new NotImplementedException("Validation for finalResult not implement");
            }
        }
    }

    public List<CourtDecisionRequestDTO> build(ThirdCourtRequest request) {
        List<CourtDecisionRequestDTO> decisions = new ArrayList<>();

        if (request.getDefendants() == null) return decisions;

        for (DefendantRequest defendant : request.getDefendants()) {

            if (defendant.getFinalResult() == null || defendant.getFinalResult().notOneOf(PUNISHMENT, TERMINATION, APPEAL)) {
                continue;
            }

//            DecisionType decisionType = defendant.getFinalResult() != null
//                    ? decisionTypeService.getByAlias(DecisionTypeAlias.TERMINATION)
//                    : decisionTypeService.getByAlias(DecisionTypeAlias.PUNISHMENT);

            DecisionTypeAlias decisionTypeAlias;

            switch (defendant.getFinalResult().getAlias()) {
                case TERMINATION: {
                    decisionTypeAlias = DecisionTypeAlias.TERMINATION;
                    break;
                }
                case PUNISHMENT: {
                    decisionTypeAlias = DecisionTypeAlias.PUNISHMENT;
                    break;
                }
                case APPEAL: {
                    if (defendant.getTerminationReason() != null) {
                        decisionTypeAlias = DecisionTypeAlias.TERMINATION;
                        break;
                    }

                    if (defendant.getMainPunishmentType() != null) {
                        decisionTypeAlias = DecisionTypeAlias.PUNISHMENT;
                        break;
                    }

                    throw new CourtValidationException(CourtValidationException.APPEAL_RESULT_UNKNOWN);
                }
                default:
                    throw new NotImplementedException("Decision type alias for finalResult not implement");
            }

            CourtPunishmentRequestDTO mainPunishment = Optional.ofNullable(defendant.getMainPunishmentType())
                    .map(PunishmentType::getAlias)
                    .map(punishmentHelpers::get)
                    .map(punishmentHelper -> punishmentHelper.buildAsMain(defendant, request))
                    .orElse(null);

            CourtPunishmentRequestDTO additionPunishment = Optional.ofNullable(defendant.getAdditionalPunishmentType())
                    .map(PunishmentType::getAlias)
                    .map(punishmentHelpers::get)
                    .map(punishmentHelper -> punishmentHelper.buildAsAddition(defendant, request))
                    .orElse(null);

            List<CourtCompensationRequestDTO> compensations = compensationHelper.build(defendant);

            CourtDecisionRequestDTO decision = CourtDecisionRequestDTO.builder()
                    .violatorId(defendant.getViolatorId())
                    .defendantId(defendant.getDefendantId())
                    .decisionType(decisionTypeAlias)
                    .terminationReason(decisionTypeAlias == DecisionTypeAlias.TERMINATION ? defendant.getTerminationReason() : null)
                    .isArticle33(defendant.isArticle33Applied())
                    .isArticle34(defendant.isArticle34Applied())
                    .mainPunishment(mainPunishment)
                    .additionPunishment(additionPunishment)
                    .compensations(compensations)
                    .build();

            decisions.add(decision);
        }

        return decisions;
    }

    private void checkMainPunishment(DefendantRequest defendant, ThirdCourtRequest request) {
        if (defendant.getMainPunishmentType() == null) {
            throw new CourtValidationException(CourtValidationException.MAIN_PUNISHMENT_TYPE_REQUIRED_RESOLUTION);
        }

        PunishmentHelper mainPunishmentHelper = punishmentHelpers.get(defendant.getMainPunishmentType().getAlias());
        if (mainPunishmentHelper == null) {
            throw new NotImplementedException("Validation for mainPunishmentType not implement");
        }
        mainPunishmentHelper.checkAsMain(defendant, request);


        if (defendant.getAdditionalPunishmentType() != null) {
            PunishmentHelper additionPunishmentHelper = punishmentHelpers.get(defendant.getAdditionalPunishmentType().getAlias());
            if (additionPunishmentHelper == null) {
                throw new NotImplementedException("Validation for additionPunishmentType not implement");
            }
            additionPunishmentHelper.checkAsAddition(defendant, request);
        }
    }

    private void checkTermination(DefendantRequest defendant) {
        if (defendant.getTerminationReason() == null) {
            throw new CourtValidationException(CourtValidationException.END_BASE_REQUIRED);
        }
    }
}
