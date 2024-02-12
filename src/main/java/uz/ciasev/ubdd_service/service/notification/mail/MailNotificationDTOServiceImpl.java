package uz.ciasev.ubdd_service.service.notification.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.notification.MailNotificationListDTO;
import uz.ciasev.ubdd_service.entity.notification.mail.MailNotificationListProjection;

@Service
@RequiredArgsConstructor
public class MailNotificationDTOServiceImpl implements MailNotificationDTOService {

    @Override
    public MailNotificationListDTO convertToListDTO(MailNotificationListProjection mailNotificationListProjection) {
        return new MailNotificationListDTO(mailNotificationListProjection);
    }
}
