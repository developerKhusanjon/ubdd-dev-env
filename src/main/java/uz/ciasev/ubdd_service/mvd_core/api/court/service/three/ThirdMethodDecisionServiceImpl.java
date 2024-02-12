package uz.ciasev.ubdd_service.mvd_core.api.court.service.three;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.*;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.court.*;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.VictimType;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransfer;
import uz.ciasev.ubdd_service.entity.dict.evidence.Currency;
import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceCategory;
import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceResult;
import uz.ciasev.ubdd_service.entity.dict.evidence.Measures;
import uz.ciasev.ubdd_service.entity.dict.resolution.DecisionTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentType;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias;
import uz.ciasev.ubdd_service.exception.court.CourtGeneralException;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;
import uz.ciasev.ubdd_service.service.dict.court.CourtFinalResultService;
import uz.ciasev.ubdd_service.service.court.trans.CourtTransferDictionaryService;
import uz.ciasev.ubdd_service.service.dict.VictimTypeDictionaryService;
import uz.ciasev.ubdd_service.service.dict.evidence.CurrencyDictionaryService;
import uz.ciasev.ubdd_service.service.dict.evidence.EvidenceCategoryDictionaryService;
import uz.ciasev.ubdd_service.service.dict.evidence.EvidenceResultDictionaryService;
import uz.ciasev.ubdd_service.service.dict.evidence.MeasureDictionaryService;
import uz.ciasev.ubdd_service.service.dict.resolution.PunishmentTypeDictionaryService;
import uz.ciasev.ubdd_service.service.dict.resolution.TerminationReasonDictionaryService;
import uz.ciasev.ubdd_service.service.main.migration.MainService;
import uz.ciasev.ubdd_service.service.main.resolution.CourtResolutionMainService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static uz.ciasev.ubdd_service.mvd_core.api.court.service.three.PresenceValue.*;
import static uz.ciasev.ubdd_service.entity.dict.court.CourtFinalResultAlias.PUNISHMENT;
import static uz.ciasev.ubdd_service.entity.dict.court.CourtFinalResultAlias.TERMINATION;
import static uz.ciasev.ubdd_service.exception.court.CourtValidationException.*;


@Service
@RequiredArgsConstructor
public class ThirdMethodDecisionServiceImpl implements ThirdMethodDecisionService {

    private static final Long CASSATION_ADDITIONAL_RESULT_CANCELING_DECISION = 538L;    //1L;
    private static final Long CASSATION_ADDITIONAL_RESULT_UPDATE_DECISION = 539L;       //2L;
    private static final Long CASSATION_ADDITIONAL_RESULT_DECISION_NOT_CHANGED = 540L;  //3L;

    private static final Long CANCELING_REASON_CASE_TERMINATION = 1L;
    private static final Long CANCELING_REASON_REPEATED_HEARING = 2L;
    private static final Long CANCELING_REASON_RETURNING_FOR_ADDITIONAL_INVESTIGATION = 3L;
    private static final Long CANCELING_REASON_PENALTY_IMPOSED = 4L;

    private final MeasureDictionaryService measureService;
    private final CurrencyDictionaryService currencyService;
    private final CourtResolutionMainService resolutionMainService;
    private final VictimTypeDictionaryService victimTypeService;
    private final CourtFinalResultService courtFinalResultService;
    private final CourtTransferDictionaryService courtTransferService;
    private final EvidenceResultDictionaryService evidenceResultService;
    private final PunishmentTypeDictionaryService punishmentTypeService;
    private final EvidenceCategoryDictionaryService evidenceCategoryService;
    private final TerminationReasonDictionaryService terminationReasonService;
    private final ThirdMethodValidationServiceImpl validationService;
    private final MainService mainService;


