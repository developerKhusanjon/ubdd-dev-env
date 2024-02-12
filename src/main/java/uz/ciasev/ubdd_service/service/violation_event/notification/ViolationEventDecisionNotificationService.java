package uz.ciasev.ubdd_service.service.violation_event.notification;

import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddTexPassData;

public interface ViolationEventDecisionNotificationService {
    SentNotificationChecklist accept(Decision decision, UbddTexPassData savedTexPass);
}
