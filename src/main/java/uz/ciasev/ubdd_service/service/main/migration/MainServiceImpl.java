package uz.ciasev.ubdd_service.service.main.migration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.court.CourtEventHolder;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.damage.Damage;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.history.AdmCaseRegistrationType;
import uz.ciasev.ubdd_service.entity.participant.Participant;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.victim.Victim;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.exception.*;
import uz.ciasev.ubdd_service.service.publicapi.eventdata.PublicApiWebhookEventCourtDataService;
import uz.ciasev.ubdd_service.repository.document.DocumentRepository;
import uz.ciasev.ubdd_service.repository.EvidenceRepository;
import uz.ciasev.ubdd_service.repository.admcase.AdmCaseRepository;
import uz.ciasev.ubdd_service.repository.damage.DamageRepository;
import uz.ciasev.ubdd_service.service.history.HistoryService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.damage.DamageMainService;
import uz.ciasev.ubdd_service.service.damage.DamageService;
import uz.ciasev.ubdd_service.service.main.admcase.CalculatingService;
import uz.ciasev.ubdd_service.service.participant.ParticipantService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;
import uz.ciasev.ubdd_service.service.status.AdmCaseStatusService;
import uz.ciasev.ubdd_service.service.victim.VictimService;
import uz.ciasev.ubdd_service.service.violator.ViolatorService;

import java.util.*;
import java.util.stream.Collectors;

import static uz.ciasev.ubdd_service.entity.status.AdmStatusAlias.MERGED;


