package uz.ciasev.ubdd_service.mvd_core.api.court.service.three;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.court.CourtArticleResultDecision;
import uz.ciasev.ubdd_service.entity.court.CourtCaseFields;
import uz.ciasev.ubdd_service.entity.court.CourtFinalResultDecision;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatus;
import uz.ciasev.ubdd_service.entity.dict.court.InstancesAliases;
import uz.ciasev.ubdd_service.event.AdmEventService;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;
import uz.ciasev.ubdd_service.mvd_core.api.court.CourtEventHolder;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtArticleRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtDefendantRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtResolutionRequestDTO;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.court.CourtAdmCaseMovementService;
import uz.ciasev.ubdd_service.service.court.CourtArticlesDecisionService;
import uz.ciasev.ubdd_service.service.court.CourtCaseFieldsService;
import uz.ciasev.ubdd_service.service.court.CourtFinalResultDecisionService;
import uz.ciasev.ubdd_service.service.court.trans.CourtTransferDictionaryService;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.service.main.resolution.CourtResolutionMainService;
import uz.ciasev.ubdd_service.service.publicapi.dto.eventdata.PublicApiWebhookEventDataCourtDTO;
import uz.ciasev.ubdd_service.service.publicapi.eventdata.PublicApiWebhookEventCourtDataService;
import uz.ciasev.ubdd_service.service.publicapi.eventdata.PublicApiWebhookEventPopulationService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias.RESOLVED;
import static uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias.RETURNED;
import static uz.ciasev.ubdd_service.exception.court.CourtValidationException.COURT_FINAL_RESULT_NOT_SUPPORTED;
import static uz.ciasev.ubdd_service.exception.court.CourtValidationException.RETURN_REASON_REQUIRED;
import static uz.ciasev.ubdd_service.mvd_core.api.court.service.three.CourtFinalResultByInstanceAliases.*;

@Service
@RequiredArgsConstructor
public class UbddCourtResolutionServiceImpl implements UbddCourtResolutionService {

    private static final EnumSet<CourtFinalResultByInstanceAliases> FIRST_INSTANCES = EnumSet.of(
            FR_I_PUNISHMENT,
            FR_I_TERMINATION,
            FR_I_CASE_RETURNING,
            FR_I_SENT_TO_OTHER_COURT,
            FR_I_RE_QUALIFICATION);

    private static final EnumSet<CourtFinalResultByInstanceAliases> OTHER_INSTANCES = EnumSet.of(
            FR_II_FULLY_SATISFIED,
            FR_II_PARTIALLY_SATISFIED,
            FR_II_REJECTED,
            FR_II_NOT_RELEVANT,

            FR_III_FULLY_SATISFIED,
            FR_III_PARTIALLY_SATISFIED,
            FR_III_REJECTED,
            FR_III_NOT_RELEVANT,

            FR_IV_FULLY_SATISFIED,
            FR_IV_PARTIALLY_SATISFIED,
            FR_IV_REJECTED,
            FR_IV_NOT_RELEVANT);

    private static final Long CASSATION_ADDITIONAL_RESULT_DECISION_NOT_CHANGED = 540L;  //3L;

    private final AdmCaseService admCaseService;
    private final CourtResolutionMainService resolutionMainService;
    private final CourtCaseFieldsService courtCaseFieldsService;
    private final DictionaryService<CourtStatus> courtStatusService;
    private final CourtTransferDictionaryService courtTransferService;
    private final CourtArticlesDecisionService articlesDecisionService;
    private final CourtFinalResultDecisionService finalResultDecisionService;
    private final CourtThirdMethodMovementService courtThirdMethodMovementService;
    private final CourtEventHolder courtEventHolder;
    private final PublicApiWebhookEventCourtDataService eventDataService;
    private final PublicApiWebhookEventPopulationService webhookEventPopulationService;
    private final AdmEventService admEventService;
    private final ThirdMethodValidationServiceImpl validationService;
    private final ThirdMethodDecisionService decisionService;


    @Override
    @Transactional
    public void accept(ThirdCourtResolutionRequestDTO request) {
        handle(request);
    }

    void handle(ThirdCourtResolutionRequestDTO resolution) {

        CourtStatus status = courtStatusService.getById(resolution.getStatus());

        courtEventHolder.init();

        saveCourtData(resolution, status);

        calculateMovements(resolution);
        if (resolution.isEditing() && status.oneOf(RETURNED, RESOLVED)) {
            resolutionMainService.prepareCaseForEditing(resolution.getCaseId(), resolution.getClaimId());
        }

        switch (status.getAlias()) {
            case REGISTERED_IN_COURT:
            case JUDGE_APPOINTED:
            case PAUSED:
            case MERGED:
            case PROCESS_REVIEW:
            case PASSED_TO_ARCHIVE:
            case DECLINED_PASSED_TO_ARCHIVE:
            case RETURNED_FROM_ARCHIVE:
                //  При этих сттаусах мы просто обрабатываем движение и сохраняем данные суда.
                break;
            case RETURNED:
                returnCase(resolution);
                break;
            case RESOLVED:
                madeDecision(resolution);
                break;
            default:
                throw new CourtValidationException("Unknown status in method 3 ");
        }

        PublicApiWebhookEventDataCourtDTO courtEventDTO = courtEventHolder.close();
        webhookEventPopulationService.addCourtEvent(courtEventDTO);

    }

