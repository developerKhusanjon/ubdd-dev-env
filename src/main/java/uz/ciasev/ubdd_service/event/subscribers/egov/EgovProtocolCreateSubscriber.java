package uz.ciasev.ubdd_service.event.subscribers.egov;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.event.subscribers.ProtocolCreateSubscriber;
import uz.ciasev.ubdd_service.service.webhook.egov.EgovWebhookCreateService;


@Service
@RequiredArgsConstructor
public class EgovProtocolCreateSubscriber extends ProtocolCreateSubscriber {
    private final EgovWebhookCreateService service;

    @Override
    public void apply(Protocol protocol) {
        service.createWebhook(protocol, AdmEventType.PROTOCOL_CREATE);
    }

}
