package uz.ciasev.ubdd_service.event.subscribers.ombudsman;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.event.subscribers.CourtResolutionSubscriber;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedResolutionDTO;
import uz.ciasev.ubdd_service.service.ombudsman.OmbudsmanWebhookCreateService;


@Service
@RequiredArgsConstructor
public class OmbudsmanCourtResolutionCreateEventSubscriber extends CourtResolutionSubscriber {
    private final OmbudsmanWebhookCreateService service;

    @Override
    public void apply(CreatedResolutionDTO resolution) {
        service.createWebhooks(resolution.getResolution().getAdmCase(), AdmEventType.COURT_RESOLUTION_CREATE);
    }
}
