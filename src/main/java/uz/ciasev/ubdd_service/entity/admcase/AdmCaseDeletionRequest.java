package uz.ciasev.ubdd_service.entity.admcase;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseDeletionReason;
import uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseDeletionRequestDeclineReason;
import uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseDeletionRequestStatusAlias;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.utils.converter.AdmCaseDeletionRequestStatusIdToAliasConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "adm_case_deletion_request")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class AdmCaseDeletionRequest implements AdmEntity {

    @Getter
    @Transient
    private final EntityNameAlias entityNameAlias = EntityNameAlias.ADM_CASE_DELETION_REQUEST;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @Getter
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime editedTime;

    @Getter
    @Setter
    @Convert(converter = AdmCaseDeletionRequestStatusIdToAliasConverter.class)
    @Column(name = "status_id")
    private AdmCaseDeletionRequestStatusAlias status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adm_case_id", updatable = false)
    private AdmCase admCase;

    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delete_reason_id", updatable = false)
    private AdmCaseDeletionReason deleteReason;

    @Getter
    @Column(updatable = false)
    private String documentBaseUri;

    @Getter
    @Column(updatable = false)
    private String signature;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private User admin;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_id")
    private AdmCaseDeletionRegistration registration;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decline_reason_id")
    private AdmCaseDeletionRequestDeclineReason declineReason;

    @Getter
    @Setter
    private String declineComment;

    //  JPA AND CRITERIA FIELD ONLY

    @Column(name = "admin_id", updatable = false, insertable = false)
    private Long adminId;

    @Column(name = "registration_id", updatable = false, insertable = false)
    private Long registrationId;

    @Column(name = "decline_reason_id", updatable = false, insertable = false)
    private Long declineReasonId;

    @Column(name = "delete_reason_id", updatable = false, insertable = false)
    private Long deleteReasonId;

    @Column(name = "status_id", updatable = false, insertable = false)
    private Long statusId;

    @Column(name = "adm_case_id", updatable = false, insertable = false)
    private Long admCaseId;

    @Column(name = "user_id", updatable = false, insertable = false)
    private Long userId;

    @Builder
    public AdmCaseDeletionRequest(AdmCase admCase, User user, AdmCaseDeletionReason deleteReason, String documentBaseUri, String signature) {
        this.admCase = admCase;
        this.user = user;
        this.deleteReason = deleteReason;
        this.documentBaseUri = documentBaseUri;
        this.signature = signature;
        this.status = AdmCaseDeletionRequestStatusAlias.CREATED;
    }

    public Long getAdminId() {
        if (admin == null) return null;
        return admin.getId();
    }

    public Long getRegistrationId() {
        if (registration == null) return null;
        return registration.getId();
    }

    public Long getDeclineReasonId() {
        if (declineReason == null) return null;
        return declineReason.getId();
    }

    public Long getDeleteReasonId() {
        if (deleteReason == null) return null;
        return deleteReason.getId();
    }

    public Long getStatusId() {
        if (status == null) return null;
        return Long.valueOf(status.getId());
    }

    public Long getAdmCaseId() {
        if (admCase == null) return null;
        return admCase.getId();
    }

    public Long getUserId() {
        if (user == null) return null;
        return user.getId();
    }
}
