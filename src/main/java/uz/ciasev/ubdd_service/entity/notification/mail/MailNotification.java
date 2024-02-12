package uz.ciasev.ubdd_service.entity.notification.mail;

import lombok.*;
import org.hibernate.annotations.Immutable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.mail.MailDeliveryStatus;
import uz.ciasev.ubdd_service.entity.dict.mib.MibNotificationTypeAlias;
import uz.ciasev.ubdd_service.entity.notification.DecisionNotification;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.settings.OrganInfo;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.utils.converter.NotificationTypeToAliasConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Immutable
@Entity
@Table(name = "mail_notification")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MailNotification implements AdmEntity, DecisionNotification {

    @Getter
    @Transient
    private final EntityNameAlias entityNameAlias = EntityNameAlias.MAIL_NOTIFICATION;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mail_notification_id_seq")
    @SequenceGenerator(name = "mail_notification_id_seq", sequenceName = "mail_notification_id_seq", allocationSize = 1)
    private Long id;

    @Getter
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @Getter
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "violator_id")
    private Violator violator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decision_id")
    private Decision decision;

//    @Getter
//    private String notificationType;

    @Getter
    @Convert(converter = NotificationTypeToAliasConverter.class)
    @Column(name = "notification_type_id")
    private NotificationTypeAlias notificationTypeAlias;

    private boolean isActive;

    @Getter
    private String fio;

    @Getter
    private String address;

    @Getter
    private Long areaId;

    @Getter
    private Long regionId;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organ_info_id")
    private OrganInfo organInfo;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organ_id")
    private Organ organ;

    @Getter
    private String messageNumber;


    // Поля заполняемые сервисом отпправки почты

    @Getter
    @Column(name = "send_date", insertable = false, updatable = false)
    private LocalDateTime sendTime;

    @Getter
    @Column(name = "perform_date", insertable = false, updatable = false)
    private LocalDate receiveDate;

    @Getter
    @Column(insertable = false, updatable = false)
    private boolean sendStatus;

    @Getter
    @Column(insertable = false, updatable = false)
    private boolean receiveStatus;

    @Getter
    @Column(insertable = false, updatable = false)
    private String mailStatusDescription;

    @Getter
    @Column(name = "delivery_status_id", insertable = false, updatable = false)
    private Long deliveryStatusId;

    @Getter
    @Column(insertable = false, updatable = false)
    private LocalDateTime changeStatusTime;



    // JPA AND CRITERIA FIELDS ONLY

    @Column(name = "decision_id", insertable=false, updatable=false)
    private Long decisionId;

    @Column(name = "violator_id", insertable=false, updatable=false)
    private Long violatorId;

    @Column(name = "organ_info_id", insertable=false, updatable=false)
    private Long organInfoId;

    @Column(name = "organ_id", insertable=false, updatable=false)
    private Long organId;

    @Column(name = "notification_type_id", insertable = false, updatable = false)
    private Long notificationTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_status_id", insertable = false, updatable = false)
    private MailDeliveryStatus deliveryStatus;

    public MailNotification(MailNotificationRequest request) {
        this.userId = request.getUserId();
        this.violator = request.getViolator();
        this.decision = request.getDecision();
        this.notificationTypeAlias = request.getNotificationType();
        this.areaId = request.getAreaId();
        this.regionId = request.getRegionId();
        this.fio = request.getFio();
        this.address = request.getAddress();
        this.organInfo = request.getOrganInfo();
        this.organ = request.getOrgan();
        this.messageNumber = request.getMessageNumber();
        this.isActive = true;
    }

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

    public String getNotificationType() {
        if (notificationTypeAlias == null) return null;
        return notificationTypeAlias.name();
    }
}

