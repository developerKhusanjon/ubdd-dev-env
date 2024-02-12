package uz.ciasev.ubdd_service.event.subscribers.webhook;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.event.subscribers.DecisionStatusChangeSubscriber;
import uz.ciasev.ubdd_service.service.publicapi.eventdata.PublicApiWebhookEventPopulationService;

@Profile("!publicapi")
@Service
@RequiredArgsConstructor
public class PublicApiWebhookDecisionStatusEventSubscriber extends DecisionStatusChangeSubscriber {

    private final PublicApiWebhookEventPopulationService publicApiWebhookEventPopulationService;

    @Override
    public void apply(Decision decision) {
        publicApiWebhookEventPopulationService.addDecisionStatusEvent(decision);
    }
}
