package uz.ciasev.ubdd_service.event.subscribers.sit_center;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.event.subscribers.OrganResolutionCreateSubscriber;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedResolutionDTO;
import uz.ciasev.ubdd_service.service.webhook.sit_center.SitCenterWebhookCreateService;


@Service
@RequiredArgsConstructor
public class SitCenterResolutionCreateEventSubscriber extends OrganResolutionCreateSubscriber {

    private final SitCenterWebhookCreateService service;

    @Override
    public void apply(CreatedResolutionDTO createdResolution) {
        service.createWebhooks(createdResolution.getResolution().getAdmCase());
    }
}
