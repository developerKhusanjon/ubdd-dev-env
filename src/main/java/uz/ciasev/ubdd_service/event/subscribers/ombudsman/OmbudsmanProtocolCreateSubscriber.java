package uz.ciasev.ubdd_service.event.subscribers.ombudsman;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.event.subscribers.ProtocolCreateSubscriber;
import uz.ciasev.ubdd_service.service.ombudsman.OmbudsmanWebhookCreateService;


@Service
@RequiredArgsConstructor
public class OmbudsmanProtocolCreateSubscriber extends ProtocolCreateSubscriber {
    private final OmbudsmanWebhookCreateService service;

    @Override
    public void apply(Protocol protocol) {
        service.createWebhook(protocol, AdmEventType.PROTOCOL_CREATE);
    }
}
