package uz.ciasev.ubdd_service.entity.notification.mail;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Immutable
@Entity
@Table(name = "mail_notification_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class MailNotificationFile {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mail_notification_file_id_seq")
    @SequenceGenerator(name = "mail_notification_file_id_seq", sequenceName = "mail_notification_file_id_seq", allocationSize = 1)
    private Long id;

    @Getter
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mail_notification_id")
    private MailNotification mailNotification;

    @Getter
    @Lob @Basic(fetch=FetchType.LAZY)
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "content")
    private byte[] content;


    // JPA AND CRITERIA FIELDS ONLY

    @Column(name = "mail_notification_id", insertable=false, updatable=false)
    private Long mailNotificationId;

    public MailNotificationFile(MailNotification mailNotification, byte[] content) {
        this.mailNotification = mailNotification;
        this.content = content;
    }

    public Long getMailNotificationId() {
        if (mailNotification == null) return null;
        return mailNotification.getId();
    }
}