    /*  1 инстанция
            SENT_TO_COURT
                1.  Если это первая инстанция, то проверить не передача ли это в другой суд finalResult = 113.
                2.  Для передачи все finalResult должны быть 113.
                3.  Если есть активное решение (не отмененное), тогда перевести в статус "Исполнение не возможно" или "Отменено судом".
                4.  Если есть активные квитанции (не заблокированные ранее) по штрафу и ущербу надо их заблокировать на Ягоне.
                5.  Если решение уже "Исполнено" или "Частично исполнено", тогда статус нужно сбросить в историю.

            PUNISHMENT
                1.  Сформировать Решение с типом Punishment на mainPunishment and additionalPunishment.
                2.  Если это "Конфискация", "Лишение прав"  то автоматом сформировать Исполнение решения.
                3.  Выставить статус адм дела в "Вынесено решение", "В процессе исполнения" или "Исполнено"
                4.  Если было активное Решение( не отменное) тогда перевести его в статус "Отменено судом"
                5.  Если по предыдущему и/или новому решениям есть штраф или ущерб в пользу гос-ва, тогда обработать квитанции ПО АЛГОРИТМУ из Таблицы соответствий

            TERMINATION
                1.  Сформировать Решение с типом Termination and endBase.
                2.  Автоматом сформировать исполнение
                3.  Выставить статус адм дела в "Вынесено решение", "В процессе исполнения" или "Исполнено"
                4.  Если было активное Решение( не отменное) тогда перевести его в статус "Отменено судом"
                5.  Если есть активные квитанции (не заблокированные ранее) по штрафу и ущербу надо их заблокировать на Ягоне.
    */
    @Override
    public void handleFirstInstance(ThirdCourtResolutionRequestDTO requestDTO) {

        var courtTransfer = courtTransferService.findByExternalId(requestDTO.getCourt());

        Optional<List<CourtEvidenceDecisionRequestDTO>> evidences = buildEvidenceDecisions(requestDTO.getEvidenceList());

        List<CourtDecisionRequestDTO> decisions = requestDTO.getDefendant().stream().map(defendant -> {
            var finalResult = courtFinalResultService.getByCode(defendant.getFinalResult());

            if (finalResult.getAlias().equals(PUNISHMENT)) {
                return handlePunishment(defendant, requestDTO.getEvidenceList(), requestDTO.getHearingDate());
            }

            if (finalResult.getAlias().equals(TERMINATION)) {
                return handleTermination(defendant);
            }

            throw new CourtValidationException(String.format("Unknown decision case for final result %s", finalResult.getAlias()));
        }).collect(Collectors.toList());

        var resolution = new CourtResolutionRequestDTO();
        resolution.setCourtNumber(requestDTO.getCaseNumber());
        resolution.setResolutionTime(requestDTO.getHearingDate());
        resolution.setJudgeInfo(requestDTO.getJudge());
        resolution.setDecisions(decisions);
        resolution.setClaimId(requestDTO.getClaimId());
        resolution.setRegion(courtTransfer.getRegion());
        resolution.setDistrict(courtTransfer.getDistrict());
        evidences.ifPresent(resolution::setEvidenceDecisions);

        resolutionMainService.createCourtResolution(requestDTO.getCaseId(), resolution);
    }