    /**
     * Статус "Решение вынесено (13)"
     * последовательность обработки блоков :
     * - блок регистрация
     * - блок caseMovements
     * - блок решение
     */
    private void madeDecision(ThirdCourtResolutionRequestDTO resolution) {
        if (isCassationUpholdReturn(resolution)) {
            returnCase(resolution);
            return;
        }


        if (FR_I_SENT_TO_OTHER_COURT.equals(getNameByValue(extractFinalResult(resolution)))) {
            makeSendToOtherCourt(resolution);
            return;
        }

        validateAndMadeDecision(resolution);
        saveFinalResultAndArticles(resolution);
    }

    private void makeSendToOtherCourt(ThirdCourtResolutionRequestDTO resolution) {
        saveFinalResultAndArticles(resolution);
    }


    /**
     * Статус "Возвращено (17)"
     * последовательность обработки блоков :
     * - блок регистрация
     * - блок caseMovements
     * - блок решение
     */
    private void returnCase(ThirdCourtResolutionRequestDTO resolution) {
        validationService.validateReturnReason(resolution);

        resolutionMainService.returnCaseFromCourt(resolution.getCaseId(), resolution.getClaimId());
        admEventService.fireEvent(
                AdmEventType.COURT_RETURN_ADM_CASE,
                admCaseService.getById(resolution.getCaseId())
        );


    }

    /**
     * Блок caseMovements обрабатываем всегда в след последовательности
     * Шаг 1. Пересмотр
     * Шаг 2. Разъединение
     * Шаг 3. Передача / Объединение
     * При передаче появляется новый claimId, который следует вернуть и использовать далее
     * Передача и Объединение никогда не придут в одном запросе (со слов суда)
     */
    private void calculateMovements(ThirdCourtResolutionRequestDTO resolution) {
        if (resolution.isEditing()) {
            return;
        }

        boolean isDuplicate = courtThirdMethodMovementService.hasDuplicateMovements(resolution);
        if (isDuplicate)
            return;

        var isRevision = courtThirdMethodMovementService.hasRevision(resolution.getCaseMovement());
        if (isRevision)
            courtThirdMethodMovementService.makeRevision(resolution);

        var isSeparation = courtThirdMethodMovementService.hasSeparation(resolution.getCaseMovement());
        if (isSeparation)
            courtThirdMethodMovementService.makeSeparation(resolution);

        var isTransfer = courtThirdMethodMovementService.hasTransfer(resolution.getCaseMovement());
        if (isTransfer)
            courtThirdMethodMovementService.makeTransfer(resolution);

        var isMerge = courtThirdMethodMovementService.hasMerge(resolution.getCaseMovement());
        if (isMerge)
            courtThirdMethodMovementService.makeMerge(resolution);

        if (!isRevision && !isTransfer)
            courtThirdMethodMovementService.makeRegistration(resolution);
    }

    private void validateAndMadeDecision(ThirdCourtResolutionRequestDTO resolution) {
        var claimId = calculateNewClaimId(resolution);

        var finalResult = getNameByValue(extractFinalResult(resolution));

        if (FIRST_INSTANCES.contains(finalResult)) {
            decisionService.handleFirstInstance(resolution);
        } else if (OTHER_INSTANCES.contains(finalResult)) {
            decisionService.handleOtherInstance(resolution, claimId);
        } else {
            throw new CourtValidationException(COURT_FINAL_RESULT_NOT_SUPPORTED);
        }
    }


    private Long extractFinalResult(ThirdCourtResolutionRequestDTO resolution) {
        var defendant = resolution.getDefendant().stream().findFirst().orElseThrow(() -> new CourtValidationException("Defendant is empty"));
        if (defendant.getFinalResult() == null) {
            throw new CourtValidationException("Defendant finalResult is empty");
        }
        return defendant.getFinalResult();
    }

    private void saveCourtData(ThirdCourtResolutionRequestDTO request, CourtStatus courtStatus) {
        saveCaseCourtFields(request, courtStatus);
        courtThirdMethodMovementService.updateViolators(request);
    }

