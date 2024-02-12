package uz.ciasev.ubdd_service.service.notification.system;

import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;

public interface SystemUserNotificationMessageService {

    String buildText(NotificationTypeAlias notificationTypeAlias, AdmCase admCase);

    String buildText(NotificationTypeAlias type, Decision decision);
}
