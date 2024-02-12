package uz.ciasev.ubdd_service.entity.notification.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.dict.mib.MibNotificationTypeAlias;
import uz.ciasev.ubdd_service.entity.notification.DecisionNotification;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Deprecated
@Getter
@AllArgsConstructor
public class MailNotificationLiteProjection implements AdmEntity, DecisionNotification {

    private final EntityNameAlias entityNameAlias = EntityNameAlias.MAIL_NOTIFICATION;

    private Long id;
    private LocalDateTime createdTime;
    private Long userId;
    private Long decisionId;
    private Long violatorId;
    private String notificationType;
    private String fio;
    private String address;
    private Long areaId;
    private Long regionId;
    private Long organInfoId;
    private String messageNumber;
    private LocalDateTime sendTime;
    private LocalDate receiveDate;
    private boolean sendStatus;
    private boolean receiveStatus;
    private String mailStatusDescription;
    private Long deliveryStatusId;
    private LocalDateTime changeStatusTime;

    @Override
    public MibNotificationTypeAlias getChannel() {
        return MibNotificationTypeAlias.MAIL;
    }

    @Override
    public String getText() {
        return "Оповещение почтой";
    }

    @Override
    public String getNumber() {
        return messageNumber;
    }

    @Override
    public LocalDate getSendDate() {
        if (sendTime == null) return null;
        return sendTime.toLocalDate();
    }
}
