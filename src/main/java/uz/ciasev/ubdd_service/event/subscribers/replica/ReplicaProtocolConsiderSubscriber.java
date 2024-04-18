package uz.ciasev.ubdd_service.event.subscribers.replica;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.event.subscribers.ProtocolConsiderStatusSubscriber;
import uz.ciasev.ubdd_service.service.webhook.replica.ReplicaWebhookCreateService;


@Service
@RequiredArgsConstructor
public class ReplicaProtocolConsiderSubscriber extends ProtocolConsiderStatusSubscriber {
    private final ReplicaWebhookCreateService service;

    @Override
    public void apply(AdmCase admCase) {
        service.createWebhooks(admCase);
    }
}
