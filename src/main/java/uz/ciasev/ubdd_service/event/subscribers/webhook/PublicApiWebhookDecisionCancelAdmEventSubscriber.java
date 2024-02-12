package uz.ciasev.ubdd_service.event.subscribers.webhook;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.event.subscribers.DecisionCanceledSubscriber;
import uz.ciasev.ubdd_service.service.publicapi.eventdata.PublicApiWebhookEventPopulationService;

import java.util.List;

@Profile("!publicapi")
@Service
@RequiredArgsConstructor
public class PublicApiWebhookDecisionCancelAdmEventSubscriber extends DecisionCanceledSubscriber {

    private final PublicApiWebhookEventPopulationService publicApiWebhookEventPopulationService;

    @Override
    public void apply(List<Decision> decisions) {
        decisions.forEach(publicApiWebhookEventPopulationService::addDecisionCancelEvent);
    }
}
