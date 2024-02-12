package uz.ciasev.ubdd_service.service.notification.sms;

import uz.ciasev.ubdd_service.dto.internal.request.notification.sms.SmsRequestDTO;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.notification.sms.SmsNotification;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;

import java.util.List;

public interface SmsNotificationService {

    List<SmsNotification> findByEntityAndEvent(AdmEntity entity, NotificationTypeAlias notificationTypeAlias);

    void sendSms(SmsRequestDTO requestDTO);

    void sendSecretSms(SmsRequestDTO requestDTO);

    List<SmsNotification> findByDecision(Long decisionId);

    SmsNotification getById(Long smsNotificationId);
    List<SmsNotification> getByIds(List<Long> smsNotificationId);
}
