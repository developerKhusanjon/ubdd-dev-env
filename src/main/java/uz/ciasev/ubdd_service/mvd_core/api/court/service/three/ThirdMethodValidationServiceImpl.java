package uz.ciasev.ubdd_service.mvd_core.api.court.service.three;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.*;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.exception.court.CourtSeparationException;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.court.CourtCaseFieldsService;

import java.util.*;

import static uz.ciasev.ubdd_service.mvd_core.api.court.service.three.CourtFinalResultByInstanceAliases.*;
import static uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias.MERGED;
import static uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias.*;
import static uz.ciasev.ubdd_service.entity.status.AdmStatusAlias.*;
import static uz.ciasev.ubdd_service.exception.court.CourtValidationException.*;

@Service
@RequiredArgsConstructor
public class ThirdMethodValidationServiceImpl implements ThirdMethodValidationService {

    private static final EnumSet<AdmStatusAlias> ADM_STATUS_ALIASES_NOT_ALLOWED_FOR_FIRST_INSTANCE = EnumSet.of(
            DECISION_MADE,
            EXECUTED,
            IN_EXECUTION_PROCESS,
            SEND_TO_MIB,
            RETURN_FROM_MIB);

    private static final Set<Long> ADM_STATUSES_NOT_ALLOWED_MADE_DECISION = Set.of(MERGED.getValue(), RETURNED.getValue());

    private static final Long PUNISHMENT_PENALTY = 1L;
    private static final Long PUNISHMENT_ARREST = 5L;

    private static final Long CASSATION_ADDITIONAL_RESULT_CANCELING_DECISION = 538L;    //1L;
    private static final Long CASSATION_ADDITIONAL_RESULT_UPDATE_DECISION = 539L;       //2L;
    private static final Long CASSATION_ADDITIONAL_RESULT_DECISION_NOT_CHANGED = 540L;  //3L;

    private static final Long CANCELING_REASON_CASE_TERMINATION = 1L;
    private static final Long CANCELING_REASON_REPEATED_HEARING = 2L;
    private static final Long CANCELING_REASON_RETURNING_FOR_ADDITIONAL_INVESTIGATION = 3L;
    private static final Long CANCELING_REASON_PENALTY_IMPOSED = 4L;

    private final AdmCaseService admCaseService;
    private final CourtCaseFieldsService courtCaseFieldsService;

    @Override
    public void validate(ThirdCourtResolutionRequestDTO resolution) {
        if (resolution == null)
            throw new CourtValidationException(REQUEST_BODY_REQUIRED);

        if (resolution.getDefendant() == null || resolution.getDefendant().isEmpty()) {
            throw new CourtValidationException(DEFENDANT_IS_EMPTY);
        }

        validateGeneralFields(resolution);
        validatePauseField(resolution);
        validateMovements(resolution);
        validate308(resolution);
        validateMergedAndReturned(resolution);
    }

    @Override
    public void validateEvidences(List<ThirdCourtEvidenceRequestDTO> courtEvidences) {
        for (ThirdCourtEvidenceRequestDTO courtEvidence : courtEvidences) {
            if (courtEvidence.getEvidenceResult() == null)
                throw new CourtValidationException(COURT_EVIDENCE_RESULT_REQUIRED);
            if (courtEvidence.getEvidenceCategory() == null)
                throw new CourtValidationException(COURT_EVIDENCE_CATEGORY_REQUIRED);

//
//            EvidenceCategory evidenceCategory = evidenceCategoryService.getById(courtEvidence.getEvidenceCategory());
//
////            if (courtEvidence.getMeasureId() == null)
////                throw new CourtValidationException(COURT_EVIDENCE_MEASURE_REQUIRED, logId);
//
//            if (evidenceCategory.is(MONEY_OR_SECURITIES)) {
//                if (courtEvidence.getCurrencyId() == null || courtEvidence.getAmount() == null) {
//                    throw new CourtValidationException(COURT_EVIDENCE_CURRENCY_AND_AMOUNT_REQUIRED);
//
//                }
//            } else {
//                if (courtEvidence.getMeasureId() == null || courtEvidence.getEvidenceCountAndUnity() == null) {
//                    throw new CourtValidationException(COURT_EVIDENCE_MEASURE_AND_UNITY_REQUIRED);
//                }
//            }
        }
    }

