package uz.ciasev.ubdd_service.event.subscribers.egov;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.event.subscribers.DecisionStatusChangeSubscriber;
import uz.ciasev.ubdd_service.service.webhook.egov.EgovWebhookCreateService;

@Service
@RequiredArgsConstructor
public class EgovDecisionStatusEventSubscriber extends DecisionStatusChangeSubscriber {

    private final EgovWebhookCreateService service;

    @Override
    public void apply(Decision decision) {
        service.createWebhooks(decision, AdmEventType.DECISION_STATUS_CHANGE);
    }
}
