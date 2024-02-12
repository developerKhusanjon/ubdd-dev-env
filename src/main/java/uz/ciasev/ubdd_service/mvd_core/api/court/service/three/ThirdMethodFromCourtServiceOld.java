//package uz.ciasev.ubdd_service.mvd_core.api.court.service.three;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.transaction.annotation.Transactional;
//import uz.ciasev.ubdd_service.mvd_core.api.court.CourtEventHolder;
//import uz.ciasev.ubdd_service.mvd_core.api.court.CourtMethod;
//import uz.ciasev.ubdd_service.mvd_core.api.court.service.CheckCourtDuplicateRequestService;
//import uz.ciasev.ubdd_service.mvd_core.api.court.service.CourtRequestOrderService;
//import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.*;
//import uz.ciasev.ubdd_service.dto.internal.request.resolution.court.*;
//import uz.ciasev.ubdd_service.entity.court.CourtArticleResultDecision;
//import uz.ciasev.ubdd_service.entity.court.CourtCaseFields;
//import uz.ciasev.ubdd_service.entity.court.CourtFinalResultDecision;
//import uz.ciasev.ubdd_service.mvd_core.api.court.service.three.CourtFinalResultByInstanceAliases;
//import uz.ciasev.ubdd_service.entity.dict.VictimType;
//import uz.ciasev.ubdd_service.entity.dict.court.CourtStatus;
//import uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias;
//import uz.ciasev.ubdd_service.entity.trans.court.CourtTransfer;
//import uz.ciasev.ubdd_service.entity.dict.court.InstancesAliases;
//import uz.ciasev.ubdd_service.entity.dict.evidence.Currency;
//import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceCategory;
//import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceResult;
//import uz.ciasev.ubdd_service.entity.dict.evidence.Measures;
//import uz.ciasev.ubdd_service.entity.dict.resolution.DecisionTypeAlias;
//import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentType;
//import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias;
//import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
//import uz.ciasev.ubdd_service.event.AdmEventService;
//import uz.ciasev.ubdd_service.event.AdmEventType;
//import uz.ciasev.ubdd_service.exception.court.CourtGeneralException;
//import uz.ciasev.ubdd_service.exception.court.CourtSeparationException;
//import uz.ciasev.ubdd_service.exception.court.CourtValidationException;
//import uz.ciasev.ubdd_service.publicapi.service.dto.eventdata.PublicApiWebhookEventDataCourtDTO;
//import uz.ciasev.ubdd_service.service.publicapi.eventdata.PublicApiWebhookEventCourtDataService;
//import uz.ciasev.ubdd_service.service.publicapi.eventdata.PublicApiWebhookEventPopulationService;
//import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
//import uz.ciasev.ubdd_service.service.court.CourtArticlesDecisionService;
//import uz.ciasev.ubdd_service.service.court.CourtCaseFieldsService;
//import uz.ciasev.ubdd_service.service.court.CourtFinalResultDecisionService;
//import uz.ciasev.ubdd_service.service.court.CourtAdmCaseMovementService;
//import uz.ciasev.ubdd_service.service.dict.court.CourtFinalResultService;
//import uz.ciasev.ubdd_service.service.court.dict.CourtTransferDictionaryService;
//import uz.ciasev.ubdd_service.service.dict.VictimTypeDictionaryService;
//import uz.ciasev.ubdd_service.service.dict.court.CourtStatusDictionaryService;
//import uz.ciasev.ubdd_service.service.dict.evidence.CurrencyDictionaryService;
//import uz.ciasev.ubdd_service.service.dict.evidence.EvidenceCategoryDictionaryService;
//import uz.ciasev.ubdd_service.service.dict.evidence.EvidenceResultDictionaryService;
//import uz.ciasev.ubdd_service.service.dict.evidence.MeasureDictionaryService;
//import uz.ciasev.ubdd_service.service.dict.resolution.DecisionTypeDictionaryService;
//import uz.ciasev.ubdd_service.service.dict.resolution.PunishmentTypeDictionaryService;
//import uz.ciasev.ubdd_service.service.dict.resolution.TerminationReasonDictionaryService;
//import uz.ciasev.ubdd_service.service.main.resolution.CourtResolutionMainService;
//
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import static uz.ciasev.ubdd_service.mvd_core.api.court.service.three.CourtFinalResultByInstanceAliases.*;
//import static uz.ciasev.ubdd_service.entity.dict.court.CourtFinalResultAlias.PUNISHMENT;
//import static uz.ciasev.ubdd_service.entity.dict.court.CourtFinalResultAlias.TERMINATION;
//import static uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias.MERGED;
//import static uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias.*;
//import static uz.ciasev.ubdd_service.entity.status.AdmStatusAlias.*;
//import static uz.ciasev.ubdd_service.exception.court.CourtValidationException.*;
//
//@RequiredArgsConstructor
//public class ThirdMethodFromCourtServiceOld implements ThirdMethodFromCourtService {
//
//    private static final EnumSet<CourtFinalResultByInstanceAliases> FIRST_INSTANCES = EnumSet.of(
//            FR_I_PUNISHMENT,
//            FR_I_TERMINATION,
//            FR_I_CASE_RETURNING,
//            FR_I_SENT_TO_OTHER_COURT,
//            FR_I_RE_QUALIFICATION);
//
//    private static final EnumSet<CourtFinalResultByInstanceAliases> CANCELLATION_RESOLUTION_FINAL_RESULTS = EnumSet.of(
//            FR_I_CASE_RETURNING,
//            FR_II_FULLY_SATISFIED,
//            FR_II_PARTIALLY_SATISFIED,
//            FR_II_REJECTED);
//
//    private static final EnumSet<CourtFinalResultByInstanceAliases> OTHER_INSTANCES = EnumSet.of(
//            FR_II_FULLY_SATISFIED,
//            FR_II_PARTIALLY_SATISFIED,
//            FR_II_REJECTED,
//            FR_II_NOT_RELEVANT,
//
//            FR_III_FULLY_SATISFIED,
//            FR_III_PARTIALLY_SATISFIED,
//            FR_III_REJECTED,
//            FR_III_NOT_RELEVANT,
//
//            FR_IV_FULLY_SATISFIED,
//            FR_IV_PARTIALLY_SATISFIED,
//            FR_IV_REJECTED,
//            FR_IV_NOT_RELEVANT);
//
//    private static final EnumSet<AdmStatusAlias> ADM_STATUS_ALIASES_NOT_ALLOWED_FOR_FIRST_INSTANCE = EnumSet.of(
//            DECISION_MADE,
//            EXECUTED,
//            IN_EXECUTION_PROCESS,
//            SEND_TO_MIB,
//            RETURN_FROM_MIB);
//
//    private static final Set<Long> notAllowedDecisionStatuses = Set.of(RETURNED.getValue(), MERGED.getValue());
//    private static final Set<Long> ADM_STATUSES_NOT_ALLOWED_MADE_DECISION = Set.of(MERGED.getValue(), RETURNED.getValue());
//
//    private static final Long PUNISHMENT_PENALTY = 1L;
//    private static final Long PUNISHMENT_ARREST = 5L;
//    private static final Long CHANGING_REASON_UPDATE_QUALIFICATION_LESS_PENALTY = 2L;
//    private static final Long CHANGING_REASON_NOT_UPDATE_QUALIFICATION_LESS_PENALTY = 4L;
//
//    private static final Long CASSATION_ADDITIONAL_RESULT_CANCELING_DECISION = 538L;    //1L;
//    private static final Long CASSATION_ADDITIONAL_RESULT_UPDATE_DECISION = 539L;       //2L;
//    private static final Long CASSATION_ADDITIONAL_RESULT_DECISION_NOT_CHANGED = 540L;  //3L;
//
//    private static final Long CANCELING_REASON_CASE_TERMINATION = 1L;
//    private static final Long CANCELING_REASON_REPEATED_HEARING = 2L;
//    private static final Long CANCELING_REASON_RETURNING_FOR_ADDITIONAL_INVESTIGATION = 3L;
//    private static final Long CANCELING_REASON_PENALTY_IMPOSED = 4L;
//
//    private static final Long FROM_JURIDIC = 2L;
//    private static final Long DAMAGE_WHOM_PERSON = 1L;
//    private static final Long IN_FAVOR_GOVERNMENT = 2L;
//
//    private final AdmCaseService admCaseService;
//    private final MeasureDictionaryService measureService;
//    private final CurrencyDictionaryService currencyService;
//    private final CourtResolutionMainService resolutionMainService;
//    private final VictimTypeDictionaryService victimTypeService;
//    private final CourtCaseFieldsService courtCaseFieldsService;
//    private final CourtStatusDictionaryService courtStatusService;
//    private final CourtFinalResultService courtFinalResultService;
//    private final CourtTransferDictionaryService courtTransferService;
//    private final CourtArticlesDecisionService articlesDecisionService;
//    private final EvidenceResultDictionaryService evidenceResultService;
//    private final PunishmentTypeDictionaryService punishmentTypeService;
//    private final EvidenceCategoryDictionaryService evidenceCategoryService;
//    private final CourtFinalResultDecisionService finalResultDecisionService;
//    private final TerminationReasonDictionaryService terminationReasonService;
//    private final CourtThirdMethodMovementService courtThirdMethodMovementService;
//    private final CourtAdmCaseMovementService courtAdmCaseMovementService;
//    private final CourtEventHolder courtEventHolder;
//    private final PublicApiWebhookEventCourtDataService eventDataService;
//    private final PublicApiWebhookEventPopulationService webhookEventPopulationService;
//    private final AdmEventService admEventService;
//    private final CheckCourtDuplicateRequestService courtDuplicateRequestService;
//    private final CourtRequestOrderService orderService;
//
//
//    @Override
//    @Transactional
//    public void accept(ThirdCourtResolutionRequestDTO resolution) {
//        courtDuplicateRequestService.checkAndRemember(resolution);
//
//        orderService.applyWithOrderCheck(CourtMethod.COURT_THIRD, resolution, this::handle);
//    }
//
//    void handle(ThirdCourtResolutionRequestDTO resolution) {
//        CourtStatus status = courtStatusService.getById(resolution.getStatus());
//        validation(resolution);
//
//        courtEventHolder.init();
//
//        saveCourtData(resolution, status);
//        calculateMovements(resolution);
//        if (resolution.isEditing() && status.oneOf(RETURNED, RESOLVED)) {
//            resolutionMainService.prepareCaseForEditing(resolution.getCaseId(), resolution.getClaimId());
//        }
//
//        switch (status.getAlias()) {
//            case REGISTERED_IN_COURT:
//            case JUDGE_APPOINTED:
//            case PAUSED:
//            case MERGED:
//            case PROCESS_REVIEW:
//            case PASSED_TO_ARCHIVE:
//            case DECLINED_PASSED_TO_ARCHIVE:
//            case RETURNED_FROM_ARCHIVE:
//                //  При этих сттаусах мы просто обрабатываем движение и сохраняем данные суда.
//                break;
//            case RETURNED:
//                returnCase(resolution);
//                break;
//            case RESOLVED:
//                madeDecision(resolution);
//                break;
//            default:
//                throw new CourtValidationException("Unknown status in method 3 ");
//        }
//
//        PublicApiWebhookEventDataCourtDTO courtEventDTO = courtEventHolder.close();
//        webhookEventPopulationService.addCourtEvent(courtEventDTO);
//
//    }
//
//    /**
//     * Статус "Решение вынесено (13)"
//     * последовательность обработки блоков :
//     * - блок регистрация
//     * - блок caseMovements
//     * - блок решение
//     */
//    private void madeDecision(ThirdCourtResolutionRequestDTO resolution) {
//        if (checkCassationUpholdReturn(resolution)) {
////            throw new CourtValidationException("is17Status in 13");
//            returnCase(resolution);
//            return;
//        }
//
////        if (resolution.isEditing()) {
////            makeMovementsWithDecisionViaEditingFlag(resolution, courtStatus);
////            return;
////        }
//
//        if (FR_I_SENT_TO_OTHER_COURT.equals(getNameByValue(extractFinalResult(resolution)))) {
//            makeSendToOtherCourt(resolution);
//            return;
//        }
//
////        makeDecision(resolution);
//        validateAndMadeDecision(resolution);
//        saveFinalResultAndArticles(resolution);
//
//    }
//
//    /**
//     * При пересмотре дела, предидущим решением по каторому было Возврат судьи, касационный суд может решить оставить возврат в силе.
//     * В этом случае мы должны обработать запрос со статусом Решение суди (13) как Возврат суди (17)
//     */
//    private boolean checkCassationUpholdReturn(ThirdCourtResolutionRequestDTO resolution) {
//        if (resolution != null && resolution.getDefendant() != null) {
//            List<ThirdCourtDefendantRequestDTO> defendants = resolution.getDefendant();
//            for (ThirdCourtDefendantRequestDTO defendant : defendants) {
//                if (defendant.getCassationAdditionalResult() != null
//                        && defendant.getCassationAdditionalResult().equals(CASSATION_ADDITIONAL_RESULT_DECISION_NOT_CHANGED)
//                        && defendant.getReturnReason() != null)
//                    return true;
//            }
//        }
//        return false;
//    }
//
//    private void makeSendToOtherCourt(ThirdCourtResolutionRequestDTO resolution) {
////        calculateMovements(resolution);
//
////        resolutionMainService.courtCancelResolutionByAdmCaseAndSetNewAdmStatus(resolution.getCaseId(), resolution.getClaimId(), SENT_TO_COURT, false, false);
////        resolutionMainService.setNewAdmStatus(resolution.getCaseId(), SENT_TO_COURT);
//
//        saveFinalResultAndArticles(resolution);
//    }
//
////    private void makeDecision(ThirdCourtResolutionRequestDTO resolution) {
//////        var claimId = calculateOldClaimId(resolution);
//////        validateAdmStatus(resolution, claimId);
////
//////        calculateMovements(resolution);
////        validateAndMadeDecision(resolution);
////        saveFinalResultAndArticles(resolution);
////    }
//
////    private void makeMovementsWithDecisionViaEditingFlag(ThirdCourtResolutionRequestDTO resolution) {
//////        resolutionMainService.courtCancelResolutionByAdmCaseAndSetNewAdmStatus(resolution.getCaseId(), resolution.getClaimId(), SENT_TO_COURT, false, false);
////        validateAndMadeDecision(resolution, true);
////        saveFinalResultAndArticles(resolution);
////    }
//
//    /**
//     * Статус "Возвращено (17)"
//     * последовательность обработки блоков :
//     * - блок регистрация
//     * - блок caseMovements
//     * - блок решение
//     */
//    private void returnCase(ThirdCourtResolutionRequestDTO resolution) {
////        handleStatusesWithoutDecision(resolution, courtStatus, logId);
//        validateReturnReason(resolution.getDefendant());
//
//        resolutionMainService.returnCaseFromCourt(resolution.getCaseId(), resolution.getClaimId());
////        resolutionMainService.courtCancelResolutionByAdmCaseAndSetNewAdmStatus(resolution.getCaseId(), resolution.getClaimId(), RETURN_FROM_COURT, false, false);
//        admEventService.fireEvent(
//                AdmEventType.COURT_RETURN_ADM_CASE,
//                admCaseService.getById(resolution.getCaseId())
//        );
//
//
//    }
//
////    /**
////     * Статус "Объединено (18)"
////     * последовательность обработки блоков :
////     * - блок регистрация
////     * - блок caseMovements
////     * - блок решение
////     */
////    private void mergeCases(ThirdCourtResolutionRequestDTO resolution, CourtStatus courtStatus, Long logId) {
////        handleStatusesWithoutDecision(resolution, courtStatus, logId);
////    }
////
////    private void handleStatusesWithoutDecision(ThirdCourtResolutionRequestDTO resolution, CourtStatus courtStatus, Long logId) {
////        calculateMovements(resolution, courtStatus, logId);
////        saveCaseCourtFields(resolution, courtStatus, logId);
////    }
//
//    /**
//     * Блок caseMovements обрабатываем всегда в след последовательности
//     * Шаг 1. Пересмотр
//     * Шаг 2. Разъединение
//     * Шаг 3. Передача / Объединение
//     * При передаче появляется новый claimId, который следует вернуть и использовать далее
//     * Передача и Объединение никогда не придут в одном запросе (со слов суда)
//     */
//    private void calculateMovements(ThirdCourtResolutionRequestDTO resolution) {
//        if (resolution.isEditing()) {
//            return;
//        }
//
//        boolean isDuplicate = courtThirdMethodMovementService.hasDuplicateMovements(resolution);
//        if (isDuplicate)
//            return;
//
//        var isRevision = courtThirdMethodMovementService.hasRevision(resolution.getCaseMovement());
//        if (isRevision)
//            courtThirdMethodMovementService.makeRevision(resolution);
//
//        var isSeparation = courtThirdMethodMovementService.hasSeparation(resolution.getCaseMovement());
//        if (isSeparation)
//            courtThirdMethodMovementService.makeSeparation(resolution);
//
//        var isTransfer = courtThirdMethodMovementService.hasTransfer(resolution.getCaseMovement());
//        if (isTransfer)
//            courtThirdMethodMovementService.makeTransfer(resolution);
//
//        var isMerge = courtThirdMethodMovementService.hasMerge(resolution.getCaseMovement());
//        if (isMerge)
//            courtThirdMethodMovementService.makeMerge(resolution);
//
//        if (!isRevision && !isTransfer)
//            courtThirdMethodMovementService.makeRegistration(resolution);
//    }
//
//    private void validateAndMadeDecision(ThirdCourtResolutionRequestDTO resolution) {
//        var claimId = calculateNewClaimId(resolution);
//
//        validateDecision(resolution);
//
//        var finalResult = getNameByValue(extractFinalResult(resolution));
//
////        if (finalResult.equals(FR_I_SENT_TO_OTHER_COURT)) {
////            makeSendToOtherCourt(resolution);
////            return;
////        }
//
////        var hasCancellationResolution = CANCELLATION_RESOLUTION_FINAL_RESULTS.contains(finalResult);
//
//        if (FIRST_INSTANCES.contains(finalResult)) {
//            handleFirstInstance(resolution);
//        } else if (OTHER_INSTANCES.contains(finalResult)) {
//            handleOtherInstance(resolution, claimId);
//        } else {
//            throw new CourtValidationException(COURT_FINAL_RESULT_NOT_SUPPORTED);
//        }
//    }
//
//
//    private Long extractFinalResult(ThirdCourtResolutionRequestDTO resolution) {
//        var defendant = resolution.getDefendant().stream().findFirst().orElseThrow(() -> new CourtValidationException("Defendant is empty"));
//        if (defendant.getFinalResult() == null) {
//            throw new CourtValidationException("Defendant finalResult is empty");
//        }
//        return defendant.getFinalResult();
//    }
//
//    /*  1 инстанция
//            SENT_TO_COURT
//                1.  Если это первая инстанция, то проверить не передача ли это в другой суд finalResult = 113.
//                2.  Для передачи все finalResult должны быть 113.
//                3.  Если есть активное решение (не отмененное), тогда перевести в статус "Исполнение не возможно" или "Отменено судом".
//                4.  Если есть активные квитанции (не заблокированные ранее) по штрафу и ущербу надо их заблокировать на Ягоне.
//                5.  Если решение уже "Исполнено" или "Частично исполнено", тогда статус нужно сбросить в историю.
//
//            PUNISHMENT
//                1.  Сформировать Решение с типом Punishment на mainPunishment and additionalPunishment.
//                2.  Если это "Конфискация", "Лишение прав"  то автоматом сформировать Исполнение решения.
//                3.  Выставить статус адм дела в "Вынесено решение", "В процессе исполнения" или "Исполнено"
//                4.  Если было активное Решение( не отменное) тогда перевести его в статус "Отменено судом"
//                5.  Если по предыдущему и/или новому решениям есть штраф или ущерб в пользу гос-ва, тогда обработать квитанции ПО АЛГОРИТМУ из Таблицы соответствий
//
//            TERMINATION
//                1.  Сформировать Решение с типом Termination and endBase.
//                2.  Автоматом сформировать исполнение
//                3.  Выставить статус адм дела в "Вынесено решение", "В процессе исполнения" или "Исполнено"
//                4.  Если было активное Решение( не отменное) тогда перевести его в статус "Отменено судом"
//                5.  Если есть активные квитанции (не заблокированные ранее) по штрафу и ущербу надо их заблокировать на Ягоне.
//*/
//    private void handleFirstInstance(ThirdCourtResolutionRequestDTO requestDTO) {
//
//        var courtTransfer = courtTransferService.findByExternalId(requestDTO.getCourt());
//
//        Optional<List<CourtEvidenceDecisionRequestDTO>> evidences = buildEvidenceDecisions(requestDTO.getEvidenceList());
//
//        List<CourtDecisionRequestDTO> decisions = requestDTO.getDefendant().stream().map(defendant -> {
//            var finalResult = courtFinalResultService.getByCode(defendant.getFinalResult());
//
//            if (finalResult.getAlias().equals(PUNISHMENT)) {
//                return handlePunishment(defendant, requestDTO.getEvidenceList(), requestDTO.getHearingDate());
//            }
//
//            if (finalResult.getAlias().equals(TERMINATION)) {
//                return handleTermination(defendant);
//            }
//
//            throw new CourtValidationException(String.format("Unknown decision case for final result %s", finalResult.getAlias()));
//        }).collect(Collectors.toList());
//
//        var resolution = new CourtResolutionRequestDTO();
//        resolution.setCourtNumber(requestDTO.getCaseNumber());
//        resolution.setResolutionTime(requestDTO.getHearingDate());
//        resolution.setJudgeInfo(requestDTO.getJudge());
//        resolution.setDecisions(decisions);
//        resolution.setClaimId(requestDTO.getClaimId());
//        resolution.setRegion(courtTransfer.getRegion());
//        resolution.setDistrict(courtTransfer.getDistrict());
//        evidences.ifPresent(resolution::setEvidenceDecisions);
//
////            resolutionMainService.courtCancelResolutionByAdmCaseAndSetNewAdmStatus(requestDTO.getCaseId(), claimId, SENT_TO_COURT, hasCancellationResolution, isEditing);
////        resolutionMainService.setNewAdmStatus(requestDTO.getCaseId(), SENT_TO_COURT);
//        resolutionMainService.createCourtResolution(requestDTO.getCaseId(), resolution);
//    }
//
//    private Optional<List<CourtCompensationRequestDTO>> buildCompensationDecision(ThirdCourtDefendantRequestDTO defendant) {
//        List<CourtCompensationRequestDTO> compensations = new ArrayList<>();
////        for (ThirdCourtDefendantRequestDTO defendant : defendants) {
//        List<ThirdExactedDamageRequestDTO> exactedDamages = defendant.getExactedDamage();
//        if (exactedDamages != null && !exactedDamages.isEmpty()) {
//            for (ThirdExactedDamageRequestDTO damage : exactedDamages) {
//                validateFavorTypeAndFromWhom(damage);
//
//                if (damage.getFromWhom().equals(DAMAGE_WHOM_PERSON)) {
//                    CourtCompensationRequestDTO compensation = new CourtCompensationRequestDTO();
//                    Currency currency = currencyService.getById(damage.getExactedDamageCurrency());
//                    VictimType victimType = victimTypeService.getById(damage.getInFavorType());
//                    compensation.setViolatorId(defendant.getViolatorId());
//                    compensation.setAmount(damage.getExactedDamageTotal());
//                    compensation.setVictimId(damage.getVictimId());
//                    compensation.setCurrency(currency);
//                    compensation.setVictimType(victimType);
//
//                    compensations.add(compensation);
//                }
//            }
//        }
////        }
//        if (!compensations.isEmpty())
//            return Optional.of(compensations);
//
//        return Optional.empty();
//    }
//
//    private Optional<List<CourtEvidenceDecisionRequestDTO>> buildEvidenceDecisions(List<ThirdCourtEvidenceRequestDTO> courtEvidences) {
//        Optional<List<CourtEvidenceDecisionRequestDTO>> result = Optional.empty();
//
//        if (courtEvidences != null && !courtEvidences.isEmpty()) {
//            validateEvidences(courtEvidences);
//            List<CourtEvidenceDecisionRequestDTO> evidences = new ArrayList<>();
//            for (ThirdCourtEvidenceRequestDTO elem : courtEvidences) {
//                CourtEvidenceDecisionRequestDTO courtEvidence = new CourtEvidenceDecisionRequestDTO();
//
//                EvidenceResult evidenceResult = Optional.ofNullable(elem.getEvidenceResult()).map(evidenceResultService::getById).orElse(null);
//                EvidenceCategory evidenceCategory = Optional.ofNullable(elem.getEvidenceCategory()).map(evidenceCategoryService::getById).orElse(null);
//                Measures measures = Optional.ofNullable(elem.getMeasureId()).map(measureService::getById).orElse(null);
//                Currency currency = Optional.ofNullable(elem.getCurrencyId()).map(currencyService::getById).orElse(null);
//
//                courtEvidence.setEvidenceId(elem.getEvidenceId());
//                courtEvidence.setEvidenceResult(evidenceResult);
//                courtEvidence.setEvidenceCategory(evidenceCategory);
//                courtEvidence.setMeasure(measures);
//                courtEvidence.setCurrency(currency);
//                courtEvidence.setQuantity(elem.getEvidenceCountAndUnity());
//                courtEvidence.setCost(elem.getAmount());
//                courtEvidence.setPersonDescription(elem.getPersonDescription());
//                courtEvidence.setEvidenceSudId(elem.getEvidenceCourtId());
//
//                evidences.add(courtEvidence);
//            }
//            result = Optional.of(evidences);
//        }
//
//        return result;
//    }
//
//    private void validateEvidences(List<ThirdCourtEvidenceRequestDTO> courtEvidences) {
//        for (ThirdCourtEvidenceRequestDTO courtEvidence : courtEvidences) {
//            if (courtEvidence.getEvidenceResult() == null)
//                throw new CourtValidationException(COURT_EVIDENCE_RESULT_REQUIRED);
//            if (courtEvidence.getEvidenceCategory() == null)
//                throw new CourtValidationException(COURT_EVIDENCE_CATEGORY_REQUIRED);
//
////
////            EvidenceCategory evidenceCategory = evidenceCategoryService.getById(courtEvidence.getEvidenceCategory());
////
//////            if (courtEvidence.getMeasureId() == null)
//////                throw new CourtValidationException(COURT_EVIDENCE_MEASURE_REQUIRED, logId);
////
////            if (evidenceCategory.is(MONEY_OR_SECURITIES)) {
////                if (courtEvidence.getCurrencyId() == null || courtEvidence.getAmount() == null) {
////                    throw new CourtValidationException(COURT_EVIDENCE_CURRENCY_AND_AMOUNT_REQUIRED);
////
////                }
////            } else {
////                if (courtEvidence.getMeasureId() == null || courtEvidence.getEvidenceCountAndUnity() == null) {
////                    throw new CourtValidationException(COURT_EVIDENCE_MEASURE_AND_UNITY_REQUIRED);
////                }
////            }
//        }
//    }
//
//    private void validateFavorTypeAndFromWhom(ThirdExactedDamageRequestDTO damage) {
//        if (damage.getInFavorType().equals(IN_FAVOR_GOVERNMENT) && damage.getFromWhom().equals(FROM_JURIDIC))
//            throw new CourtValidationException(FAVOR_TYPE_NOT_SUPPORTED);
//    }
//
//    /*  2 инстанция
//        CASSATION_ADDITIONAL_RESULT = 1
//            END_BASE = not empty и CANCELING_REASON = 1 -> Дело прекращено
//                1.  Сформировать Решение с типом Termination and endBase.
//                2.  Автоматом сформировать исполнение
//                3.  Выставить статус адм дела в "Вынесено решение", "В процессе исполнения" или "Исполнено"
//                4.  Если было активное Решение( не отменное) тогда перевести его в статус "Отменено судом"
//                5.  Если есть активные квитанции (не заблокированные ранее) по штрафу и ущербу надо их заблокировать на Ягоне.
//
//            END_BASE = empty
//                CANCELING_REASON = 2 -> Повторное разбирательство суда
//                    0.  Все CANCELING_REASON должны быть одинаковые
//                    1.  Выставить статус адм дела в "Отправлено в суд"
//                    2.  Если было активное Решение( не отменное) тогда перевести его в статус "Отменено судом"
//                    3.  Если есть активные квитанции (не заблокированные ранее) по штрафу и ущербу надо их заблокировать на Ягоне.
//
//                CANCELING_REASON = 3 -> Возврат для доп расследования
//                    1.  Выставить статус адм дела в "Возврат судом"
//                    2.  Если было активное Решение( не отменное) тогда перевести его в статус "Отменено судом"
//                    3.  Если есть активные квитанции (не заблокированные ранее) по штрафу и ущербу надо их заблокировать на Ягоне.
//
//                CANCELING_REASON = 4 -> Назначено наказание
//                    1.  Сформировать Решение с типом Punishment на mainPunishment and additionalPunishment.
//                    2.  Если это "Конфискация", "Лишение прав"  то автоматом сформировать Исполнение решения.
//                    3.  Выставить статус адм дела в "Вынесено решение", "В процессе исполнения" или "Исполнено"
//                    4.  Если было активное Решение( не отменное) тогда перевести его в статус "Отменено судом"
//                    5.  Если по предыдущему и/или новому решениям есть штраф или ущерб в пользу гос-ва, тогда обработать квитанции ПО АЛГОРИТМУ из Таблицы соответствий
//
//        CASSATION_ADDITIONAL_RESULT = 2
//            CHANGING_REASON = 1 -> Изменена квалификация
//                Сохранить статьи
//
//            CHANGING_REASON = 2 -> Изменена квалификация. Уменьшено наказание
//                0.  Сохранить статьи
//                1.  Сформировать Решение с типом Punishment на mainPunishment and additionalPunishment.
//                2.  Если это "Конфискация", "Лишение прав"  то автоматом сформировать Исполнение решения.
//                3.  Выставить статус адм дела в "Вынесено решение", "В процессе исполнения" или "Исполнено"
//                4.  Если было активное Решение( не отменное) тогда перевести его в статус "Отменено судом"
//                5.  Если по предыдущему и/или новому решениям есть штраф или ущерб в пользу гос-ва, тогда обработать квитанции ПО АЛГОРИТМУ из Таблицы соответствий
//
//            CHANGING_REASON = 3 -> Изменена квалификация. Уменьшено не наказание
//                Сохранить статьи
//
//            CHANGING_REASON = 4 -> Без изменения квалификации. Уменьшено наказание
//                1.  Сформировать Решение с типом Punishment на mainPunishment and additionalPunishment.
//                2.  Если это "Конфискация", "Лишение прав"  то автоматом сформировать Исполнение решения.
//                3.  Выставить статус адм дела в "Вынесено решение", "В процессе исполнения" или "Исполнено"
//                4.  Если было активное Решение( не отменное) тогда перевести его в статус "Отменено судом"
//                5.  Если по предыдущему и/или новому решениям есть штраф или ущерб в пользу гос-ва, тогда обработать квитанции ПО АЛГОРИТМУ из Таблицы соответствий
//
//            CHANGING_REASON = 5 -> Изменено содержание решение
//                Ничего не делаем
//            CHANGING_REASON = 6 -> Отменена часть вещдоков
//                Ничего не делаем
//            CHANGING_REASON = 7 -> Изменена часть вещдоков
//                Ничего не делаем
//            CHANGING_REASON = 8 -> Другие причины
//                Ничего не делаем
//*/
//    private void handleOtherInstance(ThirdCourtResolutionRequestDTO requestDTO, Long claimId) {
//        CourtTransfer courtTransfer = courtTransferService.findByExternalId(requestDTO.getCourt());
//
//        List<ThirdCourtDefendantRequestDTO> defendants = requestDTO.getDefendant();
//        List<ThirdCourtEvidenceRequestDTO> evidenceDTO = requestDTO.getEvidenceList();
//
//        validateSecondInstanceFields(defendants);
//
//        boolean isRetrialCourt = hasRetrialCourt(defendants);
//        boolean isReturningForAdditionalInvestigation = hasAdditionalInvestigation(defendants);
//
//        if (isRetrialCourt) {
//            // Перевести статус дела "Отправлен в суд"
////            resolutionMainService.courtCancelResolutionByAdmCaseAndSetNewAdmStatus(requestDTO.getCaseId(), claimId, SENT_TO_COURT, hasCancellationResolution, isEditing);
//            resolutionMainService.otherCourtInstanceRetrialCase(requestDTO.getCaseId(), claimId);
//            return;
//        }
//
//        if (isReturningForAdditionalInvestigation) {
//            // Перевести статус дела "Возврат судом"
////            resolutionMainService.courtCancelResolutionByAdmCaseAndSetNewAdmStatus(requestDTO.getCaseId(), claimId, RETURN_FROM_COURT, hasCancellationResolution, isEditing);
//            resolutionMainService.otherCourtInstanceReturnFromCourt(requestDTO.getCaseId(), claimId);
//            return;
//        }
//
//        List<CourtDecisionRequestDTO> decisions = defendants.stream()
//                .map(defendant -> courtCassationDecision(requestDTO, defendant))
//                .collect(Collectors.toList());
//
//
//        Optional<List<CourtEvidenceDecisionRequestDTO>> evidences = buildEvidenceDecisions(requestDTO.getEvidenceList());
////            Optional<List<CourtCompensationRequestDTO>> compensations = buildCompensationDecision(defendants, claimId);
//
//        var resolution = new CourtResolutionRequestDTO();
//        resolution.setJudgeInfo(requestDTO.getJudge());
//        resolution.setCourtNumber(requestDTO.getCaseNumber());
//        resolution.setResolutionTime(requestDTO.getHearingDate());
//        resolution.setDecisions(decisions);
//        resolution.setClaimId(claimId);
//        resolution.setRegion(courtTransfer.getRegion());
//        resolution.setDistrict(courtTransfer.getDistrict());
//        evidences.ifPresent(resolution::setEvidenceDecisions);
//
////            resolutionMainService.courtCancelResolutionByAdmCaseAndSetNewAdmStatus(requestDTO.getCaseId(), claimId, SENT_TO_COURT, hasCancellationResolution, isEditing);
////            resolutionMainService.setNewAdmStatus(requestDTO.getCaseId(), SENT_TO_COURT);
//        resolutionMainService.createCourtResolution(requestDTO.getCaseId(), resolution);
//
//    }
//
//
////    private CourtDecisionRequestDTO buildOtherInstanceDecision(ThirdCourtResolutionRequestDTO requestDTO, ThirdCourtDefendantRequestDTO defendant) {
////        if (Objects.equals(defendant.getCassationAdditionalResult(), CASSATION_ADDITIONAL_RESULT_CANCELING_DECISION)) {
////            return courtDecisionCanceling(requestDTO, defendant);
////        } else if (Objects.equals(defendant.getCassationAdditionalResult(), CASSATION_ADDITIONAL_RESULT_UPDATE_DECISION)) {
////            return courtDecisionChanging(requestDTO, defendant);
////        } else if (Objects.equals(defendant.getCassationAdditionalResult(), CASSATION_ADDITIONAL_RESULT_DECISION_NOT_CHANGED)) {
////            return courtDecisionNotChanging(requestDTO, defendant);
////        }
////        throw new CourtValidationException(String.format("Unknown decision case for other instance cassationAdditionalResult %s and final result %s", defendant.getCassationAdditionalResult(), defendant.getFinalResult()));
////    }
////
////    /**
////     * РЕШЕНИЕ СУДА ОТМЕНЕНО
////     * Если это 2 или 4, то формируем наказание
////     * <p>
////     * 2 - ИЗМЕНЕНА КВАЛИФИКАЦИЯ-УМЕНЬШЕНО НАКАЗАНИЕ
////     * 4 - БЕЗ ИЗМЕНЕНИЯ КВАЛИФИКАЦИИ-УМЕНЬШЕНО НАКАЗАНИЕ
////     */
////    private CourtDecisionRequestDTO courtDecisionCanceling(ThirdCourtResolutionRequestDTO requestDTO, ThirdCourtDefendantRequestDTO defendant) {
////        Long endBase = defendant.getEndBase();
////        Long cancelingReason = defendant.getCancellingReason();
////
////        if (endBase != null && cancelingReason.equals(CANCELING_REASON_CASE_TERMINATION))
////            return handleTermination(defendant);
////
////        if (cancelingReason.equals(CANCELING_REASON_PENALTY_IMPOSED))
////            return handlePunishment(defendant, requestDTO.getEvidenceList(), requestDTO.getHearingDate());
////
////        throw new CourtValidationException("No case for casAdditionalResult canceling");
////    }
////
////    /**
////     * РЕШЕНИЕ СУДА ИЗМЕНЕНО
////     * Если это 2 или 4, то формируем наказание
////     * <p>
////     * 2 - ИЗМЕНЕНА КВАЛИФИКАЦИЯ-УМЕНЬШЕНО НАКАЗАНИЕ
////     * 4 - БЕЗ ИЗМЕНЕНИЯ КВАЛИФИКАЦИИ-УМЕНЬШЕНО НАКАЗАНИЕ
////     */
////
////    private CourtDecisionRequestDTO courtDecisionChanging(ThirdCourtResolutionRequestDTO requestDTO, ThirdCourtDefendantRequestDTO defendant) {
////        Long changingReason = defendant.getChangingReason();
////
////        if (changingReason.equals(CHANGING_REASON_UPDATE_QUALIFICATION_LESS_PENALTY)
////                || changingReason.equals(CHANGING_REASON_NOT_UPDATE_QUALIFICATION_LESS_PENALTY)
////                || changingReason.equals(7L)) {
////            return handlePunishment(defendant, requestDTO.getEvidenceList(), requestDTO.getHearingDate());
////        } else {
////            if ((defendant.getMainPunishment() == null && defendant.getEndBase() == null)
////                    && (defendant.getMainPunishment() != null && defendant.getEndBase() != null))
////                throw new CourtValidationException(COURT_OLD_DATA_FOR_DEFENDANTS_REQUIRED);
////
////            if (defendant.getMainPunishment() != null)
////                return handlePunishment(defendant, requestDTO.getEvidenceList(), requestDTO.getHearingDate());
////            if (defendant.getEndBase() != null)
////                return handleTermination(defendant);
////        }
////
////        throw new CourtValidationException("No case for casAdditionalResult changing");
////    }
////
////    /**
////     * РЕШЕНИЕ СУДА НЕ ИЗМЕНЕНО
////     * В зависимости от параметров, формируем наказание или прекращение
////     */
////    private CourtDecisionRequestDTO courtDecisionNotChanging(ThirdCourtResolutionRequestDTO requestDTO, ThirdCourtDefendantRequestDTO defendant) {
//////        Optional<CourtDecisionRequestDTO> result = Optional.empty();
//////
//////        if (casAdditionalResult.equals(CASSATION_ADDITIONAL_RESULT_DECISION_NOT_CHANGED)) {
////        if (defendant.getEndBase() != null) {
////            return handleTermination(defendant);
////        } else {
////            return handlePunishment(defendant, requestDTO.getEvidenceList(), requestDTO.getHearingDate());
////        }
//////        return result;
////    }
//
//    private CourtDecisionRequestDTO courtCassationDecision(ThirdCourtResolutionRequestDTO requestDTO, ThirdCourtDefendantRequestDTO defendant) {
//        if (defendant.getMainPunishment() == null && defendant.getEndBase() == null) {
//            throw new CourtValidationException(APPEAL_RESULT_UNKNOWN);
//        }
//
//        if (defendant.getMainPunishment() != null && defendant.getEndBase() != null) {
//            throw new CourtValidationException(APPEAL_RESULT_NOT_CLEAR);
//        }
//
//        if (defendant.getMainPunishment() != null) {
//            return handlePunishment(defendant, requestDTO.getEvidenceList(), requestDTO.getHearingDate());
//        }
//        if (defendant.getEndBase() != null) {
//            return handleTermination(defendant);
//        }
//
//        throw new CourtValidationException("No case for casAdditionalResult changing");
//    }
//
//
//    private void validateSecondInstanceFields(List<ThirdCourtDefendantRequestDTO> defendants) {
//        for (ThirdCourtDefendantRequestDTO defendant : defendants) {
//            var endBase = defendant.getEndBase();
//            var changingReason = defendant.getChangingReason();
//            var cancelingReason = defendant.getCancellingReason();
//            var casAdditionalResult = defendant.getCassationAdditionalResult();
//
//            if (casAdditionalResult == null) {
//                throw new CourtValidationException(COURT_CASSATION_ADDITIONAL_RESULT_REQUIRED);
//            } else {
//
//                if (casAdditionalResult.equals(CASSATION_ADDITIONAL_RESULT_CANCELING_DECISION)) {
//                    if (cancelingReason == null)
//                        throw new CourtValidationException(COURT_CANCELING_REASON_REQUIRED);
//                    else if (cancelingReason.equals(CANCELING_REASON_CASE_TERMINATION) && endBase == null)
//                        throw new CourtValidationException(COURT_END_BASE_REQUIRED_FOR_TERMINATION);
//                }
//
//                if (casAdditionalResult.equals(CASSATION_ADDITIONAL_RESULT_UPDATE_DECISION) && changingReason == null)
//                    throw new CourtValidationException(COURT_CHANGING_REASON_REQUIRED);
//            }
//        }
//    }
//
//    /**
//     * Вычисляем Повторное Разбирательство
//     */
//    private boolean hasRetrialCourt(List<ThirdCourtDefendantRequestDTO> defendants) {
//        boolean hasRetrial = false;
//        boolean hasOtherCancelingReason = false;
//        for (ThirdCourtDefendantRequestDTO defendant : defendants) {
//            var endBase = defendant.getEndBase();
//            var cancelingReason = defendant.getCancellingReason();
//            var casAdditionalResult = defendant.getCassationAdditionalResult();
//
//            if (casAdditionalResult.equals(CASSATION_ADDITIONAL_RESULT_CANCELING_DECISION)
//                    && cancelingReason.equals(CANCELING_REASON_REPEATED_HEARING)
//                    && endBase == null)
//                hasRetrial = true;
//            else
//                hasOtherCancelingReason = true;
//        }
//
//        if (hasRetrial && hasOtherCancelingReason)
//            throw new CourtValidationException(RETREAL_COURT_EXCEPTION);
//
//        return hasRetrial;
//    }
//
//    /**
//     * Вычисляем Возврат Для Допрасследования
//     */
//    private boolean hasAdditionalInvestigation(List<ThirdCourtDefendantRequestDTO> defendants) {
//        boolean hasOtherCancelingReason = false;
//        boolean hasAdditionalInvestigation = false;
//
//        for (ThirdCourtDefendantRequestDTO defendant : defendants) {
//            var endBase = defendant.getEndBase();
//            var cancelingReason = defendant.getCancellingReason();
//            var casAdditionalResult = defendant.getCassationAdditionalResult();
//
//            if (casAdditionalResult.equals(CASSATION_ADDITIONAL_RESULT_CANCELING_DECISION)
//                    && cancelingReason.equals(CANCELING_REASON_RETURNING_FOR_ADDITIONAL_INVESTIGATION)
//                    && endBase == null)
//                hasAdditionalInvestigation = true;
//            else
//                hasOtherCancelingReason = true;
//        }
//
//        if (hasAdditionalInvestigation && hasOtherCancelingReason)
//            throw new CourtValidationException(RETREAL_COURT_EXCEPTION);
//
//        return hasAdditionalInvestigation;
//    }
//
//    private void validateDecision(ThirdCourtResolutionRequestDTO requestDTO) {
//        // это обработка решения, тут уже и так тольок 13 статаус окажеться. к таму же не понятно почему ткаой код ошибки, при чем туту финал резалт то?
//        //        if (notAllowedDecisionStatuses.contains(requestDTO.getStatus()))
//        //            throw new CourtValidationException(COURT_STATUS_AND_FINAL_RESULT_NOT_CONSISTENT);
//
//        List<ThirdCourtDefendantRequestDTO> defendants = requestDTO.getDefendant();
//        if (defendants == null || defendants.isEmpty())
//            throw new CourtValidationException(DEFENDANT_IS_EMPTY);
//
//        for (var defendant : defendants) {
//            if (defendant.getFinalResult() == null)
//                throw new CourtValidationException(FINAL_RESULT_REQUIRED);
//
//            var frAlias = getNameByValue(defendant.getFinalResult());
//
//
//            //  валидация частичного возврата судьи. Надо сделать выделение возвращенных нарушителей.
//            if (frAlias.equals(FR_I_CASE_RETURNING))
//                throw new CourtValidationException(FINAL_RESULT_4_AND_STATUS_13);
//
//            if (frAlias.equals(FR_I_RE_QUALIFICATION))
//                throw new CourtValidationException(FINAL_RESULT_115_AND_STATUS_13);
//
//            var additionalPunishment = defendant.getAdditionalPunishment();
//            if (additionalPunishment != null &&
//                    (additionalPunishment.equals(PUNISHMENT_PENALTY) || additionalPunishment.equals(PUNISHMENT_ARREST)))
//                throw new CourtValidationException(COURT_PUNISHMENT_ADDITIONAL);
//        }
//
//    }
//
//    private CourtDecisionRequestDTO handleTermination(ThirdCourtDefendantRequestDTO defendant) {
//        if (defendant.getEndBase() == null) {
//            throw new CourtValidationException("endBase required fro finalResult = 2");
//        }
//
//        var terminationReason = terminationReasonService.getById(defendant.getEndBase());
//
//        var decision = new CourtDecisionRequestDTO();
//        decision.setViolatorId(defendant.getViolatorId());
//        decision.setDefendantId(defendant.getDefendantId());
//        decision.setDecisionType(DecisionTypeAlias.TERMINATION);
//        decision.setTerminationReason(terminationReason);
//
//        Optional<List<CourtCompensationRequestDTO>> compensations = buildCompensationDecision(defendant);
//        compensations.ifPresent(decision::setCompensations);
//
//        return decision;
//    }
//
//    private CourtDecisionRequestDTO handlePunishment(ThirdCourtDefendantRequestDTO defendant, List<ThirdCourtEvidenceRequestDTO> evidences, LocalDateTime hearingDate) {
//        var courtDecision = new CourtDecisionRequestDTO();
//        courtDecision.setViolatorId(defendant.getViolatorId());
//        courtDecision.setDefendantId(defendant.getDefendantId());
//        courtDecision.setDecisionType(DecisionTypeAlias.PUNISHMENT);
//        courtDecision.setArticle33(defendant.isArticle33Applied());
//        courtDecision.setArticle34(defendant.isArticle34Applied());
//        //        courtDecision.setExecutionFromDate(hearingDate.toLocalDate());
//
//        if (defendant.getMainPunishment() == null)
//            throw new CourtGeneralException("Court main punishment required");
//
//        var mainType = punishmentTypeService.getById(defendant.getMainPunishment());
//
//        var withdrawalAmount = calcEvidenceAmount(evidences, defendant.getWithdrawalEvidences());
//        var confiscationAmount = calcEvidenceAmount(evidences, defendant.getConfiscationEvidences());
//
//        var mainPunishment = buildPunishment(
//                mainType,
//                defendant.getFineTotal(),
//                confiscationAmount,
//                withdrawalAmount,
//                defendant.getPunishmentDurationYear(),
//                defendant.getPunishmentDurationMonth(),
//                defendant.getPunishmentDurationDay(),
//                defendant.getArrest());
//        courtDecision.setMainPunishment(mainPunishment);
//
//        calculateAdditionalPunishment(defendant, evidences).ifPresent(courtDecision::setAdditionPunishment);
//
//        Optional<List<CourtCompensationRequestDTO>> compensations = buildCompensationDecision(defendant);
//        compensations.ifPresent(courtDecision::setCompensations);
//
//        return courtDecision;
//    }
//
//    private Long calcEvidenceAmount(List<ThirdCourtEvidenceRequestDTO> evidences, List<Long> ids) {
//        if (ids == null || evidences == null || evidences.isEmpty())
//            return null;
//
//        Map<Long, Long> mapEvidence = evidences
//                .stream()
//                .filter(e -> e.getAmount() != null)
//                .collect(Collectors
//                        .toMap(ThirdCourtEvidenceRequestDTO::getEvidenceCourtId, ThirdCourtEvidenceRequestDTO::getAmount));
//        long amount = 0;
//        for (Long id : ids) {
//            amount = amount + mapEvidence.getOrDefault(id, 0L);
//        }
//        return amount;
//    }
//
//    private Optional<CourtPunishmentRequestDTO> calculateAdditionalPunishment(ThirdCourtDefendantRequestDTO defendant,
//                                                                              List<ThirdCourtEvidenceRequestDTO> evidences) {
//        Optional<CourtPunishmentRequestDTO> result = Optional.empty();
//
//        var adPunishment = defendant.getAdditionalPunishment();
//        var withdrawalAmount = calcEvidenceAmount(evidences, defendant.getWithdrawalEvidences());
//        var confiscationAmount = calcEvidenceAmount(evidences, defendant.getConfiscationEvidences());
//
//        if (adPunishment != null) {
//            var additionalPunishmentType = punishmentTypeService.getById(adPunishment);
//            result = Optional.of(buildPunishment(
//                    additionalPunishmentType,
//                    null,
//                    confiscationAmount,
//                    withdrawalAmount,
//                    defendant.getAdditionalPunishmentDurationYear(),
//                    defendant.getAdditionalPunishmentDurationMonth(),
//                    defendant.getAdditionalPunishmentDurationDay(),
//                    defendant.getArrest()));
//        }
//        return result;
//    }
//
//    private CourtPunishmentRequestDTO buildPunishment(PunishmentType type,
//                                                      Long amount,
//                                                      Long confiscationAmount,
//                                                      Long withdrawalAmount,
//                                                      Integer years,
//                                                      Integer months,
//                                                      Integer days,
//                                                      Integer arrest) {
//        var punishment = new CourtPunishmentRequestDTO();
//
//        punishment.setPunishmentType(type);
//
//        punishment.setAmount(amount != null && amount == 0 ? null : amount);
//        punishment.setYears(years != null && years == 0 ? null : years);
//        punishment.setMonths(months != null && months == 0 ? null : months);
//        punishment.setDays(days != null && days == 0 ? null : days);
//
//        if (type.getAlias().equals(PunishmentTypeAlias.ARREST))
//            punishment.setArrestDate(arrest != null && arrest == 0 ? null : arrest);
//
//        if (type.getAlias().equals(PunishmentTypeAlias.CONFISCATION))
//            punishment.setAmount(confiscationAmount);
//
//        if (type.getAlias().equals(PunishmentTypeAlias.WITHDRAWAL))
//            punishment.setAmount(withdrawalAmount);
//
//        return punishment;
//    }
//
//    private void saveCourtData(ThirdCourtResolutionRequestDTO resolution, CourtStatus courtStatus) {
//        saveCaseCourtFields(resolution, courtStatus);
//        courtThirdMethodMovementService.updateViolators(resolution);
//    }
//
//    private void saveCaseCourtFields(ThirdCourtResolutionRequestDTO resolution, CourtStatus courtStatus) {
//        var caseId = resolution.getCaseId();
//        var newClaimId = calculateNewClaimId(resolution);
//
//        Optional<CourtCaseFields> optional = courtCaseFieldsService.findByCaseId(caseId);
//
//        var courtCaseFields = new CourtCaseFields();
//        if (optional.isPresent())
//            courtCaseFields = optional.get();
//
//        courtCaseFields.setCaseId(caseId);
//        courtCaseFields.setClaimId(newClaimId);
//        courtCaseFields.setStatus(courtStatus);
//        courtCaseFields.setInstance(resolution.getInstance());
//        courtCaseFields.setProtest(resolution.isProtest());
//        courtCaseFields.setUseVcc(resolution.isUseVcc());
//        courtCaseFields.setCaseNumber(resolution.getCaseNumber());
//        courtCaseFields.setJudge(resolution.getJudge());
//        courtCaseFields.setHearingDate(resolution.getHearingDate());
//        courtCaseFields.setIsPaused(resolution.isPaused());
//
//        var courtId = resolution.getCourt();
//        var movements = resolution.getCaseMovement();
//        if (movements != null) {
//            courtCaseFields.setCaseMergeId(movements.getClaimMergeId());
//            courtCaseFields.setCaseReviewId(movements.getClaimReviewId());
//
//            if (movements.getOtherCourtId() != null)
//                courtId = movements.getOtherCourtId();
//        }
//
//        List<ThirdCourtDefendantRequestDTO> defendants = resolution.getDefendant();
//        if (resolution.getStatus().equals(RETURNED.getValue())) {
//            courtCaseFields.setReturnReason(getReturnReason(defendants));
//        }
//
//        var courtTransfer = courtTransferService.findByExternalId(courtId);
//
//        courtCaseFields.setRegionId(courtTransfer.getInternalRegionId());
//        courtCaseFields.setDistrictId(courtTransfer.getInternalDistrictId());
//
//        courtCaseFieldsService.create(courtCaseFields);
//
//        eventDataService.setCourtDTOForFields(courtEventHolder.getCurrentInstance(), courtCaseFields);
//    }
//
//
//    private void saveFinalResultAndArticles(ThirdCourtResolutionRequestDTO resolution) {
//        if (resolution != null && resolution.getDefendant() != null) {
//            var caseId = resolution.getCaseId();
//
//            for (var defendant : resolution.getDefendant()) {
//                if (defendant.getFinalResult() != null) {
//                    var fr = new CourtFinalResultDecision();
//                    fr.setCaseId(caseId);
//                    fr.setViolatorId(defendant.getViolatorId());
//                    fr.setFinalResult(defendant.getFinalResult());
//                    fr.setCassationAdditionalResult(defendant.getCassationAdditionalResult());
//                    fr.setCancelingReason(defendant.getCancellingReason());
//                    fr.setChangingReason(defendant.getChangingReason());
//                    fr.setInstance(defendant.getFinalResult() <= 115L ? InstancesAliases.FIRST : InstancesAliases.SECOND);
//
//                    finalResultDecisionService.create(fr);
//
//                    List<CourtArticleResultDecision> articles = new ArrayList<>();
//                    List<CourtArticleResultDecision> mainArticles = new ArrayList<>();
//
//                    if (defendant.getArticleResults() != null && !defendant.getArticleResults().isEmpty()) {
//                        for (var courtArticle : defendant.getArticleResults()) {
//                            var article = new CourtArticleResultDecision();
//
//                            article.setArticleId(courtArticle.getArticleId());
//                            article.setArticlePartId(courtArticle.getArticlePartId());
//                            article.setArticleResult(courtArticle.getResult());
//                            article.setFinalResult(fr);
//
//                            mainArticles.add(article);
//                            var reArticles = calcReArticles(courtArticle, fr);
//
//                            articles = Stream
//                                    .of(mainArticles, reArticles)
//                                    .flatMap(Collection::stream)
//                                    .collect(Collectors.toList());
//                        }
//                        articlesDecisionService.saveAll(articles);
//                    }
//                }
//            }
//        }
//    }
//
//    private List<CourtArticleResultDecision> calcReArticles(ThirdCourtArticleRequestDTO courtArticle, CourtFinalResultDecision fr) {
//        var changedTo = courtArticle.getChangedTo();
//        if (changedTo == null || changedTo.isEmpty())
//            return Collections.emptyList();
//
//        var articles = new ArrayList<CourtArticleResultDecision>();
//        for (var changed : changedTo) {
//            var article = new CourtArticleResultDecision();
//
//            article.setFinalResult(fr);
//            article.setArticleId(courtArticle.getArticleId());
//            article.setArticlePartId(courtArticle.getArticlePartId());
//            article.setReArticleId(changed.getArticleId());
//            article.setReArticlePartId(changed.getArticlePartId());
//
//            articles.add(article);
//        }
//        return articles;
//    }
//
//    private Long calculateNewClaimId(ThirdCourtResolutionRequestDTO resolution) {
//        var newClaimId = resolution.getClaimId();
//        if (resolution.getCaseMovement() != null && resolution.getCaseMovement().getClaimReviewId() != null)
//            newClaimId = resolution.getClaimId();
//
//        if (resolution.getCaseMovement() != null && resolution.getCaseMovement().getOtherCourtClaimId() != null)
//            newClaimId = resolution.getCaseMovement().getOtherCourtClaimId();
//
//        return newClaimId;
//    }
//
//    private Long calculateOldClaimId(ThirdCourtResolutionRequestDTO resolution) {
//        var oldClaimId = resolution.getClaimId();
//
//        if (resolution.getCaseMovement() != null && resolution.getCaseMovement().getClaimReviewId() != null) {
//
//            // todo очень сомнительный код. может давать баги если движение сохраниться раньше
//            var movements = courtAdmCaseMovementService
//                    .findAllByCaseAndClaimIds(resolution.getCaseId(), resolution.getClaimId());
//
//            if (movements.isEmpty())
//                oldClaimId = resolution.getCaseMovement().getClaimReviewId();
//        }
//
//        return oldClaimId;
//    }
//
//    private void validation(ThirdCourtResolutionRequestDTO resolution) {
//        if (resolution == null)
//            throw new CourtValidationException(REQUEST_BODY_REQUIRED);
//
//        validateGeneralFields(resolution);
//        validatePauseField(resolution);
//        validateMovements(resolution);
//        validate308(resolution);
//        validateMergedAndReturned(resolution);
//    }
//
//    private Long getReturnReason(List<ThirdCourtDefendantRequestDTO> defendants) {
//        return defendants.stream()
//                .filter(Objects::nonNull)
//                .map(ThirdCourtDefendantRequestDTO::getReturnReason)
//                .filter(Objects::nonNull)
//                .max(Comparator.comparing(Long::longValue))
//                .orElseThrow(() -> new CourtValidationException(RETURN_REASON_REQUIRED));
//    }
//
//    private void validateReturnReason(List<ThirdCourtDefendantRequestDTO> defendants) {
//        if (defendants.isEmpty())
//            throw new CourtValidationException(DEFENDANT_IS_EMPTY);
//
//        for (var defendant : defendants) {
//            var finalResult = getNameByValue(defendant.getFinalResult());
//
//            if (!finalResult.equals(FR_I_CASE_RETURNING))
//                throw new CourtValidationException(FINAL_RESULT_MAST_BE_4);
//
//            if (defendant.getReturnReason() == null)
//                throw new CourtValidationException(RETURN_REASON_REQUIRED);
//        }
//    }
//
//    private void validateGeneralFields(ThirdCourtResolutionRequestDTO resolution) {
//        if (resolution.getCaseId() == null)
//            throw new CourtValidationException(CASE_ID_REQUIRED);
//        if (resolution.getClaimId() == null)
//            throw new CourtValidationException(CLAIM_ID_REQUIRED);
//        if (resolution.getCourt() == null)
//            throw new CourtValidationException(COURT_FIELD_REQUIRED);
//    }
//
//    private void validatePauseField(ThirdCourtResolutionRequestDTO resolution) {
//        if (resolution.getStatus().equals(CourtStatusAlias.PAUSED.getValue()) && !resolution.isPaused())
//            throw new CourtValidationException(IS_PAUSED_TRUE);
//
//        if (!resolution.getStatus().equals(CourtStatusAlias.PAUSED.getValue()) && resolution.isPaused())
//            throw new CourtValidationException(IS_PAUSED_FALSE);
//    }
//
//    private void validateMovements(ThirdCourtResolutionRequestDTO resolution) {
//        var movements = resolution.getCaseMovement();
//
//        if (movements != null) {
//            if (movements.getOtherCourtClaimId() != null && movements.getClaimMergeId() != null)
//                throw new CourtValidationException(TRANSFER_AND_MERGE_IDS_ERROR);
//
//            if (movements.getOtherCourtClaimId() != null && movements.getOtherCourtId() == null)
//                throw new CourtValidationException(COURT_FIELD_REQUIRED);
//
//            validateSeparation(movements.getCaseSeparation());
//        }
//    }
//
//    private void validateMergedAndReturned(ThirdCourtResolutionRequestDTO resolution) {
//        var caseFields = courtCaseFieldsService
//                .getByCaseId(resolution.getCaseId());
//
//        if (ADM_STATUSES_NOT_ALLOWED_MADE_DECISION.contains(caseFields.getStatusId()) && resolution.getStatus().equals(MERGED.getValue()))
//            throw new CourtValidationException(ADM_CASE_ALREADY_MERGED_OR_RETURNED);
//    }
//
//    private void validate308(ThirdCourtResolutionRequestDTO resolution) {
//        var admCase = admCaseService.getById(resolution.getCaseId());
//
//        if (admCase.getIs308()) {
//            var movements = resolution.getCaseMovement();
//
//            if (movements != null && (movements.getCaseSeparation() != null || movements.getClaimMergeId() != null))
//                throw new CourtValidationException(COURT_SEPARATION_AND_MERGING_BY_308_NOT_ALLOWED);
//        }
//    }
//
//    private void validateAdmStatus(ThirdCourtResolutionRequestDTO resolution, Long claimId) {
//        // Эта проверка мешает обрабатывать пересмотры.
//        // а блок пересмотра в мувементе обрабатываеться тольок после этой проверки.
////        var caseId = resolution.getCaseId();
////
////        var admCase = admCaseService
////                .getByIdAndClaimId(caseId, claimId)
////                .orElseThrow(() -> new CourtValidationException(COURT_ADM_CASE_NOT_FOUND, logId));
////
////        var instances = finalResultDecisionService.findByCaseId(caseId);
////        var instance = InstancesAliases.OTHER;
////
////        if (resolution.getDefendant() != null && resolution.getDefendant().get(0).getFinalResult() != null) {
////            var defendant = resolution.getDefendant().get(0);
////            instance = defendant.getFinalResult() <= 115L ? InstancesAliases.FIRST : InstancesAliases.SECOND;
////        }
////
////        if (ADM_STATUS_ALIASES_NOT_ALLOWED_FOR_FIRST_INSTANCE.contains(admCase.getStatus().getAlias()) && instances.contains(instance))
////            throw new CourtValidationException(COURT_ADM_CASE_ALREADY_HAS_DECISION_FROM_COURT, logId);
//    }
//
//    private void validateSeparation(List<ThirdCourtCaseSeparationRequestDTO> separations) {
//        if (separations != null && !separations.isEmpty())
//            for (var elem : separations)
//                if (elem.getClaimSeparationId() == null || elem.getCaseSeparationViolatorId().isEmpty())
//                    throw new CourtSeparationException(elem);
//    }
//}
