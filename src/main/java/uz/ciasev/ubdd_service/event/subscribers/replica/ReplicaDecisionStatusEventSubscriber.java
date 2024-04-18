package uz.ciasev.ubdd_service.event.subscribers.replica;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.event.subscribers.DecisionStatusChangeSubscriber;
import uz.ciasev.ubdd_service.service.webhook.replica.ReplicaWebhookCreateService;

@Service
@RequiredArgsConstructor
public class ReplicaDecisionStatusEventSubscriber extends DecisionStatusChangeSubscriber {

    private final ReplicaWebhookCreateService service;

    @Override
    public void apply(Decision decision) {
        service.createWebhooks(decision);
    }
}
