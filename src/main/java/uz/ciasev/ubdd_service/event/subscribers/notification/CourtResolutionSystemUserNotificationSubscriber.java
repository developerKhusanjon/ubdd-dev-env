package uz.ciasev.ubdd_service.event.subscribers.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.notification.sms.SystemUserNotificationBroadcastRequestDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.event.subscribers.CourtResolutionSubscriber;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedResolutionDTO;
import uz.ciasev.ubdd_service.service.notification.system.SystemUserNotificationMessageService;
import uz.ciasev.ubdd_service.service.notification.system.SystemUserNotificationService;

@Service
@RequiredArgsConstructor
public class CourtResolutionSystemUserNotificationSubscriber extends CourtResolutionSubscriber {

    private final SystemUserNotificationService systemUserNotificationService;
    private final SystemUserNotificationMessageService systemUserNotificationMessageService;
    private final NotificationTypeAlias notificationTypeAlias = NotificationTypeAlias.COURT_RESOLUTION_CREATE;

    @Override
    public void apply(CreatedResolutionDTO resolution) {

        AdmCase admCase = resolution.getResolution().getAdmCase();

        String messageText = systemUserNotificationMessageService.buildText(notificationTypeAlias, admCase);
        SystemUserNotificationBroadcastRequestDTO notificationRequest = new SystemUserNotificationBroadcastRequestDTO(admCase, notificationTypeAlias, messageText);

        systemUserNotificationService.sendBroadcast(notificationRequest);
    }
}
