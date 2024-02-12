package uz.ciasev.ubdd_service.service.vehicle.sending.castoms;

import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

@Service
public class SendDecisionToCustomsServiceImpl implements SendDecisionToCustomsService {
    @Override
    public void accept(String customsEventId, Decision decision) {

    }
}
