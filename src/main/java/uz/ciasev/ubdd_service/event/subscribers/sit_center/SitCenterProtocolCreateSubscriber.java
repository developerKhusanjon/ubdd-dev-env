package uz.ciasev.ubdd_service.event.subscribers.sit_center;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.event.subscribers.ProtocolCreateSubscriber;


@Service
@RequiredArgsConstructor
public class SitCenterProtocolCreateSubscriber extends ProtocolCreateSubscriber {
    private final uz.ciasev.ubdd_service.service.sit_center.SitCenterWebhookCreateService service;

    @Override
    public void apply(Protocol protocol) {
        service.createWebhook(protocol);
    }
}
