package uz.ciasev.ubdd_service.entity.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "system_user_notification")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SystemUserNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "system_user_notification_id_seq")
    @SequenceGenerator(name = "system_user_notification_id_seq", sequenceName = "system_user_notification_id_seq", allocationSize = 1)
    private Long id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @Column(updatable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(updatable = false)
    private NotificationTypeAlias notificationType;

    @Column(updatable = false)
    private Long admCaseId;

    @Column(updatable = false)
    private Long decisionId;

    @Column(updatable = false)
    private String text;


    @Builder.Default
    private boolean isRead = false;

    private LocalDateTime readTime;

}
