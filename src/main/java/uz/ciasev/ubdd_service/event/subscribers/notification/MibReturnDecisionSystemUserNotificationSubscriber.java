package uz.ciasev.ubdd_service.event.subscribers.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.notification.sms.SystemUserNotificationBroadcastRequestDTO;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.event.subscribers.MibReturnDecisionSubscriber;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;
import uz.ciasev.ubdd_service.service.notification.system.SystemUserNotificationMessageService;
import uz.ciasev.ubdd_service.service.notification.system.SystemUserNotificationService;

@Service
@RequiredArgsConstructor
public class MibReturnDecisionSystemUserNotificationSubscriber extends MibReturnDecisionSubscriber {

    private final SystemUserNotificationService systemUserNotificationService;
    private final SystemUserNotificationMessageService systemUserNotificationMessageService;
    private final NotificationTypeAlias notificationTypeAlias = NotificationTypeAlias.MIB_RETURN_DECISION;

    @Override
    public void apply(Decision decision) {
        if (decision.getIsCourt()) return;

        String messageText = systemUserNotificationMessageService.buildText(notificationTypeAlias, decision);
        SystemUserNotificationBroadcastRequestDTO notificationRequest = new SystemUserNotificationBroadcastRequestDTO(decision.getResolution().getAdmCase(), decision, notificationTypeAlias, messageText);

        systemUserNotificationService.sendBroadcast(notificationRequest);
    }
}
