package uz.ciasev.ubdd_service.event.subscribers.replica;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.event.subscribers.CourtResolutionSubscriber;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedResolutionDTO;
import uz.ciasev.ubdd_service.service.replica.ReplicaWebhookCreateService;


@Service
@RequiredArgsConstructor
public class ReplicaCourtResolutionCreateEventSubscriber extends CourtResolutionSubscriber {

    private final ReplicaWebhookCreateService service;

    @Override
    public void apply(CreatedResolutionDTO resolution) {
        service.createWebhooks(resolution.getResolution().getAdmCase());
    }
}
