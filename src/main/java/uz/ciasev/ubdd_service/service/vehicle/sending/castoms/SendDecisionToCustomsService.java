package uz.ciasev.ubdd_service.service.vehicle.sending.castoms;

import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

public interface SendDecisionToCustomsService {
    void accept(String customsEventId, Decision decision);
}