    /*  2 инстанция
        CASSATION_ADDITIONAL_RESULT = 1
            END_BASE = not empty и CANCELING_REASON = 1 -> Дело прекращено
                1.  Сформировать Решение с типом Termination and endBase.
                2.  Автоматом сформировать исполнение
                3.  Выставить статус адм дела в "Вынесено решение", "В процессе исполнения" или "Исполнено"
                4.  Если было активное Решение( не отменное) тогда перевести его в статус "Отменено судом"
                5.  Если есть активные квитанции (не заблокированные ранее) по штрафу и ущербу надо их заблокировать на Ягоне.

            END_BASE = empty
                CANCELING_REASON = 2 -> Повторное разбирательство суда
                    0.  Все CANCELING_REASON должны быть одинаковые
                    1.  Выставить статус адм дела в "Отправлено в суд"
                    2.  Если было активное Решение( не отменное) тогда перевести его в статус "Отменено судом"
                    3.  Если есть активные квитанции (не заблокированные ранее) по штрафу и ущербу надо их заблокировать на Ягоне.

                CANCELING_REASON = 3 -> Возврат для доп расследования
                    1.  Выставить статус адм дела в "Возврат судом"
                    2.  Если было активное Решение( не отменное) тогда перевести его в статус "Отменено судом"
                    3.  Если есть активные квитанции (не заблокированные ранее) по штрафу и ущербу надо их заблокировать на Ягоне.

                CANCELING_REASON = 4 -> Назначено наказание
                    1.  Сформировать Решение с типом Punishment на mainPunishment and additionalPunishment.
                    2.  Если это "Конфискация", "Лишение прав"  то автоматом сформировать Исполнение решения.
                    3.  Выставить статус адм дела в "Вынесено решение", "В процессе исполнения" или "Исполнено"
                    4.  Если было активное Решение( не отменное) тогда перевести его в статус "Отменено судом"
                    5.  Если по предыдущему и/или новому решениям есть штраф или ущерб в пользу гос-ва, тогда обработать квитанции ПО АЛГОРИТМУ из Таблицы соответствий

        CASSATION_ADDITIONAL_RESULT = 2
            CHANGING_REASON = 1 -> Изменена квалификация
                Сохранить статьи

            CHANGING_REASON = 2 -> Изменена квалификация. Уменьшено наказание
                0.  Сохранить статьи
                1.  Сформировать Решение с типом Punishment на mainPunishment and additionalPunishment.
                2.  Если это "Конфискация", "Лишение прав"  то автоматом сформировать Исполнение решения.
                3.  Выставить статус адм дела в "Вынесено решение", "В процессе исполнения" или "Исполнено"
                4.  Если было активное Решение( не отменное) тогда перевести его в статус "Отменено судом"
                5.  Если по предыдущему и/или новому решениям есть штраф или ущерб в пользу гос-ва, тогда обработать квитанции ПО АЛГОРИТМУ из Таблицы соответствий

            CHANGING_REASON = 3 -> Изменена квалификация. Уменьшено не наказание
                Сохранить статьи

            CHANGING_REASON = 4 -> Без изменения квалификации. Уменьшено наказание
                1.  Сформировать Решение с типом Punishment на mainPunishment and additionalPunishment.
                2.  Если это "Конфискация", "Лишение прав"  то автоматом сформировать Исполнение решения.
                3.  Выставить статус адм дела в "Вынесено решение", "В процессе исполнения" или "Исполнено"
                4.  Если было активное Решение( не отменное) тогда перевести его в статус "Отменено судом"
                5.  Если по предыдущему и/или новому решениям есть штраф или ущерб в пользу гос-ва, тогда обработать квитанции ПО АЛГОРИТМУ из Таблицы соответствий

            CHANGING_REASON = 5 -> Изменено содержание решение
                Ничего не делаем
            CHANGING_REASON = 6 -> Отменена часть вещдоков
                Ничего не делаем
            CHANGING_REASON = 7 -> Изменена часть вещдоков
                Ничего не делаем
            CHANGING_REASON = 8 -> Другие причины
                Ничего не делаем
    */
    @Override
    public void handleOtherInstance(ThirdCourtResolutionRequestDTO requestDTO, Long claimId) {
        List<ThirdCourtDefendantRequestDTO> defendants = requestDTO.getDefendant();

        validationService.validateSecondInstanceFields(defendants);

        PresenceValue hasRetrialCourt = hasRetrialCourt(defendants);
        PresenceValue hasReturningForAdditionalInvestigation = hasAdditionalInvestigation(defendants);


        if (hasRetrialCourt.equals(PARTIALLY) && hasReturningForAdditionalInvestigation.equals(PARTIALLY)) {
            throw new CourtValidationException("Partially return for retrial and additional investigation");
        }

        if (hasRetrialCourt.equals(PARTIALLY)) {
            // пока илья сказал, что в послдующим решения прийдут тольок по пожаловавшимся.
            // это нас не устраивает, так что опка не обрабатываем
            throw new CourtValidationException(RETREAL_COURT_EXCEPTION);
        }

        if (hasRetrialCourt.equals(YES)) {
            resolutionMainService.otherCourtInstanceRetrialCase(requestDTO.getCaseId(), claimId);
            return;
        }


        if (hasReturningForAdditionalInvestigation.equals(YES)) {
            // все дело целиком было возврашено на доп расследование
            resolutionMainService.otherCourtInstanceReturnFromCourt(requestDTO.getCaseId(), claimId);
            return;
        }

        if (hasReturningForAdditionalInvestigation.equals(NO)) {
            // по всему делу вынесли решение
            makeOtherInstanceResolution(requestDTO, claimId, defendants);
            return;
        }

        if (hasReturningForAdditionalInvestigation.equals(PARTIALLY)) {
            // Часть нарушители, каторых вернули на доп расследование
            List<Long> violatorIds = getAdditionalInvestigationViolatorsId(defendants);


            AdmCase newAdmCase = mainService.courtSeparateAdmCase(requestDTO.getCaseId(), violatorIds, null);
            resolutionMainService.otherCourtInstanceReturnFromCourt(newAdmCase.getId(), claimId);


            // по остальным нарушителям вынесли решение
            List<ThirdCourtDefendantRequestDTO> defendantsForDecision = defendants.stream()
                    .filter(defendant -> !violatorIds.contains(defendant.getViolatorId()))
                    .collect(Collectors.toList());
            makeOtherInstanceResolution(requestDTO, claimId, defendantsForDecision);

            return;
        }

        throw new CourtValidationException("No case for handleOtherInstance");
    }


