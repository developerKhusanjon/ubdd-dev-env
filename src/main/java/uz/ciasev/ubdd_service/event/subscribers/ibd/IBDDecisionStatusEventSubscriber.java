package uz.ciasev.ubdd_service.event.subscribers.ibd;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.event.subscribers.DecisionStatusChangeSubscriber;
import uz.ciasev.ubdd_service.service.webhook.ibd.IBDWebhookCreateService;

@Service
@RequiredArgsConstructor
public class IBDDecisionStatusEventSubscriber extends DecisionStatusChangeSubscriber {

    private final IBDWebhookCreateService service;

    @Override
    public void apply(Decision decision) {
        service.createWebhooks(decision);
    }
}
