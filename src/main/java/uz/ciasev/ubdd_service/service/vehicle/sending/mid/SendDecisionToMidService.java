package uz.ciasev.ubdd_service.service.vehicle.sending.mid;

import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddTexPassData;

public interface SendDecisionToMidService {

    void accept(Decision decision, UbddTexPassData texPass);
}
