package uz.ciasev.ubdd_service.service.vehicle.sending.mid;

import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddTexPassData;

@Service
public class SendDecisionToMidServiceImpl implements SendDecisionToMidService {
    @Override
    public void accept(Decision decision, UbddTexPassData texPass) {

    }
}
