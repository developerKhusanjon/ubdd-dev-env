package uz.ciasev.ubdd_service.event.subscribers.ombudsman;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.event.subscribers.OrganResolutionCreateSubscriber;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedResolutionDTO;
import uz.ciasev.ubdd_service.service.ombudsman.OmbudsmanWebhookCreateService;

import static uz.ciasev.ubdd_service.event.AdmEventType.ORGAN_RESOLUTION_CREATE;


@Service
@RequiredArgsConstructor
public class OmbudsmanResolutionCreateEventSubscriber extends OrganResolutionCreateSubscriber {
    private final OmbudsmanWebhookCreateService service;

    @Override
    public void apply(CreatedResolutionDTO createdResolution) {
        service.createWebhooks(createdResolution.getResolution().getAdmCase(),ORGAN_RESOLUTION_CREATE);
    }
}
