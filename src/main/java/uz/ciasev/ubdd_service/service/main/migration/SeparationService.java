package uz.ciasev.ubdd_service.service.main.migration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.history.AdmCaseRegistrationType;
import uz.ciasev.ubdd_service.entity.damage.Damage;
import uz.ciasev.ubdd_service.entity.damage.DamageDetail;
import uz.ciasev.ubdd_service.entity.participant.Participant;
import uz.ciasev.ubdd_service.entity.participant.ParticipantDetail;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.victim.Victim;
import uz.ciasev.ubdd_service.entity.victim.VictimDetail;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;
import uz.ciasev.ubdd_service.repository.protocol.ProtocolRepository;
import uz.ciasev.ubdd_service.repository.damage.DamageDetailRepository;
import uz.ciasev.ubdd_service.repository.participant.ParticipantDetailRepository;
import uz.ciasev.ubdd_service.repository.resolution.compensation.CompensationRepository;
import uz.ciasev.ubdd_service.repository.resolution.decision.DecisionRepository;
import uz.ciasev.ubdd_service.repository.victim.VictimDetailRepository;
import uz.ciasev.ubdd_service.repository.violator.ViolatorDetailRepository;
import uz.ciasev.ubdd_service.service.history.HistoryService;
import uz.ciasev.ubdd_service.service.damage.DamageMainService;
import uz.ciasev.ubdd_service.service.damage.DamageService;
import uz.ciasev.ubdd_service.service.dict.VictimTypeDictionaryService;
import uz.ciasev.ubdd_service.service.participant.ParticipantService;
import uz.ciasev.ubdd_service.service.victim.VictimService;
import uz.ciasev.ubdd_service.service.violator.ViolatorService;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SeparationService {

    private final DamageMainService damageMainService;
    private final ViolatorService violatorService;
    private final VictimService victimService;
    private final VictimTypeDictionaryService victimTypeService;
    private final ParticipantService participantService;
    private final DamageService damageService;
    private final ViolatorDetailRepository violatorDetailRepository;
    private final VictimDetailRepository victimDetailRepository;
    private final DamageDetailRepository damageDetailRepository;
    private final ParticipantDetailRepository participantDetailRepository;
    private final ProtocolRepository protocolRepository;
    private final CompensationRepository compensationRepository;
    private final DecisionRepository decisionRepository;
    private final HistoryService admCaseMergeSeparationRegistrationService;

    @Transactional
    public void recalculateMainProtocols(AdmCase admCase) {
        protocolRepository.setMainToFalseForAllByAdmCaseId(admCase.getId());
        protocolRepository.recalculateMainByAdmCaseId(admCase.getId());
    }


    @Transactional
    public void resetViolatorMainProtocols(Protocol protocol) {
        protocolRepository.setMainToFalseForAllByViolatorId(protocol.getViolatorDetail().getViolator().getId());
        protocolRepository.makeProtocolMain(protocol.getId());
    }

    @Transactional
    public AdmCase separateProtocols(AdmCase fromAdmCase, AdmCase toAdmCase, List<Protocol> separatedProtocols) {
        Cache cache = new Cache(toAdmCase);

        List<VictimDetail> victimDetailList = new ArrayList<>();
        List<ParticipantDetail> participantDetailList = new ArrayList<>();
        List<DamageDetail> damageDetailList = new ArrayList<>();


        for (Protocol protocol : separatedProtocols) {
            ViolatorDetail violatorDetail = protocol.getViolatorDetail();

            Violator newViolator = cache.getNew(violatorDetail.getViolator());
            violatorDetail.setViolator(newViolator);
            violatorDetailRepository.saveAndFlush(violatorDetail);

            victimDetailList.addAll(victimDetailRepository.findAllByProtocolId(protocol.getId()));
            participantDetailList.addAll(participantDetailRepository.findAllByProtocolId(protocol.getId()));
            damageDetailList.addAll(damageDetailRepository.findAllByProtocolId(protocol.getId()));
        }

        for (ParticipantDetail participantDetail : participantDetailList) {
            Participant newParticipant = cache.getNew(participantDetail.getParticipant());
            participantDetail.setParticipant(newParticipant);
            participantDetailRepository.saveAndFlush(participantDetail);
        }

        for (VictimDetail victimDetail : victimDetailList) {
            Victim newVictim = cache.getNew(victimDetail.getVictim());
            victimDetail.setVictim(newVictim);
            victimDetailRepository.saveAndFlush(victimDetail);
        }

        Map<Long, List<DamageDetail>> damageDamageDetailMap = damageDetailList.stream()
                .collect(Collectors.groupingBy(DamageDetail::getDamageId));

        for (Map.Entry<Long, List<DamageDetail>> entry : damageDamageDetailMap.entrySet()) {
            Damage oldDamage = damageService.findById(entry.getKey());
            List<DamageDetail> damageDetails = entry.getValue();

            Damage newDamage = cache.getNew(oldDamage);
            damageDetailRepository.saveAll(damageDetails.stream().peek(dd -> dd.setDamage(newDamage)).collect(Collectors.toList()));
            damageDetailRepository.flush();

            damageMainService.recalculateDamageTotalAmount(oldDamage);
            damageMainService.recalculateDamageTotalAmount(newDamage);
        }

        cleanActorByCase(fromAdmCase, cache);
        recalculateMainProtocols(fromAdmCase);
        recalculateMainProtocols(toAdmCase);

        registerSeparation(fromAdmCase, toAdmCase, separatedProtocols);

        return toAdmCase;
    }

    private void registerSeparation(AdmCase fromAdmCase, AdmCase toAdmCase, List<Protocol> separatedProtocols) {
        admCaseMergeSeparationRegistrationService.registerAdmCaseEvent(
                AdmCaseRegistrationType.SEPARATION,
                fromAdmCase,
                toAdmCase,
                separatedProtocols);
    }

    private void cleanActorByCase(AdmCase admCase, Cache cache) {
        for (Victim victim : cache.victimOldToNewMap.keySet()) {
            if (!victimDetailRepository.existsByVictimId(victim.getId())) {
                compensationRepository.changVictimId(victim.getId(), cache.getCached(victim).getId());
                victimService.delete(victim);
            }
        }

        for (Participant participant : cache.participantOldToNewMap.keySet()) {
            if (!participantDetailRepository.existsByParticipantId(participant.getId())) {
                participantService.delete(participant);
            }
        }

        for (Violator violator : cache.violatorOldToNewMap.keySet()) {
            if (!violatorDetailRepository.existsByViolatorId(violator.getId())) {
//                compensationRepository.changViolatorId(violator.getId(), cache.getCached(violator).getId());
                decisionRepository.changViolatorId(violator.getId(), cache.getCached(violator).getId());
                violatorService.delete(violator);
            }
        }
    }


    private class Cache {
        private AdmCase newAdmCase;
        private Map<Violator, Violator> violatorOldToNewMap;
        Map<Participant, Participant> participantOldToNewMap;
        Map<Victim, Victim> victimOldToNewMap;
        Map<Damage, Damage> damageOldToNewMap;

        Cache(AdmCase newAdmCase) {
            this.newAdmCase = newAdmCase;
            this.violatorOldToNewMap = new HashMap<>();
            this.participantOldToNewMap = new HashMap<>();
            this.victimOldToNewMap = new HashMap<>();
            this.damageOldToNewMap = new HashMap<>();
        }

        Violator getCached(Violator oldViolator) {
            return violatorOldToNewMap.get(oldViolator);
        }

        Violator getNew(Violator oldViolator) {
            Violator newViolator = violatorOldToNewMap.get(oldViolator);

            if (Objects.isNull(newViolator)) {
                newViolator = violatorService.getOrSave(oldViolator
                        .toBuilder()
                        .admCase(this.newAdmCase)
                        .separatedFromViolatorId(oldViolator.getId())
                        .build());
                violatorOldToNewMap.put(oldViolator, newViolator);
            }

            return newViolator;
        }

        Victim getCached(Victim oldVictim) {
            return victimOldToNewMap.get(oldVictim);
        }

        Victim getNew(Victim oldVictim) {
            Victim newVictim = victimOldToNewMap.get(oldVictim);

            if (Objects.isNull(newVictim)) {
                newVictim = victimService.getOrSave(oldVictim
                        .toBuilder()
                        .admCase(this.newAdmCase)
                        .build());
                victimOldToNewMap.put(oldVictim, newVictim);
            }

            return newVictim;
        }

        Participant getNew(Participant oldParticipant) {
            Participant newParticipant = participantOldToNewMap.get(oldParticipant);

            if (Objects.isNull(newParticipant)) {
                newParticipant = participantService.getOrSave(oldParticipant
                        .toBuilder()
                        .admCase(this.newAdmCase)
                        .build());
                participantOldToNewMap.put(oldParticipant, newParticipant);
            }

            return newParticipant;
        }

        Damage getNew(Damage oldDamage) {
            Damage newDamage = damageOldToNewMap.get(oldDamage);

            if (Objects.isNull(newDamage)) {
                Violator newViolator = violatorOldToNewMap.get(violatorService.getById(oldDamage.getViolatorId()));
                Victim newVictim = null;
                if (Objects.nonNull(oldDamage.getVictimId())) {
                    newVictim = victimOldToNewMap.get(victimService.findById(oldDamage.getVictimId()));
                }

                newDamage = damageService.getOrCreate(newViolator, newVictim, oldDamage.getVictimType());
                damageOldToNewMap.put(oldDamage, newDamage);
            }

            return newDamage;
        }
    }
}