package uz.ciasev.ubdd_service.event.subscribers.sms;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.notification.sms.SmsRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.event.subscribers.MibPreSendSubscriber;
import uz.ciasev.ubdd_service.service.notification.sms.SmsNotificationDTOService;
import uz.ciasev.ubdd_service.service.notification.sms.SmsNotificationService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MibSmsAdmEventSubscriber extends MibPreSendSubscriber {

    private final SmsNotificationService smsNotificationService;
    private final SmsNotificationDTOService smsNotificationDTOService;

    @Override
    public void apply(Decision decision) {
        Organ organ = Optional.ofNullable(decision.getResolution())
                .map(Resolution::getOrgan).orElse(null);
        if (!Optional.ofNullable(organ)
                .map(Organ::getSmsNotification)
                .orElse(false)) {
            return;
        }
        SmsRequestDTO notification = smsNotificationDTOService.makeMibDTO(decision, organ);
        smsNotificationService.sendSms(notification);
    }
}
