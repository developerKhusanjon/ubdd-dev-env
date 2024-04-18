package uz.ciasev.ubdd_service.event.subscribers.ibd;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.event.subscribers.OrganResolutionCreateSubscriber;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedResolutionDTO;
import uz.ciasev.ubdd_service.service.webhook.ibd.IBDWebhookCreateService;


@Service
@RequiredArgsConstructor
public class IBDResolutionCreateEventSubscriber extends OrganResolutionCreateSubscriber {
    private final IBDWebhookCreateService service;

    @Override
    public void apply(CreatedResolutionDTO createdResolution) {
        service.createWebhooks(createdResolution.getResolution().getAdmCase().getId(), AdmEventType.ORGAN_RESOLUTION_CREATE);
    }
}
