package uz.ciasev.ubdd_service.service.vehicle.sending.vai;

import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddTexPassData;

@Service
public class MoveDecisionToVaiService {

    public void accept(Decision decision, UbddTexPassData texPass) {}
}
