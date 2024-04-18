package uz.ciasev.ubdd_service.event.subscribers.webhook;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.event.subscribers.OrganResolutionCreateSubscriber;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedResolutionDTO;
import uz.ciasev.ubdd_service.service.publicapi.eventdata.PublicApiWebhookEventPopulationService;

@Profile("!publicapi")
@Service
@RequiredArgsConstructor
public class PublicApiWebhookDecisionMadeAdmEventSubscriber extends OrganResolutionCreateSubscriber {

    private final PublicApiWebhookEventPopulationService publicApiWebhookEventPopulationService;

    @Override
    public void apply(CreatedResolutionDTO createdResolution) {
        publicApiWebhookEventPopulationService.addDecisionsMadeEvent(createdResolution);
    }
}