    private void makeOtherInstanceResolution(ThirdCourtResolutionRequestDTO requestDTO, Long claimId, List<ThirdCourtDefendantRequestDTO> defendantsForDecision) {
        CourtTransfer courtTransfer = courtTransferService.findByExternalId(requestDTO.getCourt());

        List<CourtDecisionRequestDTO> decisions = defendantsForDecision.stream()
                .map(defendant -> courtCassationDecision(requestDTO, defendant))
                .collect(Collectors.toList());


        Optional<List<CourtEvidenceDecisionRequestDTO>> evidences = buildEvidenceDecisions(requestDTO.getEvidenceList());

        var resolution = new CourtResolutionRequestDTO();
        resolution.setJudgeInfo(requestDTO.getJudge());
        resolution.setCourtNumber(requestDTO.getCaseNumber());
        resolution.setResolutionTime(requestDTO.getHearingDate());
        resolution.setDecisions(decisions);
        resolution.setClaimId(claimId);
        resolution.setRegion(courtTransfer.getRegion());
        resolution.setDistrict(courtTransfer.getDistrict());
        evidences.ifPresent(resolution::setEvidenceDecisions);

        resolutionMainService.createCourtResolution(requestDTO.getCaseId(), resolution);
    }


    private List<CourtCompensationRequestDTO> buildCompensationDecision(ThirdCourtDefendantRequestDTO defendant) {
        if (defendant.getExactedDamage() == null) {
            return List.of();
        }

        return defendant.getExactedDamage().stream()
                .map(damage -> {
                    CourtCompensationRequestDTO compensation = new CourtCompensationRequestDTO();

                    Currency currency = currencyService.getById(damage.getExactedDamageCurrency());
                    VictimType victimType = victimTypeService.getById(damage.getInFavorType());

                    compensation.setViolatorId(defendant.getViolatorId());
                    compensation.setAmount(damage.getExactedDamageTotal());
                    compensation.setPayerTypeId(damage.getFromWhom());
                    compensation.setPayerAdditionalInfo(Optional.ofNullable(damage.getFromWhomInn()).map(String::valueOf).orElse(null));
                    compensation.setVictimId(damage.getVictimId());
                    compensation.setCurrency(currency);
                    compensation.setVictimType(victimType);

                    return compensation;
                }).collect(Collectors.toList());

    }

