package uz.ciasev.ubdd_service.service.status;

import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.resolution.evidence_decision.EvidenceDecision;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;


public interface StatusService {

//    Resolution setStatus(Resolution entity, AdmStatusAlias statusAlias);

    @Transactional
    AdmStatus addStatusAndGet(Resolution entity, AdmStatusAlias statusAlias);

    EvidenceDecision setStatus(EvidenceDecision entity, AdmStatusAlias statusAlias);

    void addStatus(AdmEntity entity, AdmStatusAlias statusAlias);

    void cancelStatus(AdmEntity entity, AdmStatusAlias... statusAlias);

    @Transactional
    AdmStatus getStatusOrDefault(AdmEntity entity, AdmStatusAlias defaultValue);

    AdmStatus getStatus(AdmEntity entity);
}
