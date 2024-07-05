package uz.ciasev.ubdd_service.event.subscribers.egov;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.event.subscribers.OrganResolutionCreateSubscriber;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedResolutionDTO;
import uz.ciasev.ubdd_service.service.webhook.egov.EgovWebhookCreateService;


@Service
@RequiredArgsConstructor
public class EgovResolutionCreateEventSubscriber extends OrganResolutionCreateSubscriber {

    private final EgovWebhookCreateService service;

    @Override
    public void apply(CreatedResolutionDTO createdResolution) {
        service.createWebhooks(createdResolution.getResolution().getAdmCase(), AdmEventType.ORGAN_RESOLUTION_CREATE);
    }
}
