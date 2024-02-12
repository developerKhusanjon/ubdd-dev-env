package uz.ciasev.ubdd_service.entity.notification.sms;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.NotificationType;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.mib.MibNotificationTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.sms.SmsDeliveryStatus;
import uz.ciasev.ubdd_service.entity.notification.DecisionNotification;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;
import uz.ciasev.ubdd_service.utils.converter.NotificationTypeToAliasConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "sms_notification")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SmsNotification implements AdmEntity, DecisionNotification {

    @Getter
    @Transient
    private final EntityNameAlias entityNameAlias = EntityNameAlias.SMS_NOTIFICATION;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sms_notification_id_seq")
    @SequenceGenerator(name = "sms_notification_id_seq", sequenceName = "sms_notification_id_seq", allocationSize = 1)
    private Long id;

    @Setter
    private String idPrefix;

    @Getter
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    private boolean isActive;

    @Getter
    @Convert(converter = NotificationTypeToAliasConverter.class)
    @Column(name = "notification_type_id")
    private NotificationTypeAlias notificationTypeAlias;

    @Getter
    @NotNull(message = ErrorCode.PHONE_NUMBER_REQUIRED)
    private String phoneNumber;

    @Getter
    @NotNull(message = ErrorCode.NOTIFICATION_MESSAGE_REQUIRED)
    private String message;


    private boolean isEncrypt;

    private String encryptMessage;

    @Getter
    @ManyToOne
    @JoinColumn(name = "organ_id")
    private Organ organ;

    @Getter
    @Column(name = "person_id")
    private Long personId;

    @Getter
    @Enumerated(EnumType.STRING)
    private EntityNameAlias entityType;

    @Getter
    private Long entityId;


    // Поля смс нарушителю

    @Getter
    private Long violatorId;

    @Getter
    @Column(name = "adm_case_id")
    private Long admCaseId;


    // Поля смс пользователю

    @Getter
    @Column(name = "user_id")
    private Long userId;


    //  Поля заполняються в сервисе отправки

    @Getter
    @Column(name = "message_id", insertable = false, updatable = false)
    private String messageId;

    @Getter
    @Column(name = "send_date", insertable = false, updatable = false)
    private LocalDateTime sendTime;

    @Getter
    @Column(name = "send_status", updatable = false)
    private boolean sendStatus;

    @Getter
    @ManyToOne
    @JoinColumn(name = "delivery_status_id", insertable = false, updatable = false)
    private SmsDeliveryStatus deliveryStatus;

    @Getter
    @Column(name = "receive_date", insertable = false, updatable = false)
    private LocalDateTime receiveTime;

    @Getter
    @Column(name = "receive_status", updatable = false)
    private boolean receiveStatus;

    @Getter
    @Column(name = "reason_failure", insertable = false, updatable = false)
    private String reasonFailure;



    // JPA AND CRITERIA ONLY FIELDS

    @Column(name = "delivery_status_id", insertable = false, updatable = false)
    private Long deliveryStatusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", insertable = false, updatable = false)
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adm_case_id", insertable = false, updatable = false)
    private AdmCase admCase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_type_id", insertable = false, updatable = false)
    private NotificationType notificationType;

    @Column(name = "notification_type_id", insertable = false, updatable = false)
    private Long notificationTypeId;

    @Builder
    private SmsNotification(NotificationTypeAlias notificationTypeAlias, String phoneNumber, String message, boolean isEncrypt, String encryptMessage, Organ organ, Long personId, AdmEntity entity, Long violatorId, Long admCaseId, Long userId) {
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.isEncrypt = isEncrypt;
        this.encryptMessage = encryptMessage;

        this.organ = organ;
        this.personId = personId;
        this.entityType = entity.getEntityNameAlias();
        this.entityId = entity.getId();
        this.violatorId = violatorId;
        this.admCaseId = admCaseId;
        this.userId = userId;
        this.notificationTypeAlias = notificationTypeAlias;


        this.isActive = true;
        this.sendStatus = false;
        this.receiveStatus = false;
    }

    public Long getDeliveryStatusId() {
        if (deliveryStatus == null) return null;
        return deliveryStatus.getId();
    }

    @Override
    public MibNotificationTypeAlias getChannel() {
        return MibNotificationTypeAlias.SMS;
    }

    @Override
    public String getText() {
        return message;
    }

    @Override
    public String getNumber() {
        return phoneNumber;
    }

    @Override
    public LocalDate getSendDate() {
        if (sendTime == null) return null;
        return sendTime.toLocalDate();
    }

    @Override
    public LocalDate getReceiveDate() {
        if (receiveTime == null) return null;
        return receiveTime.toLocalDate();
    }
}
