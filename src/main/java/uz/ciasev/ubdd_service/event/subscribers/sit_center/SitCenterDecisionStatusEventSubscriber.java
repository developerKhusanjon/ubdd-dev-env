package uz.ciasev.ubdd_service.event.subscribers.sit_center;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.event.subscribers.DecisionStatusChangeSubscriber;

@Service
@RequiredArgsConstructor
public class SitCenterDecisionStatusEventSubscriber extends DecisionStatusChangeSubscriber {

    private final uz.ciasev.ubdd_service.service.sit_center.SitCenterWebhookCreateService service;

    @Override
    public void apply(Decision decision) {
        service.createWebhooks(decision);
    }
}
