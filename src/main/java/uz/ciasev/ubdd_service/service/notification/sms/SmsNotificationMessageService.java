package uz.ciasev.ubdd_service.service.notification.sms;

import uz.ciasev.ubdd_service.dto.internal.request.notification.sms.*;

public interface SmsNotificationMessageService {

    String buildDecisionMessage(NotificationSmsTextDecisionRequestDTO requestDTO);
    String buildCourtMessage(NotificationSmsTextCourtRequestDTO requestDTO);
    String buildMibMessage(NotificationSmsTextMibRequestDTO requestDTO);
    String buildProtocolMessage(NotificationSmsTextProtocolRequestDTO requestDTO);
    String buildDigitalSignaturePasswordMessage(NotificationSmsTextDigitalSignaturePasswordRequestDTO requestDTO);
}