    private void saveCaseCourtFields(ThirdCourtResolutionRequestDTO resolution, CourtStatus courtStatus) {
        var caseId = resolution.getCaseId();
        var newClaimId = calculateNewClaimId(resolution);

        CourtCaseFields courtCaseFields = courtCaseFieldsService.findByCaseId(caseId).orElseGet(CourtCaseFields::new);

        courtCaseFields.setCaseId(caseId);
        courtCaseFields.setClaimId(newClaimId);
        courtCaseFields.setStatus(courtStatus);
        courtCaseFields.setInstance(resolution.getInstance());
        courtCaseFields.setProtest(resolution.isProtest());
        courtCaseFields.setUseVcc(resolution.isUseVcc());
        courtCaseFields.setCaseNumber(resolution.getCaseNumber());
        courtCaseFields.setJudge(resolution.getJudge());
        courtCaseFields.setHearingDate(resolution.getHearingDate());
        courtCaseFields.setIsPaused(resolution.isPaused());

        var courtId = resolution.getCourt();
        var movements = resolution.getCaseMovement();
        if (movements != null) {
            courtCaseFields.setCaseMergeId(movements.getClaimMergeId());
            courtCaseFields.setCaseReviewId(movements.getClaimReviewId());

            if (movements.getOtherCourtId() != null)
                courtId = movements.getOtherCourtId();
        }

        List<ThirdCourtDefendantRequestDTO> defendants = resolution.getDefendant();
        if (resolution.getStatus().equals(RETURNED.getValue())) {
            courtCaseFields.setReturnReason(getReturnReason(defendants));
        }

        var courtTransfer = courtTransferService.findByExternalId(courtId);

        courtCaseFields.setRegionId(courtTransfer.getRegionId());
        courtCaseFields.setDistrictId(courtTransfer.getDistrictId());

        courtCaseFieldsService.save(courtCaseFields);

        eventDataService.setCourtDTOForFields(courtEventHolder.getCurrentInstance(), courtCaseFields);
    }


    private void saveFinalResultAndArticles(ThirdCourtResolutionRequestDTO resolution) {
        var caseId = resolution.getCaseId();

        for (var defendant : resolution.getDefendant()) {
            if (defendant.getFinalResult() != null) {
                var fr = new CourtFinalResultDecision();
                fr.setCaseId(caseId);
                fr.setViolatorId(defendant.getViolatorId());
                fr.setFinalResult(defendant.getFinalResult());
                fr.setCassationAdditionalResult(defendant.getCassationAdditionalResult());
                fr.setCancelingReason(defendant.getCancellingReason());
                fr.setChangingReason(defendant.getChangingReason());
                fr.setInstance(defendant.getFinalResult() <= 115L ? InstancesAliases.FIRST : InstancesAliases.SECOND);

                finalResultDecisionService.save(fr);

                List<CourtArticleResultDecision> articles = new ArrayList<>();
                List<CourtArticleResultDecision> mainArticles = new ArrayList<>();

                if (defendant.getArticleResults() != null && !defendant.getArticleResults().isEmpty()) {
                    for (var courtArticle : defendant.getArticleResults()) {
                        var article = new CourtArticleResultDecision();

                        article.setArticleId(courtArticle.getArticleId());
                        article.setArticlePartId(courtArticle.getArticlePartId());
                        article.setArticleResult(courtArticle.getResult());
                        article.setFinalResult(fr);

                        mainArticles.add(article);
                        var reArticles = calcReArticles(courtArticle, fr);

                        articles = Stream
                                .of(mainArticles, reArticles)
                                .flatMap(Collection::stream)
                                .collect(Collectors.toList());
                    }
                    articlesDecisionService.saveAll(articles);
                }
            }
        }
    }

    private List<CourtArticleResultDecision> calcReArticles(ThirdCourtArticleRequestDTO courtArticle, CourtFinalResultDecision fr) {
        var changedTo = courtArticle.getChangedTo();
        if (changedTo == null || changedTo.isEmpty())
            return Collections.emptyList();

        var articles = new ArrayList<CourtArticleResultDecision>();
        for (var changed : changedTo) {
            var article = new CourtArticleResultDecision();

            article.setFinalResult(fr);
            article.setArticleId(courtArticle.getArticleId());
            article.setArticlePartId(courtArticle.getArticlePartId());
            article.setReArticleId(changed.getArticleId());
            article.setReArticlePartId(changed.getArticlePartId());

            articles.add(article);
        }
        return articles;
    }

    private Long calculateNewClaimId(ThirdCourtResolutionRequestDTO request) {
        var newClaimId = request.getClaimId();

        if (request.getCaseMovement() != null && request.getCaseMovement().getOtherCourtClaimId() != null)
            newClaimId = request.getCaseMovement().getOtherCourtClaimId();

        return newClaimId;
    }

    private Long getReturnReason(List<ThirdCourtDefendantRequestDTO> defendants) {
        var returnReason = defendants.stream()
                .filter(Objects::nonNull)
                .map(ThirdCourtDefendantRequestDTO::getReturnReason)
                .filter(Objects::nonNull)
                .max(Comparator.comparing(Long::longValue))
                .orElse(99L);

        if (defendants.stream().anyMatch(defendant -> FR_I_CASE_RETURNING.equals(getNameByValue(defendant.getFinalResult())))) {
            return returnReason;
        } else {
            throw new CourtValidationException(RETURN_REASON_REQUIRED);
        }
    }


    private boolean isCassationUpholdReturn(ThirdCourtResolutionRequestDTO resolution) {
        List<ThirdCourtDefendantRequestDTO> defendants = resolution.getDefendant();
        for (ThirdCourtDefendantRequestDTO defendant : defendants) {
            if (defendant.getCassationAdditionalResult() != null
                    && defendant.getCassationAdditionalResult().equals(CASSATION_ADDITIONAL_RESULT_DECISION_NOT_CHANGED)
                    && defendant.getReturnReason() != null)
                return true;
        }
        return false;
    }
}
