package uz.ciasev.ubdd_service.service.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.CompensationRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.DecisionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.EvidenceDecisionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.PunishmentRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.SingleResolutionRequestDTO;
import uz.ciasev.ubdd_service.entity.evidence.Evidence;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.VictimTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.resolution.DecisionTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.exception.*;
import uz.ciasev.ubdd_service.service.main.ActorService;
import uz.ciasev.ubdd_service.service.main.admcase.CalculatingService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;
import uz.ciasev.ubdd_service.service.settings.ArticleSettingsService;
import uz.ciasev.ubdd_service.utils.AdmEntityList;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResolutionValidationServiceImpl implements ResolutionValidationService {

    private final ValidationService validationService;
    private final ActorService actorService;
    private final PunishmentValidationService punishmentValidationService;
    private final CalculatingService calculatingService;
    private final ArticleSettingsService articleSettingsService;
    private final ProtocolService protocolService;

    private final EnumMap<DecisionTypeAlias, DecisionValidator> decisionTypeValidatorMap = new EnumMap<>(DecisionTypeAlias.class);

    {
        decisionTypeValidatorMap.put(DecisionTypeAlias.PUNISHMENT, this::validatePunishmentDecision);
        decisionTypeValidatorMap.put(DecisionTypeAlias.TERMINATION, this::validateTerminationDecision);
    }

    @Override
    public void validateConsider(User user, AdmCase admCase, SingleResolutionRequestDTO requestDTO) {

        if (requestDTO.getArticlePart() != null) {

            if (!calculatingService.isConsideredUser(user, requestDTO.getArticlePart())) {
                throw new ValidationException(ErrorCode.NOT_CONSIDER_OF_ARTICLE_PART);
            }

            if (requestDTO.getArticlePart().isCourtOnly()) {
                throw new ValidationException(ErrorCode.COURT_ONLY_ARTICLE_PART);
            }

        }

    }

    @Override
    public void validateDecision(DecisionRequestDTO requestDTO) {
        ValidationCollectingError error = new ValidationCollectingError();

        DecisionValidator validator = decisionTypeValidatorMap.get(requestDTO.getDecisionType());
        if (validator == null) {
            error.add(ErrorCode.DECISION_TYPE_DEACTIVATED);
            error.throwErrorIfNotEmpty();
        }
        validator.validate(error, requestDTO);

        error.throwErrorIfNotEmpty();
    }

    @Override
    public void validateDecisions(AdmEntityList<Violator> violators, List<? extends DecisionRequestDTO> decisions) {
        List<Long> decisionsId = decisions.stream().map(DecisionRequestDTO::getViolatorId).collect(Collectors.toList());

        if (validationService.checkHasDuplicate(decisionsId)) {
            throw new DecisionsContainDuplicateViolatorException();
        }


        Set<Long> violatorsIdSet = violators.getIds();
        Set<Long> decisionsIdSet = new HashSet<>(decisionsId);

        if (validationService.isNotEquals(violatorsIdSet, decisionsIdSet)) {
            throw new ViolatorAndDecisionNotConsistException(violatorsIdSet, decisionsIdSet);
        }

        for(DecisionRequestDTO decision : decisions) {
            validateCourtCompensationsByViolators(violators, decision.getCompensations());
        }
    }

    @Override
    public void validateDecisionByProtocol(Violator violator, DecisionRequestDTO decision) {
        Protocol protocol = protocolService.findSingleMainByAdmCaseId(violator.getAdmCaseId());
        LocalDate toDate = protocol.getViolationTime().toLocalDate();

        ArticlePart articlePart = decision.getArticlePart();
        PunishmentRequestDTO mainPunishment = decision.getMainPunishment();

        if (decision.getDecisionType().is(DecisionTypeAlias.TERMINATION)) {
            return;
        }

        if (articlePart.isPenaltyOnly() && mainPunishment.getPunishmentType().getAlias() != PunishmentTypeAlias.PENALTY) {
            throw new PenaltyOnlyArticlePartException();
        }

        if (mainPunishment.getPunishmentType().getAlias() != PunishmentTypeAlias.PENALTY) {
            return;
        }

        // validatePenaltyAmount(decision, toDate);
    }

    @Override
    public void validateCourtCompensationsByViolators(AdmEntityList<Violator> violators,
                                                      List<? extends CompensationRequestDTO> compensations) {
        Map<Long, List<CompensationRequestDTO>> violatorCompensations = compensations.stream()
                .collect(Collectors.groupingBy(CompensationRequestDTO::getViolatorId));

        violatorCompensations.forEach((violatorId, compensationsDTOS) -> {
            Violator violator = violators.findId(violatorId).orElseThrow(ViolatorAndDecisionNotConsistException::new);
            validateCourtCompensationsByViolator(violator, compensationsDTOS);
        });
    }

    public void validateOrganCompensationsByViolator(Violator violator, List<? extends CompensationRequestDTO> compensations) {
        ValidationCollectingError error = new ValidationCollectingError();

        error.addIf(
                compensations.stream().filter(comp -> comp.getVictimType().getAlias().equals(VictimTypeAlias.VICTIM)).map(CompensationRequestDTO::getVictimId).anyMatch(Objects::isNull),
                ErrorCode.VICTIM_COMPENSATION_HAS_NO_VICTIM
        );

        validateBaseCompensationsByViolator(error, violator, compensations);

        error.addIf(
                !actorService.isAllVictimsRelatedWithViolator(compensations.stream().map(CompensationRequestDTO::getVictimId).filter(Objects::nonNull).collect(Collectors.toList()), violator),
                ErrorCode.NOT_ALL_VICTIM_IN_COMPENSATION_LIST_RELATED_WITH_VIOLATOR
        );

        error.throwErrorIfNotEmpty();
    }

    public void validateCourtCompensationsByViolator(Violator violator, List<? extends CompensationRequestDTO> compensations) {
        ValidationCollectingError error = new ValidationCollectingError();

        validateBaseCompensationsByViolator(error, violator, compensations);

        error.addIf(
                !actorService.isAllVictimsRelatedWithAdmCase(compensations.stream().map(CompensationRequestDTO::getVictimId).filter(Objects::nonNull).collect(Collectors.toList()), violator.getAdmCaseId()),
                ErrorCode.NOT_ALL_VICTIM_IN_COMPENSATION_LIST_RELATED_WITH_VIOLATOR
        );

        //  2022-08-03 Бегзод разрешил сохранять ущербы потерпевшим без указания потерпевшего для суда. Суд упорно не хочет указывать их, а решения застревают.
//        error.addIf(
//                compensations.stream().filter(comp -> comp.getVictimType().getAlias().equals(VictimTypeAlias.VICTIM)).map(CompensationRequestDTO::getVictimId).anyMatch(Objects::isNull),
//                ErrorCode.VICTIM_COMPENSATION_HAS_NO_VICTIM
//        );

        error.throwErrorIfNotEmpty();
    }

    private void validateBaseCompensationsByViolator(ValidationCollectingError error, Violator violator, List<? extends CompensationRequestDTO> compensations) {
        error.addIf(
                validationService.checkHasDuplicate(compensations.stream().map(CompensationRequestDTO::getVictimId).filter(Objects::nonNull).collect(Collectors.toList())),
                ErrorCode.VICTIM_IN_COMPENSATION_LIST_NOT_UNIQUE
        );

        error.addIf(
                compensations.stream().filter(comp -> comp.getVictimType().getAlias().equals(VictimTypeAlias.GOVERNMENT)).map(CompensationRequestDTO::getVictimId).anyMatch(Objects::nonNull),
                ErrorCode.GOVERNMENT_COMPENSATION_HAS_VICTIM
        );
    }

    @Override
    public void validateEvidenceDecisions(AdmEntityList<Evidence> evidences,
                                          List<? extends EvidenceDecisionRequestDTO> evidenceDecisions) {

        ValidationCollectingError error = new ValidationCollectingError();

        Set<Long> evidencesIdSet = evidences.getIds();
        List<Long> evidenceOfDecisionIds = evidenceDecisions
                .stream()
                .map(EvidenceDecisionRequestDTO::getEvidenceId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        error.addIf(
                validationService.checkHasDuplicate(evidenceOfDecisionIds),
                ErrorCode.EVIDENCE_DECISION_CONTENT_DUPLICATE_EVIDENCE
        );

        error.addIf(
                validationService.isNotEquals(evidencesIdSet, new HashSet<>(evidenceOfDecisionIds)),
                ErrorCode.EVIDENCE_AND_EVIDENCE_DECISION_NOT_CONSIST
        );

        error.throwErrorIfNotEmpty();
    }

    private void validatePenaltyAmount(DecisionRequestDTO decision, LocalDate toDate) {
//        Long min = articleSettingsService.calculateMinForDate(toDate, decision.getIsJuridic(), decision.getArticlePart()).orElseThrow(PenaltyNotAllowedByArticlePartException::new);
//        Long max = articleSettingsService.calculateMaxForDate(toDate, decision.getIsJuridic(), decision.getArticlePart()).orElseThrow(PenaltyNotAllowedByArticlePartException::new);

        Pair<Optional<Long>, Optional<Long>> penaltyRange = articleSettingsService.calculateRangeForDate(toDate, decision.getIsJuridic(), decision.getArticlePart());
        Long min = penaltyRange.getFirst().orElseThrow(PenaltyNotAllowedByArticlePartException::new);
        Long max = penaltyRange.getSecond().orElseThrow(PenaltyNotAllowedByArticlePartException::new);

        Long penaltyAmount = decision.getMainPunishment().getAmount();

        if (min.compareTo(penaltyAmount) > 0) {
            throw new PenaltyTooSmallException();
        }

        if (max.compareTo(penaltyAmount) < 0) {
            throw new PenaltyTooLargeException();
        }
    }

    private void validateTerminationDecision(ValidationCollectingError error, DecisionRequestDTO decision) {
        error.addIf(Objects.isNull(decision.getTerminationReason()), "TERMINATION_REASON_REQUIRED");
        error.addIf(Objects.nonNull(decision.getMainPunishment()), "MAIN_PUNISHMENT_NOT_ALLOW");
        error.addIf(Objects.nonNull(decision.getAdditionPunishment()), "ADDITION_PUNISHMENT_NOT_ALLOW");
        error.addIf(
                LocalDate.now().isBefore(decision.getExecutionFromDate()),
                "EXECUTION_FROM_DATE_IN_FUTURE"
        );
    }

    private void validatePunishmentDecision(ValidationCollectingError error, DecisionRequestDTO decision) {
        error.addIf(Objects.nonNull(decision.getTerminationReason()), "TERMINATION_REASON_NOT_ALLOW");

        PunishmentRequestDTO mainPunishment = decision.getMainPunishment();
        if (Objects.isNull(mainPunishment)) {
            error.add("MAIN_PUNISHMENT_REQUIRED");
        } else {
//            punishmentValidationService.validatePunishment(error, decision.getMainPunishment());
            validateAdditionType(error, decision);
        }
    }

    private void validateAdditionType(ValidationCollectingError error, DecisionRequestDTO decision) {
        if (Objects.isNull(decision.getAdditionPunishment())) {
            return;
        }

        PunishmentTypeAlias additionTypeAlias = decision.getAdditionPunishment().getPunishmentType().getAlias();

        error.addIf(
                Objects.equals(decision.getMainPunishment().getPunishmentType().getAlias(), additionTypeAlias),
                "MAIN_AND_ADDITION_PUNISHMENT_TYPE_EQUALS"
        );

        error.addIf(
                Objects.equals(additionTypeAlias, PunishmentTypeAlias.PENALTY)
                        || Objects.equals(additionTypeAlias, PunishmentTypeAlias.ARREST)
                        || Objects.equals(additionTypeAlias, PunishmentTypeAlias.DEPORTATION)
                        || Objects.equals(additionTypeAlias, PunishmentTypeAlias.COMMUNITY_WORK),
                //   не факт                || Objects.equals(additionTypeAlias, PunishmentTypeAlias.MEDICAL_PENALTY)
                "UNACCEPTABLE_ADDITION_PUNISHMENT_TYPE"
        );

//        punishmentValidationService.validatePunishment(error, decision.getAdditionPunishment());
    }


    @FunctionalInterface
    public interface DecisionValidator {
        void validate(ValidationCollectingError error, DecisionRequestDTO requestDTO);
    }
}
