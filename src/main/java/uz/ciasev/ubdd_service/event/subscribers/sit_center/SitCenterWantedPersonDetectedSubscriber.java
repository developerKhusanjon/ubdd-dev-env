package uz.ciasev.ubdd_service.event.subscribers.sit_center;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.wanted.WantedProtocol;
import uz.ciasev.ubdd_service.event.subscribers.WantedPersonDetectedSubscriber;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SitCenterWantedPersonDetectedSubscriber extends WantedPersonDetectedSubscriber {

    private final uz.ciasev.ubdd_service.service.webhook.sit_center.SitCenterWebhookCreateService service;

    @Override
    public void apply(List<WantedProtocol> wantedList) {
        service.createWebhooks(wantedList);
    }
}
