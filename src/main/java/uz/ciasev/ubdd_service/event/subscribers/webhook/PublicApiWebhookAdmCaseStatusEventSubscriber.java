package uz.ciasev.ubdd_service.event.subscribers.webhook;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.event.subscribers.AdmCaseStatusChangeSubscriber;
import uz.ciasev.ubdd_service.service.publicapi.eventdata.PublicApiWebhookEventPopulationService;

@Profile("!publicapi")
@Service
@RequiredArgsConstructor
public class PublicApiWebhookAdmCaseStatusEventSubscriber extends AdmCaseStatusChangeSubscriber {

    private final PublicApiWebhookEventPopulationService publicApiWebhookEventPopulationService;

    @Override
    public void apply(AdmCase admCase) {
        publicApiWebhookEventPopulationService.addAdmCaseStatusEvent(admCase);
    }
}
