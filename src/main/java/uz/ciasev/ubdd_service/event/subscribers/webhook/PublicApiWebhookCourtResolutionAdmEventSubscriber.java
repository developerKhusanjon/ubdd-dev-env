package uz.ciasev.ubdd_service.event.subscribers.webhook;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.event.subscribers.CourtResolutionSubscriber;
import uz.ciasev.ubdd_service.repository.resolution.decision.DecisionRepository;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedResolutionDTO;
import uz.ciasev.ubdd_service.service.publicapi.eventdata.PublicApiWebhookEventPopulationService;

@Profile("!publicapi")
@Service
@RequiredArgsConstructor
public class PublicApiWebhookCourtResolutionAdmEventSubscriber extends CourtResolutionSubscriber {

    private final PublicApiWebhookEventPopulationService publicApiWebhookEventPopulationService;
    private final DecisionRepository decisionRepository;


    @Override
    public void apply(CreatedResolutionDTO createdResolution) {
//        publicApiWebhookEventPopulationService.addDecisionsMadeEvent(resolution.getAdmCase(), () -> decisionRepository.findByResolutionId(resolution.getId()));
        publicApiWebhookEventPopulationService.addDecisionsMadeEvent(createdResolution);
    }
}
