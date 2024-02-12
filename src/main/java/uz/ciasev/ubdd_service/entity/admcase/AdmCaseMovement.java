package uz.ciasev.ubdd_service.entity.admcase;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.dict.*;
import uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseMovementStatusAlias;
import uz.ciasev.ubdd_service.entity.dict.admcase.DeclineReason;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.utils.converter.AdmCaseMovementStatusAliasConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "adm_case_movement")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class AdmCaseMovement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_case_movement_id_seq")
    @SequenceGenerator(name = "adm_case_movement_id_seq", sequenceName = "adm_case_movement_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    @Getter
    private Long id;

    @Getter
    private LocalDateTime sendTime;

    @Getter
    private LocalDateTime declineTime;

    @Getter
    private String declineComment;

    @Getter
    private LocalDateTime cancelTime;

    @Column(name = "status_id")
    @Convert(converter = AdmCaseMovementStatusAliasConverter.class)
    private AdmCaseMovementStatusAlias statusAlias;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adm_case_id", updatable = false)
    private AdmCase admCase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_organ_id", updatable = false)
    private Organ fromOrgan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_department_id", updatable = false)
    private Department fromDepartment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_region_id", updatable = false)
    private Region fromRegion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_district_id", updatable = false)
    private District fromDistrict;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_organ_id", updatable = false)
    private Organ toOrgan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_district_id", updatable = false)
    private District toDistrict;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_department_id", updatable = false)
    private Department toDepartment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_region_id", updatable = false)
    private Region toRegion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_user_id", updatable = false)
    private User sendUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decline_user_id")
    private User declineUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decline_reason_id")
    private DeclineReason declineReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancel_user_id")
    private User cancelUser;


    // JPA AND CRITERIA FIELDS

    @Column(name = "status_id", insertable = false, updatable = false)
    private Long statusId;

    @Column(name = "adm_case_id", insertable = false, updatable = false)
    private Long admCaseId;

    @Column(name = "from_organ_id", insertable = false, updatable = false)
    private Long fromOrganId;

    @Column(name = "from_department_id", insertable = false, updatable = false)
    private Long fromDepartmentId;

    @Column(name = "from_region_id", insertable = false, updatable = false)
    private Long fromRegionId;

    @Column(name = "from_district_id", insertable = false, updatable = false)
    private Long fromDistrictId;

    @Column(name = "to_organ_id", insertable = false, updatable = false)
    private Long toOrganId;

    @Column(name = "to_department_id", insertable = false, updatable = false)
    private Long toDepartmentId;

    @Column(name = "to_region_id", insertable = false, updatable = false)
    private Long toRegionId;

    @Column(name = "to_district_id", insertable = false, updatable = false)
    private Long toDistrictId;

    @Column(name = "send_user_id", insertable = false, updatable = false)
    private Long sendUserId;

    @Column(name = "decline_user_id", insertable = false, updatable = false)
    private Long declineUserId;

    @Column(name = "decline_reason_id", insertable = false, updatable = false)
    private Long declineReasonId;

    @Column(name = "cancel_user_id", insertable = false, updatable = false)
    private Long cancelUserId;

    public AdmCaseMovement(
            AdmCase admCase,
            User sendUser,
            Organ toOrgan,
            Department toDepartment,
            Region toRegion,
            District toDistrict) {

        this.admCase = admCase;
        this.sendUser = sendUser;
        this.sendTime = LocalDateTime.now();
        this.statusAlias = AdmCaseMovementStatusAlias.SENT;

        this.fromOrgan = admCase.getOrgan();
        this.fromDepartment = admCase.getDepartment();
        this.fromRegion = admCase.getRegion();
        this.fromDistrict = admCase.getDistrict();

        this.toOrgan = toOrgan;
        this.toDepartment = toDepartment;
        this.toRegion = toRegion;
        this.toDistrict = toDistrict;
    }

    public Long getStatusId() {
        if (this.statusAlias == null) return null;
        return statusAlias.getId();
    }

    public Long getAdmCaseId() {
        if (this.admCase == null) return null;
        return admCase.getId();
    }

    public Long getFromOrganId() {
        if (this.fromOrgan == null) return null;
        return fromOrgan.getId();
    }

    public Long getFromDepartmentId() {
        if (this.fromDepartment == null) return null;
        return fromDepartment.getId();
    }

    public Long getFromRegionId() {
        if (this.fromRegion == null) return null;
        return fromRegion.getId();
    }

    public Long getFromDistrictId() {
        if (this.fromDistrict == null) return null;
        return fromDistrict.getId();
    }

    public Long getToOrganId() {
        if (this.toOrgan == null) return null;
        return toOrgan.getId();
    }

    public Long getToDepartmentId() {
        if (this.toDepartment == null) return null;
        return toDepartment.getId();
    }

    public Long getToRegionId() {
        if (this.toRegion == null) return null;
        return toRegion.getId();
    }

    public Long getToDistrictId() {
        if (this.toDistrict == null) return null;
        return toDistrict.getId();
    }

    public Long getSendUserId() {
        if (this.sendUser == null) return null;
        return sendUser.getId();
    }

    public Long getDeclineUserId() {
        if (this.declineUser == null) return null;
        return declineUser.getId();
    }

    public Long getDeclineReasonId() {
        if (this.declineReason == null) return null;
        return declineReason.getId();
    }

    public Long getCancelUserId() {
        if (this.cancelUser == null) return null;
        return cancelUser.getId();
    }

    public Boolean getIsDecline() {
        return this.statusAlias == AdmCaseMovementStatusAlias.DECLINED;
    }

    public Boolean isSent() {return this.statusAlias == AdmCaseMovementStatusAlias.SENT;}

    public void declineMovement(User user, DeclineReason declineReason, String declineComment) {
        this.declineUser = user;
        this.declineReason = declineReason;
        this.declineComment = declineComment;
        this.declineTime = LocalDateTime.now();
        this.statusAlias = AdmCaseMovementStatusAlias.DECLINED;
    }

    public void cancelMovement(User user) {
        this.cancelUser = user;
        this.cancelTime = LocalDateTime.now();
        this.statusAlias = AdmCaseMovementStatusAlias.CANCELLED;
    }
}