    @Override
    public void validateSecondInstanceFields(List<ThirdCourtDefendantRequestDTO> defendants) {
        for (ThirdCourtDefendantRequestDTO defendant : defendants) {
            var endBase = defendant.getEndBase();
            var changingReason = defendant.getChangingReason();
            var cancelingReason = defendant.getCancellingReason();
            var casAdditionalResult = defendant.getCassationAdditionalResult();

            if (casAdditionalResult == null) {
                throw new CourtValidationException(COURT_CASSATION_ADDITIONAL_RESULT_REQUIRED);
            } else {

                if (casAdditionalResult.equals(CASSATION_ADDITIONAL_RESULT_CANCELING_DECISION)) {
                    if (cancelingReason == null)
                        throw new CourtValidationException(COURT_CANCELING_REASON_REQUIRED);
                    else if (cancelingReason.equals(CANCELING_REASON_CASE_TERMINATION) && endBase == null)
                        throw new CourtValidationException(COURT_END_BASE_REQUIRED_FOR_TERMINATION);
                }

                if (casAdditionalResult.equals(CASSATION_ADDITIONAL_RESULT_UPDATE_DECISION) && changingReason == null)
                    throw new CourtValidationException(COURT_CHANGING_REASON_REQUIRED);
            }
        }
    }

    @Override
    public void validateDecision(ThirdCourtResolutionRequestDTO requestDTO) {
        // это обработка решения, тут уже и так тольок 13 статаус окажеться. к таму же не понятно почему ткаой код ошибки, при чем туту финал резалт то?
        //        if (notAllowedDecisionStatuses.contains(requestDTO.getStatus()))
        //            throw new CourtValidationException(COURT_STATUS_AND_FINAL_RESULT_NOT_CONSISTENT);
        // сюда пустые дефенденты не дойдут
        //        List<ThirdCourtDefendantRequestDTO> defendants = requestDTO.getDefendant();
        //        if (defendants == null || defendants.isEmpty())
        //            throw new CourtValidationException(DEFENDANT_IS_EMPTY);

        for (var defendant : requestDTO.getDefendant()) {
            if (defendant.getFinalResult() == null)
                throw new CourtValidationException(FINAL_RESULT_REQUIRED);

            var frAlias = getNameByValue(defendant.getFinalResult());


            //  валидация частичного возврата судьи. Надо сделать выделение возвращенных нарушителей.
            if (frAlias.equals(FR_I_CASE_RETURNING))
                throw new CourtValidationException(FINAL_RESULT_4_AND_STATUS_13);

            if (frAlias.equals(FR_I_RE_QUALIFICATION))
                throw new CourtValidationException(FINAL_RESULT_115_AND_STATUS_13);

            var additionalPunishment = defendant.getAdditionalPunishment();
            if (additionalPunishment != null &&
                    (additionalPunishment.equals(PUNISHMENT_PENALTY) || additionalPunishment.equals(PUNISHMENT_ARREST)))
                throw new CourtValidationException(COURT_PUNISHMENT_ADDITIONAL);
        }

    }

    @Override
    public void validateReturnReason(ThirdCourtResolutionRequestDTO resolution) {
        List<ThirdCourtDefendantRequestDTO> defendants = resolution.getDefendant();
        Long instance = resolution.getInstance();

        for (var defendant : defendants) {
            var finalResult = getNameByValue(defendant.getFinalResult());

            if (instance == 1L) {
                if (!finalResult.equals(FR_I_CASE_RETURNING)) {
                    throw new CourtValidationException(FINAL_RESULT_MAST_BE_4);
                }
            } else {
                if (!(
                        finalResult.equals(FR_I_CASE_RETURNING)
                                || finalResult.equals(FR_II_REJECTED)
                                || finalResult.equals(FR_II_NOT_RELEVANT)
                )) {
                    throw new CourtValidationException(FINAL_RESULT_MAST_BE_4_OR_217_OR_218);
                }
            }

            if (!finalResult.equals(FR_I_CASE_RETURNING) && defendant.getReturnReason() == null)
                throw new CourtValidationException(RETURN_REASON_REQUIRED);
        }
    }

