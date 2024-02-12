package uz.ciasev.ubdd_service.event.subscribers.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.notification.sms.SystemUserNotificationBroadcastRequestDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.event.subscribers.CourtReturnAdmCaseSubscriber;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;
import uz.ciasev.ubdd_service.service.notification.system.SystemUserNotificationMessageService;
import uz.ciasev.ubdd_service.service.notification.system.SystemUserNotificationService;

@Service
@RequiredArgsConstructor
public class CourtReturnAdmCaseSystemUserNotificationSubscriber extends CourtReturnAdmCaseSubscriber {

    private final SystemUserNotificationService systemUserNotificationService;
    private final SystemUserNotificationMessageService systemUserNotificationMessageService;
    private final NotificationTypeAlias notificationTypeAlias = NotificationTypeAlias.COURT_RETURN_ADM_CASE;

    @Override
    public void apply(AdmCase admCase) {
        String messageText = systemUserNotificationMessageService.buildText(notificationTypeAlias, admCase);
        SystemUserNotificationBroadcastRequestDTO notificationRequest = new SystemUserNotificationBroadcastRequestDTO(admCase, notificationTypeAlias, messageText);

        systemUserNotificationService.sendBroadcast(notificationRequest);
    }
}
