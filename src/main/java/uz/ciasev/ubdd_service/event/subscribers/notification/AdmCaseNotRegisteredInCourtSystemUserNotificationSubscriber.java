package uz.ciasev.ubdd_service.event.subscribers.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.notification.sms.SystemUserNotificationBroadcastRequestDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.court.CourtCaseChancelleryData;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;
import uz.ciasev.ubdd_service.event.subscribers.AdmCaseRegistrationStatusInCourtSubscriber;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.notification.system.SystemUserNotificationMessageService;
import uz.ciasev.ubdd_service.service.notification.system.SystemUserNotificationService;

import static uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias.CANCELLED;

@Service
@RequiredArgsConstructor
public class AdmCaseNotRegisteredInCourtSystemUserNotificationSubscriber extends AdmCaseRegistrationStatusInCourtSubscriber {

    private final SystemUserNotificationService systemUserNotificationService;
    private final SystemUserNotificationMessageService systemUserNotificationMessageService;
    private final AdmCaseService admCaseService;
    private final NotificationTypeAlias notificationTypeAlias = NotificationTypeAlias.NOT_REGISTERED_IN_COURT;

    @Override
    public void apply(CourtCaseChancelleryData courtCaseFields) {

        if (courtCaseFields.getStatus().not(CANCELLED)) {
            return;
        }

        AdmCase admCase = admCaseService.getById(courtCaseFields.getCaseId());

        String messageText = systemUserNotificationMessageService.buildText(notificationTypeAlias, admCase);
        SystemUserNotificationBroadcastRequestDTO notificationRequest = new SystemUserNotificationBroadcastRequestDTO(admCase, notificationTypeAlias, messageText);

        systemUserNotificationService.sendBroadcast(notificationRequest);
    }
}