    private void validateGeneralFields(ThirdCourtResolutionRequestDTO resolution) {
        if (resolution.getCaseId() == null)
            throw new CourtValidationException(CASE_ID_REQUIRED);
        if (resolution.getClaimId() == null)
            throw new CourtValidationException(CLAIM_ID_REQUIRED);
        if (resolution.getCourt() == null)
            throw new CourtValidationException(COURT_FIELD_REQUIRED);
    }

    private void validatePauseField(ThirdCourtResolutionRequestDTO resolution) {
        if (resolution.getStatus().equals(CourtStatusAlias.PAUSED.getValue()) && !resolution.isPaused())
            throw new CourtValidationException(IS_PAUSED_TRUE);

        if (!resolution.getStatus().equals(CourtStatusAlias.PAUSED.getValue()) && resolution.isPaused())
            throw new CourtValidationException(IS_PAUSED_FALSE);
    }

    private void validateMovements(ThirdCourtResolutionRequestDTO resolution) {
        var movements = resolution.getCaseMovement();

        if (movements != null) {
            if (movements.getOtherCourtClaimId() != null && movements.getClaimMergeId() != null)
                throw new CourtValidationException(TRANSFER_AND_MERGE_IDS_ERROR);

            if (movements.getOtherCourtClaimId() != null && movements.getOtherCourtId() == null)
                throw new CourtValidationException(COURT_FIELD_REQUIRED);

            validateSeparation(movements.getCaseSeparation());
        }
    }

    private void validateMergedAndReturned(ThirdCourtResolutionRequestDTO resolution) {
        courtCaseFieldsService.findByCaseId(resolution.getCaseId())
                .ifPresent(caseFields -> {
                    if (ADM_STATUSES_NOT_ALLOWED_MADE_DECISION.contains(caseFields.getStatusId()) && resolution.getStatus().equals(MERGED.getValue())) {
                        throw new CourtValidationException(ADM_CASE_ALREADY_MERGED_OR_RETURNED);
                    }
                });
    }

    private void validate308(ThirdCourtResolutionRequestDTO resolution) {
        var admCase = admCaseService.getById(resolution.getCaseId());

        if (admCase.getIs308()) {
            var movements = resolution.getCaseMovement();

            if (movements != null && (movements.getCaseSeparation() != null || movements.getClaimMergeId() != null))
                throw new CourtValidationException(COURT_SEPARATION_AND_MERGING_BY_308_NOT_ALLOWED);
        }
    }

    private void validateAdmStatus(ThirdCourtResolutionRequestDTO resolution, Long claimId) {
        // Эта проверка мешает обрабатывать пересмотры.
        // а блок пересмотра в мувементе обрабатываеться тольок после этой проверки.
//        var caseId = resolution.getCaseId();
//
//        var admCase = admCaseService
//                .getByIdAndClaimId(caseId, claimId)
//                .orElseThrow(() -> new CourtValidationException(COURT_ADM_CASE_NOT_FOUND, logId));
//
//        var instances = finalResultDecisionService.findByCaseId(caseId);
//        var instance = InstancesAliases.OTHER;
//
//        if (resolution.getDefendant() != null && resolution.getDefendant().get(0).getFinalResult() != null) {
//            var defendant = resolution.getDefendant().get(0);
//            instance = defendant.getFinalResult() <= 115L ? InstancesAliases.FIRST : InstancesAliases.SECOND;
//        }
//
//        if (ADM_STATUS_ALIASES_NOT_ALLOWED_FOR_FIRST_INSTANCE.contains(admCase.getStatus().getAlias()) && instances.contains(instance))
//            throw new CourtValidationException(COURT_ADM_CASE_ALREADY_HAS_DECISION_FROM_COURT, logId);
    }

    private void validateSeparation(List<ThirdCourtCaseSeparationRequestDTO> separations) {
        if (separations != null && !separations.isEmpty())
            for (var elem : separations)
                if (elem.getClaimSeparationId() == null || elem.getCaseSeparationViolatorId().isEmpty())
                    throw new CourtSeparationException(elem);
    }
}
