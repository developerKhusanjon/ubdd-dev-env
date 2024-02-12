package uz.ciasev.ubdd_service.event.subscribers.ombudsman;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.event.subscribers.DecisionStatusChangeSubscriber;
import uz.ciasev.ubdd_service.service.ombudsman.OmbudsmanWebhookCreateService;

import static uz.ciasev.ubdd_service.event.AdmEventType.DECISION_STATUS_CHANGE;

@Service
@RequiredArgsConstructor
public class OmbudsmanDecisionStatusEventSubscriber extends DecisionStatusChangeSubscriber {

    private final OmbudsmanWebhookCreateService service;

    @Override
    public void apply(Decision decision) {
        service.createWebhooks(decision, DECISION_STATUS_CHANGE);
    }
}
