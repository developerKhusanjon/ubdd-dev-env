package uz.ciasev.ubdd_service.service.status;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.resolution.*;
import uz.ciasev.ubdd_service.entity.resolution.evidence_decision.EvidenceDecision;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.entity.status.EntityStatusAccumulator;
import uz.ciasev.ubdd_service.repository.status.EntityStatusAccumulatorRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

    private final AdmStatusDictionaryService admStatusDictionaryService;
    private final EntityStatusAccumulatorRepository decisionAccumulator;

//    @Override
//    @Transactional
//    public Resolution setStatus(Resolution entity, AdmStatusAlias statusAlias) {
//        addStatus(entity, statusAlias);
//        entity.setStatus(getStatus(entity));
//        return entity;
//    }

    @Override
    @Transactional
    public AdmStatus addStatusAndGet(Resolution entity, AdmStatusAlias statusAlias) {
        addStatus(entity, statusAlias);
        return getStatus(entity);
    }

    @Override
    public EvidenceDecision setStatus(EvidenceDecision entity, AdmStatusAlias statusAlias) {
        addStatus(entity, statusAlias);
        entity.setStatus(getStatus(entity));
        return entity;
    }

    @Override
    @Transactional
    public void addStatus(AdmEntity entity, AdmStatusAlias statusAlias) {
        putToAccumulator(entity.getId(), entity.getEntityNameAlias(), statusAlias);
    }

    @Override
    @Transactional
    public void cancelStatus(AdmEntity entity, AdmStatusAlias... statusAlias) {
        removeFromAccumulator(entity.getId(), entity.getEntityNameAlias(), List.of(statusAlias));
    }

    @Override
    @Transactional
    public AdmStatus getStatusOrDefault(AdmEntity entity, AdmStatusAlias defaultValue) {
        if (entity.getId() == null) {
            return admStatusDictionaryService.findByAlias(defaultValue);
        }

        return getFromAccumulator(entity.getId(), entity.getEntityNameAlias())
                .orElseGet(() -> admStatusDictionaryService.findByAlias(defaultValue));
    }

    @Override
    @Transactional
    public AdmStatus getStatus(AdmEntity entity) {
        // TODO: 06.11.2023
        return getStatusOrDefault(entity, AdmStatusAlias.DECISION_MADE);
    }

    private Optional<AdmStatus> getFromAccumulator(Long id, EntityNameAlias type) {
       return decisionAccumulator.findTopByEntityIdAndEntityTypeOrderByAdmStatusRankDesc(id, type)
               .map(EntityStatusAccumulator::getAdmStatus);
    }

    private void putToAccumulator(Long id, EntityNameAlias type, AdmStatusAlias statusAlias) {
        AdmStatus status = admStatusDictionaryService.findByAlias(statusAlias);

        decisionAccumulator.deleteWithSameRank(id, type, statusAlias);
        decisionAccumulator.saveAndFlush(new EntityStatusAccumulator(null, type, id, status));
    }

    private void removeFromAccumulator(Long id, EntityNameAlias type, List<AdmStatusAlias> statusAlias) {
        decisionAccumulator.deleteWithStatusAlias(id, type, statusAlias);
    }
}
