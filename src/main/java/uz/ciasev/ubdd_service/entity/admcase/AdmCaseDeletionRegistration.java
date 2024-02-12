package uz.ciasev.ubdd_service.entity.admcase;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseDeletionReason;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "adm_case_deletion_registration")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class AdmCaseDeletionRegistration {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdTime;

    @Getter
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime editedTime;

    @Getter
    @Setter
    private Boolean isActive;

    @Getter
    @ManyToOne
    @JoinColumn(name = "adm_case_id", updatable = false)
    private AdmCase admCase;

    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reason_id", updatable = false)
    private AdmCaseDeletionReason reason;

    @Getter
    @Column(updatable = false)
    private String documentBaseUri;

    @Getter
    @Column(updatable = false)
    private String signature;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recovered_user_id")
    private User recoveredUser;

    @Setter
    @Getter
    private LocalDateTime recoveredTime;


    //  JPA AND CRITERIA FIELD ONLY

    @Column(name = "adm_case_id", updatable = false, insertable = false)
    private Long admCaseId;

    @Column(name = "user_id", updatable = false, insertable = false)
    private Long userId;

    @Column(name = "recovered_user_id", updatable = false, insertable = false)
    private Long recoveredUserId;

    @Column(name = "reason_id", updatable = false, insertable = false)
    private Long reasonId;

    @Builder
    public AdmCaseDeletionRegistration(AdmCase admCase, User user, AdmCaseDeletionReason reason, String documentBaseUri, String signature) {
        this.admCase = admCase;
        this.user = user;
        this.reason = reason;
        this.documentBaseUri = documentBaseUri;
        this.signature = signature;
        this.isActive = true;
    }

    public Long getAdmCaseId() {
        if (admCase == null) return null;
        return admCase.getId();
    }

    public Long getUserId() {
        if (user == null) return null;
        return user.getId();
    }

    public Long getRecoveredUserId() {
        if (recoveredUser == null) return null;
        return recoveredUser.getId();
    }

    public Long getReasonId() {
        if (reason == null) return null;
        return reason.getId();
    }

}
