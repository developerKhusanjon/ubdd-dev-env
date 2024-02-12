package uz.ciasev.ubdd_service.service.notification.mail;

import uz.ciasev.ubdd_service.dto.internal.response.notification.MailNotificationListDTO;
import uz.ciasev.ubdd_service.entity.notification.mail.MailNotificationListProjection;

public interface MailNotificationDTOService {

    MailNotificationListDTO convertToListDTO(MailNotificationListProjection mailNotificationListProjection);
}
