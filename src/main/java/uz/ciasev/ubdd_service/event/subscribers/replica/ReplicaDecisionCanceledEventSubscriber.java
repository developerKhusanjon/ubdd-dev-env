package uz.ciasev.ubdd_service.event.subscribers.replica;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.event.subscribers.DecisionCanceledSubscriber;
import uz.ciasev.ubdd_service.service.replica.ReplicaWebhookCreateService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplicaDecisionCanceledEventSubscriber extends DecisionCanceledSubscriber {

    private final ReplicaWebhookCreateService service;

    @Override
    public void apply(List<Decision> decisions) {
        decisions.stream()
                .findFirst()
                .map(Decision::getResolution)
                .map(Resolution::getAdmCase)
                .ifPresent(service::createWebhooks);
    }
}
