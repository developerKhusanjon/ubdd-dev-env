package uz.ciasev.ubdd_service.entity.signature;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "digital_signature_certificate")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DigitalSignatureCertificate implements Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_user_id", updatable = false)
    private User createdUser;

    @Getter
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @Getter
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime editedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @Getter
    @Column(updatable = false)
    private String serial;

    @Getter
    @Column(updatable = false)
    private String encryptedPassword;

    @Getter
    @Column(updatable = false)
    private LocalDateTime issuedOn;

    @Getter
    @Column(updatable = false)
    private LocalDateTime expiresOn;

    @Getter
    @Column(updatable = false)
    private String file;

    @Getter
    private boolean isActive;

    @Getter
    private String activityChangeReasonDesc;

    // JPA AND CRITERIA FIELDS ONLY
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;


    @Builder
    public DigitalSignatureCertificate(User createdUser, User user, String serial, String encryptedPassword, LocalDateTime issuedOn, LocalDateTime expiresOn, String file) {
        this.createdUser = createdUser;
        this.user = user;
        this.serial = serial;
        this.encryptedPassword = encryptedPassword;
        this.issuedOn = issuedOn;
        this.expiresOn = expiresOn;
        this.file = file;

        this.activityChangeReasonDesc = null;
        this.isActive = true;
    }

    public Long getUserId() {
        if (user == null) return null;
        return user.getId();
    }

    public Long getCreatedUserId() {
        if (createdUser == null) return null;
        return createdUser.getId();
    }

    public void editActivity(boolean isActive, String reason) {
        this.isActive = isActive;
        this.activityChangeReasonDesc = reason;
    }
}
