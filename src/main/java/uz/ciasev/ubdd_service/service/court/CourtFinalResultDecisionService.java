package uz.ciasev.ubdd_service.service.court;

import uz.ciasev.ubdd_service.entity.court.CourtFinalResultDecision;
import uz.ciasev.ubdd_service.entity.dict.court.InstancesAliases;

import java.util.List;

public interface CourtFinalResultDecisionService {

    CourtFinalResultDecision save(CourtFinalResultDecision finalResult);

    List<InstancesAliases> findByCaseId(Long id);
}
