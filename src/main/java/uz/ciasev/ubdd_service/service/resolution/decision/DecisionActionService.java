package uz.ciasev.ubdd_service.service.resolution.decision;

import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;


public interface DecisionActionService {

    @Transactional
    void calculateAndSetExecutedDate(Long decisionId);

    void saveStatus(Decision decision, AdmStatusAlias statusAlias);

    void setMibPreSendHandled(Decision decision);
}
