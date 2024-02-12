package uz.ciasev.ubdd_service.event.subscribers.webhook;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.event.subscribers.CompensationStatusChangeSubscriber;
import uz.ciasev.ubdd_service.service.publicapi.eventdata.PublicApiWebhookEventPopulationService;

@Profile("!publicapi")
@Service
@RequiredArgsConstructor
public class PublicApiWebhookCompensationStatusEventSubscriber extends CompensationStatusChangeSubscriber {

    private final PublicApiWebhookEventPopulationService publicApiWebhookEventPopulationService;

    @Override
    public void apply(Compensation compensation) {
        publicApiWebhookEventPopulationService.addCompensationStatusEvent(compensation);
    }
}
