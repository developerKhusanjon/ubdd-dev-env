package uz.ciasev.ubdd_service.entity.notification.manual;

import lombok.*;
import org.springframework.data.annotation.Immutable;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.dict.NotificationType;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.mib.MibNotificationTypeAlias;
import uz.ciasev.ubdd_service.entity.notification.DecisionNotification;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.utils.converter.NotificationTypeToAliasConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Immutable
@Entity
@Table(name = "manual_notification")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ManualNotification implements AdmEntity, DecisionNotification {

    @Getter
    @Transient
    private final EntityNameAlias entityNameAlias = EntityNameAlias.MANUAL_NOTIFICATION;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "manual_notification_id_seq")
    @SequenceGenerator(name = "manual_notification_id_seq", sequenceName = "manual_notification_id_seq", allocationSize = 1)
    private Long id;

    @Getter
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decision_id")
    private Decision decision;

    @Getter
    @Convert(converter = NotificationTypeToAliasConverter.class)
    @Column(name = "notification_type_id")
    private NotificationTypeAlias notificationTypeAlias;

    @Getter
    private LocalDate sendDate;

    @Getter
    private LocalDate receiveDate;

    @Getter
    private String number;

    @Getter
    private String text;

    @Getter
    private String fileUri;


    // JPA AND CRITERIA ONLY FIELDS

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_type_id", insertable = false, updatable = false)
    private NotificationType notificationType;

    @Column(name = "decision_id", insertable = false, updatable = false)
    private Long decisionId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;


    @Builder
    public ManualNotification(User user, Decision decision, NotificationTypeAlias notificationType, LocalDate sendDate, LocalDate receiveDate, String number, String text, String fileUri) {
        this.createdTime = LocalDateTime.now();

        this.user = user;
        this.decision = decision;
        this.notificationTypeAlias = notificationType;
        this.sendDate = sendDate;
        this.receiveDate = receiveDate;
        this.number = number;
        this.text = text;
        this.fileUri = fileUri;
    }

    public Long getNotificationTypeId() {
        if (notificationTypeAlias == null) return null;
        return notificationTypeAlias.getId();
    }

    public Long getDecisionId() {
        if (decision == null) return null;
        return decision.getId();
    }

    public Long getUserId() {
        if (user == null) return null;
        return user.getId();
    }

    @Override
    public MibNotificationTypeAlias getChannel() {
        return MibNotificationTypeAlias.MANUAL;
    }
}