    private Optional<List<CourtEvidenceDecisionRequestDTO>> buildEvidenceDecisions(List<ThirdCourtEvidenceRequestDTO> courtEvidences) {
        Optional<List<CourtEvidenceDecisionRequestDTO>> result = Optional.empty();

        if (courtEvidences != null && !courtEvidences.isEmpty()) {
//  2022-12-26 Убрала валидацию, потаму что суд все равноне неисправит, а решения принимать надо
//            validationService.validateEvidences(courtEvidences);
            List<CourtEvidenceDecisionRequestDTO> evidences = new ArrayList<>();
            for (ThirdCourtEvidenceRequestDTO elem : courtEvidences) {
                CourtEvidenceDecisionRequestDTO courtEvidence = new CourtEvidenceDecisionRequestDTO();

                EvidenceResult evidenceResult = Optional.ofNullable(elem.getEvidenceResult()).map(evidenceResultService::getById).orElse(null);
                EvidenceCategory evidenceCategory = Optional.ofNullable(elem.getEvidenceCategory()).map(evidenceCategoryService::getById).orElse(null);
                Measures measures = Optional.ofNullable(elem.getMeasureId()).map(measureService::getById).orElse(null);
                Currency currency = Optional.ofNullable(elem.getCurrencyId()).map(currencyService::getById).orElse(null);

                courtEvidence.setEvidenceId(elem.getEvidenceId());
                courtEvidence.setEvidenceResult(evidenceResult);
                courtEvidence.setEvidenceCategory(evidenceCategory);
                courtEvidence.setMeasure(measures);
                courtEvidence.setCurrency(currency);
                courtEvidence.setQuantity(elem.getEvidenceCountAndUnity());
                courtEvidence.setCost(elem.getAmount());
                courtEvidence.setPersonDescription(elem.getPersonDescription());
                courtEvidence.setEvidenceSudId(elem.getEvidenceCourtId());

                evidences.add(courtEvidence);
            }
            result = Optional.of(evidences);
        }

        return result;
    }


    private CourtDecisionRequestDTO courtCassationDecision(ThirdCourtResolutionRequestDTO requestDTO, ThirdCourtDefendantRequestDTO defendant) {
        if (defendant.getMainPunishment() == null && defendant.getEndBase() == null) {
            throw new CourtValidationException(APPEAL_RESULT_UNKNOWN);
        }

        if (defendant.getMainPunishment() != null && defendant.getEndBase() != null) {
            throw new CourtValidationException(APPEAL_RESULT_NOT_CLEAR);
        }

        if (defendant.getMainPunishment() != null) {
            return handlePunishment(defendant, requestDTO.getEvidenceList(), requestDTO.getHearingDate());
        }
        if (defendant.getEndBase() != null) {
            return handleTermination(defendant);
        }

        throw new CourtValidationException("No case for casAdditionalResult changing");
    }


    private CourtDecisionRequestDTO handleTermination(ThirdCourtDefendantRequestDTO defendant) {
        if (defendant.getEndBase() == null) {
            throw new CourtValidationException("endBase required fro finalResult = 2");
        }

        var terminationReason = terminationReasonService.getById(defendant.getEndBase());

        var decision = new CourtDecisionRequestDTO();
        decision.setViolatorId(defendant.getViolatorId());
        decision.setDefendantId(defendant.getDefendantId());
        decision.setDecisionType(DecisionTypeAlias.TERMINATION);
        decision.setTerminationReason(terminationReason);

        decision.setCompensations(buildCompensationDecision(defendant));

        return decision;
    }

