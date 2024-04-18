package uz.ciasev.ubdd_service.event.subscribers.sit_center;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.event.subscribers.CourtResolutionSubscriber;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedResolutionDTO;


@Service
@RequiredArgsConstructor
public class SitCenterCourtResolutionCreateEventSubscriber extends CourtResolutionSubscriber {

    private final uz.ciasev.ubdd_service.service.webhook.sit_center.SitCenterWebhookCreateService service;

    @Override
    public void apply(CreatedResolutionDTO resolution) {
        service.createWebhooks(resolution.getResolution().getAdmCase());
    }
}
