package uz.ciasev.ubdd_service.event.subscribers.sit_center;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.event.subscribers.DecisionCanceledSubscriber;
import uz.ciasev.ubdd_service.service.webhook.sit_center.SitCenterWebhookCreateService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SitCenterDecisionCanceledEventSubscriber extends DecisionCanceledSubscriber {

    private final SitCenterWebhookCreateService service;

    @Override
    public void apply(List<Decision> decisions) {
        decisions.stream()
                .findFirst()
                .map(Decision::getResolution)
                .map(Resolution::getAdmCase)
                .ifPresent(service::createWebhooks);
    }
}
