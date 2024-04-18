package uz.ciasev.ubdd_service.event.subscribers.ibd;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.event.subscribers.ProtocolCreateSubscriber;
import uz.ciasev.ubdd_service.service.webhook.ibd.IBDWebhookCreateService;


@Service
@RequiredArgsConstructor
public class IBDCenterProtocolCreateSubscriber extends ProtocolCreateSubscriber {
    private final IBDWebhookCreateService service;

    @Override
    public void apply(Protocol protocol) {
        service.createWebhook(protocol, AdmEventType.PROTOCOL_CREATE);
    }
}
