package uz.ciasev.ubdd_service.event.subscribers.replica;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.event.subscribers.OrganResolutionCreateSubscriber;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedResolutionDTO;
import uz.ciasev.ubdd_service.service.webhook.replica.ReplicaWebhookCreateService;


@Service
@RequiredArgsConstructor
public class ReplicaResolutionCreateEventSubscriber extends OrganResolutionCreateSubscriber {

    private final ReplicaWebhookCreateService service;

    @Override
    public void apply(CreatedResolutionDTO createdResolution) {
        service.createWebhooks(createdResolution.getResolution().getAdmCase());
    }
}
