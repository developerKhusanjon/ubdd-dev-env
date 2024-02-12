package uz.ciasev.ubdd_service.event.subscribers.sms;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.dto.internal.request.notification.sms.SmsRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.event.subscribers.ProtocolCreateSubscriber;
import uz.ciasev.ubdd_service.service.notification.sms.SmsNotificationDTOService;
import uz.ciasev.ubdd_service.service.notification.sms.SmsNotificationService;

import java.util.Optional;

//@Service
@RequiredArgsConstructor
public class ProtocolSmsAdmEventSubscriber extends ProtocolCreateSubscriber {

    private final SmsNotificationService smsNotificationService;
    private final SmsNotificationDTOService smsNotificationDTOService;

    @Override
    public void apply(Protocol protocol) {
        Organ organ = protocol.getOrgan();
        if (!Optional.ofNullable(organ)
                .map(Organ::getSmsNotification)
                .orElse(false)) {
            return;
        }
        SmsRequestDTO notification = smsNotificationDTOService.makeProtocolDTO(protocol, organ);
        smsNotificationService.sendSms(notification);
    }
}
