package uz.ciasev.ubdd_service.event.subscribers.ibd;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.event.subscribers.CourtResolutionSubscriber;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedResolutionDTO;
import uz.ciasev.ubdd_service.service.webhook.ibd.IBDWebhookCreateService;


@Service
@RequiredArgsConstructor
public class IBDCourtResolutionCreateEventSubscriber extends CourtResolutionSubscriber {
    private final IBDWebhookCreateService service;

    @Override
    public void apply(CreatedResolutionDTO resolution) {
        service.createWebhooks(resolution.getResolution().getAdmCase().getId(), AdmEventType.COURT_RESOLUTION_CREATE);
    }
}