    private CourtDecisionRequestDTO handlePunishment(ThirdCourtDefendantRequestDTO defendant, List<ThirdCourtEvidenceRequestDTO> evidences, LocalDateTime hearingDate) {

        var courtDecision = new CourtDecisionRequestDTO();
        courtDecision.setViolatorId(defendant.getViolatorId());
        courtDecision.setDefendantId(defendant.getDefendantId());
        courtDecision.setDecisionType(DecisionTypeAlias.PUNISHMENT);
        courtDecision.setArticle33(defendant.isArticle33Applied());
        courtDecision.setArticle34(defendant.isArticle34Applied());

        if (defendant.getMainPunishment() == null)
            throw new CourtGeneralException("Court main punishment required");

        var mainType = punishmentTypeService.getById(defendant.getMainPunishment());

        var withdrawalAmount = calcEvidenceAmount(evidences, defendant.getWithdrawalEvidences());
        var confiscationAmount = calcEvidenceAmount(evidences, defendant.getConfiscationEvidences());

        var mainPunishment = buildPunishment(
                mainType,
                defendant.getFineTotal(),
                defendant.getFineWithDiscount(),
                confiscationAmount,
                withdrawalAmount,
                defendant.getPunishmentDurationYear(),
                defendant.getPunishmentDurationMonth(),
                defendant.getPunishmentDurationDay(),
                defendant.getArrest());
        courtDecision.setMainPunishment(mainPunishment);

//        If main and additional punishment type is equal, the decision consists only main one.
        calculateAdditionalPunishment(defendant, evidences).ifPresent(additionalPunishment -> {
            if (!additionalPunishment.getPunishmentType().equals(mainType)){
                courtDecision.setAdditionPunishment(additionalPunishment);
            }
        });

        courtDecision.setCompensations(buildCompensationDecision(defendant));

        return courtDecision;
    }

    private Long calcEvidenceAmount(List<ThirdCourtEvidenceRequestDTO> evidences, List<Long> ids) {
        if (ids == null || ids.isEmpty() || evidences == null || evidences.isEmpty())
            return 0L;

        Map<Long, Long> mapEvidence = evidences
                .stream()
                .filter(e -> e.getAmount() != null)
                .collect(Collectors.toMap(
                        ThirdCourtEvidenceRequestDTO::getEvidenceCourtId,
                        ThirdCourtEvidenceRequestDTO::getAmount
                ));

        long amount = 0;
        for (Long id : ids) {
            amount = amount + mapEvidence.getOrDefault(id, 0L);
        }
        return amount;
    }

    private Optional<CourtPunishmentRequestDTO> calculateAdditionalPunishment(ThirdCourtDefendantRequestDTO defendant,
                                                                              List<ThirdCourtEvidenceRequestDTO> evidences) {
        Optional<CourtPunishmentRequestDTO> result = Optional.empty();

        var adPunishment = defendant.getAdditionalPunishment();
        var withdrawalAmount = calcEvidenceAmount(evidences, defendant.getWithdrawalEvidences());
        var confiscationAmount = calcEvidenceAmount(evidences, defendant.getConfiscationEvidences());

        if (adPunishment != null) {
            var additionalPunishmentType = punishmentTypeService.getById(adPunishment);
            result = Optional.of(buildPunishment(
                    additionalPunishmentType,
                    null,
                    null,
                    confiscationAmount,
                    withdrawalAmount,
                    defendant.getAdditionalPunishmentDurationYear(),
                    defendant.getAdditionalPunishmentDurationMonth(),
                    defendant.getAdditionalPunishmentDurationDay(),
                    defendant.getArrest()));
        }
        return result;
    }