@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private final DamageMainService damageMainService;
    private final AdmCaseService admCaseService;
    private final ViolatorService violatorService;
    private final ProtocolService protocolService;
    private final VictimService victimService;
    private final ParticipantService participantService;
    private final DamageService damageService;
    private final EvidenceRepository evidenceRepository;
    private final DocumentRepository documentRepository;
    private final DamageRepository damageRepository;
    private final AdmCaseAccessService admCaseAccessService;
    private final SeparationService separationService;
    private final CalculatingService calculatingService;
    private final HistoryService admCaseMergeSeparationRegistrationService;
    private final CourtEventHolder courtEventHolder;
    private final PublicApiWebhookEventCourtDataService eventDataService;
    private final AdmCaseRepository admCaseRepository;
    private final AdmCaseStatusService admStatusService;

    @Override
    @Transactional
    public void courtMergeAdmCases(Long fromCaseId, Long toCaseId) {

        AdmCase fromAdmCase = admCaseService.getById(fromCaseId);
        admCaseAccessService.checkPermitActionWithAdmCase(ActionAlias.COURT_MERGE_CASE, fromAdmCase);

        AdmCase toAdmCase = admCaseService.getById(toCaseId);
        admCaseAccessService.checkPermitActionWithAdmCase(ActionAlias.COURT_MERGE_CASE, toAdmCase);

//        //  Суду нельзя обьеденять дела не со своими статьями
//        List<ArticlePart> fromCaseArticleParts = protocolService.findMainArticlePartsByAdmCase(fromAdmCase);
//        if (!validationService.checkAllPartConsideredByCourt(fromCaseArticleParts)) {
//            throw new MixingOfCourtAndOrganArticlesUnacceptable();
//        }
//
//        List<ArticlePart> toCaseArticleParts = protocolService.findMainArticlePartsByAdmCase(toAdmCase);
//        if (!validationService.checkAllPartConsideredByCourt(fromCaseArticleParts)) {
//            throw new MixingOfCourtAndOrganArticlesUnacceptable();
//        }

        mergeAdmCases(fromAdmCase, toAdmCase);

        eventDataService.setCourtDTOForMerge(courtEventHolder.getCurrentInstance(), toAdmCase);
    }

    @Override
    @Transactional
    public List<Violator> organMergeAdmCases(User user, Long fromCaseId, Long toCaseId) {

        if (fromCaseId.equals(toCaseId)) {
            throw new ValidationException(ErrorCode.ADM_CASE_EQUALS);
        }

        AdmCase fromAdmCase = admCaseService.getById(fromCaseId);
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.MERGE_CASE, fromAdmCase);

        AdmCase toAdmCase = admCaseService.getById(toCaseId);
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.MERGE_CASE, toAdmCase);

        validateOrganMerge(user, fromAdmCase, toAdmCase);

        return mergeAdmCases(fromAdmCase, toAdmCase);
    }

    @Override
    @Transactional
    public AdmCase courtSeparateAdmCase(Long fromCaseId, List<Long> violatorIds, Long claimId) {

        AdmCase fromAdmCase = admCaseService.getById(fromCaseId);
        for (Long violatorId : violatorIds) {
            Violator violator = violatorService.getById(violatorId);
            if (!violator.getAdmCaseId().equals(fromCaseId)) {
                throw new ValidationException(ErrorCode.VIOLATOR_AND_CASE_NOT_CONSIST);
            }
        }

        admCaseAccessService.checkPermitActionWithAdmCase(ActionAlias.COURT_SEPARATE_CASE, fromAdmCase);

        List<Protocol> separatedProtocols = protocolService.findAllByViolatorsId(violatorIds);

        AdmCase toAdmCase = admCaseService.createCourtCopyAdmCase(fromAdmCase, claimId);
        AdmCase separatedCase = separationService.separateProtocols(fromAdmCase, toAdmCase, separatedProtocols);

        eventDataService.setCourtDTOForSeparation(courtEventHolder.getCurrentInstance(), separatedCase);

        return separatedCase;
    }

    private List<Violator> mergeAdmCases(AdmCase fromAdmCase, AdmCase toAdmCase) {

        List<Protocol> fromAdmCaseProtocols = protocolService.findAllProtocolsInAdmCase(fromAdmCase.getId());

        Map<Violator, Violator> violatorOldToNewMap = new HashMap<>();
        Map<Victim, Victim> victimOldToNewMap = new HashMap<>();

        List<Violator> violators = violatorService.findByAdmCaseId(fromAdmCase.getId());
        for (Violator violator : violators) {
            Violator newViolator = violatorService.mergeTo(violator, toAdmCase);
            violatorOldToNewMap.put(violator, newViolator);
        }

        List<Victim> victims = victimService.findByAdmCaseId(fromAdmCase.getId());
        for (Victim victim : victims) {
            Victim newVictim = victimService.mergeTo(victim, toAdmCase);
            victimOldToNewMap.put(victim, newVictim);
        }

        List<Participant> participants = participantService.findAllByAdmCaseId(fromAdmCase.getId());
        for (Participant participant : participants) {
            participantService.mergeTo(participant, toAdmCase);
        }

        List<Damage> damages = damageService.findByAdmCaseId(fromAdmCase.getId());
        for (Damage damage : damages) {
            damageMainService.mergeTo(
                    damage,
                    violatorOldToNewMap.get(violatorService.getById(damage.getViolatorId())),
                    victimOldToNewMap.get(Optional.ofNullable(damage.getVictimId()).map(victimService::findById).orElse(null))
            );
        }
        damageRepository.deleteAll(damages);

        evidenceRepository.mergeAllTo(fromAdmCase.getId(), toAdmCase.getId());
        documentRepository.mergeAllTo(fromAdmCase.getId(), toAdmCase.getId());

        makeMergedTo(fromAdmCase, toAdmCase);
        separationService.recalculateMainProtocols(toAdmCase);

        registerMovement(fromAdmCase, toAdmCase, fromAdmCaseProtocols);

        return violatorOldToNewMap.values().stream().collect(Collectors.toList());
    }

    private void registerMovement(AdmCase fromAdmCase, AdmCase toAdmCase, List<Protocol> fromAdmCaseProtocols) {
        admCaseMergeSeparationRegistrationService.registerAdmCaseEvent(
                AdmCaseRegistrationType.MERGE,
                fromAdmCase,
                toAdmCase,
                fromAdmCaseProtocols);
    }

    private void validateOrganMerge(User user, AdmCase fromAdmCase, AdmCase toAdmCase) {

        List<ArticlePart> fromCaseArticleParts = protocolService.findMainArticlePartsByAdmCase(fromAdmCase);
        if (fromCaseArticleParts.isEmpty()) {
            throw new AdmCaseIsEmptyException();
        }

        List<ArticlePart> toCaseArticleParts = protocolService.findMainArticlePartsByAdmCase(toAdmCase);
        if (toCaseArticleParts.isEmpty()) {
            throw new AdmCaseIsEmptyException();
        }

        List<ArticlePart> allArticleParts = new ArrayList<>();
        allArticleParts.addAll(fromCaseArticleParts);
        allArticleParts.addAll(toCaseArticleParts);

        if (!calculatingService.isConsideredUserForAll(user, allArticleParts)) {
            throw new NotConsiderOfArticlePartException();
        }
    }


    private void makeMergedTo(AdmCase admCase, AdmCase toAdmCase) {
        admCase.setMergedToAdmCaseId(toAdmCase.getId());
        admStatusService.setStatus(admCase, MERGED);
        admCaseRepository.save(admCase);
    }
}