    private CourtPunishmentRequestDTO buildPunishment(PunishmentType type,
                                                      Long amount,
                                                      ThirdCourtDiscountRequestDTO discount,
                                                      Long confiscationAmount,
                                                      Long withdrawalAmount,
                                                      Integer years,
                                                      Integer months,
                                                      Integer days,
                                                      Integer arrest) {
        var punishment = new CourtPunishmentRequestDTO();

        punishment.setPunishmentType(type);

        punishment.setAmount(amount != null && amount == 0 ? null : amount);
        punishment.setYears(years != null && years == 0 ? null : years);
        punishment.setMonths(months != null && months == 0 ? null : months);
        punishment.setDays(days != null && days == 0 ? null : days);

        if (type.getAlias().equals(PunishmentTypeAlias.ARREST))
            punishment.setArrestDate(arrest != null && arrest == 0 ? null : arrest);

        if (type.getAlias().equals(PunishmentTypeAlias.CONFISCATION))
            punishment.setAmount(confiscationAmount);

        if (type.getAlias().equals(PunishmentTypeAlias.WITHDRAWAL))
            punishment.setAmount(withdrawalAmount);

        if (type.getAlias().equals(PunishmentTypeAlias.PENALTY)) {
            if (discount != null) {
                punishment.setDiscount(
                        discount.getFirstTotalFine(),
                        discount.getFirstFineDeadline(),
                        discount.getSecondTotalFine(),
                        discount.getSecondFineDeadline()
                );
            }
        }

        return punishment;
    }

    /**
     * Вычисляем Повторное Разбирательство
     */
    private PresenceValue hasRetrialCourt(List<ThirdCourtDefendantRequestDTO> defendants) {
        boolean hasRetrial = false;
        boolean hasOtherCancelingReason = false;
        for (ThirdCourtDefendantRequestDTO defendant : defendants) {
            var endBase = defendant.getEndBase();
            var cancelingReason = defendant.getCancellingReason();
            var casAdditionalResult = defendant.getCassationAdditionalResult();

            if (casAdditionalResult.equals(CASSATION_ADDITIONAL_RESULT_CANCELING_DECISION)
                    && cancelingReason.equals(CANCELING_REASON_REPEATED_HEARING)
                    && endBase == null)
                hasRetrial = true;
            else
                hasOtherCancelingReason = true;
        }

        if (hasRetrial && hasOtherCancelingReason)
            return PARTIALLY;

        return hasRetrial ? YES : NO;
    }

    /**
     * Вычисляем Возврат Для Допрасследования
     */
    private PresenceValue hasAdditionalInvestigation(List<ThirdCourtDefendantRequestDTO> defendants) {
        boolean hasOtherCancelingReason = false;
        boolean hasAdditionalInvestigation = false;

        for (ThirdCourtDefendantRequestDTO defendant : defendants) {
            if (isAdditionalInvestigation(defendant)) {
                hasAdditionalInvestigation = true;
            } else {
                hasOtherCancelingReason = true;
            }
        }

        if (hasAdditionalInvestigation && hasOtherCancelingReason)
            return PARTIALLY;

        return hasAdditionalInvestigation ? YES : NO;
    }

    /**
     * Вычисляем Возврат Для Допрасследования
     */
    private List<Long> getAdditionalInvestigationViolatorsId(List<ThirdCourtDefendantRequestDTO> defendants) {
        return defendants.stream()
                .filter(this::isAdditionalInvestigation)
                .map(defendant -> defendant.getViolatorId())
                .collect(Collectors.toList());
    }

    private boolean isAdditionalInvestigation(ThirdCourtDefendantRequestDTO defendant) {
        var endBase = defendant.getEndBase();
        var cancelingReason = defendant.getCancellingReason();
        var casAdditionalResult = defendant.getCassationAdditionalResult();

        return (casAdditionalResult.equals(CASSATION_ADDITIONAL_RESULT_CANCELING_DECISION)
                && cancelingReason.equals(CANCELING_REASON_RETURNING_FOR_ADDITIONAL_INVESTIGATION)
                && endBase == null);
    }
}
