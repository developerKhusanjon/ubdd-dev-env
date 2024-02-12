package uz.ciasev.ubdd_service.entity.mib;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.mib.MibNotificationType;
import uz.ciasev.ubdd_service.entity.dict.mib.MibNotificationTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.utils.converter.MibNotificationTypeAliasConverter;
import uz.ciasev.ubdd_service.utils.converter.MibOwnerTypeIdToAliasConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@Table(name = "mib_execution_card")
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class MibExecutionCard implements AdmEntity, Serializable {

    @Getter
    @Transient
    private final EntityNameAlias entityNameAlias = EntityNameAlias.MIB_EXECUTION_CARD;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mib_execution_card_id_seq")
    @SequenceGenerator(name = "mib_execution_card_id_seq", sequenceName = "mib_execution_card_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @Getter
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime editedTime;

    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Getter
    @Setter
    @Convert(converter = MibOwnerTypeIdToAliasConverter.class)
    @Column(name = "mib_owner_type_id")
    private MibOwnerTypeAlias ownerTypeAlias;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "decision_id")
    private Decision decision;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compensation_id")
    private Compensation compensation;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    Region region;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    District district;

    @Getter
    @Setter
    private String outNumber;

    @Getter
    @Setter
    @LastModifiedDate
    private LocalDate outDate;


// Поля переехали в MibCardMovement
//    @Getter
//    @Setter
//    private Long amountOfRecovery;
//
//    @Getter
//    @Setter
//    private Long totalRecoveredAmount;
//
//    @Getter
//    @Setter
//    @Type(type = "jsonb")
//    @Column(columnDefinition = "jsonb")
//    @Basic(fetch = FetchType.LAZY)
//    private List<PaymentData> payments;


    //  Данные оповещения
    @Getter
    @Setter
    private Long notificationId;


    @Getter
    @Setter
    @Convert(converter = MibNotificationTypeAliasConverter.class)
    @Column(name = "notification_type_id")
    private MibNotificationTypeAlias notificationTypeAlias;

//    @Getter
//    @Setter
//    private LocalDate notificationSentDate;
//
//    @Getter
//    @Setter
//    private LocalDate notificationReceiveDate;
//
//    @Getter
//    @Setter
//    private String notificationNumber;
//
//    @Getter
//    @Setter
//    private String notificationText;


    // JPA AND CRITERIA ONLY FIELDS

    @Column(name = "decision_id", insertable = false, updatable = false)
    private Long decisionId;

    @Column(name = "compensation_id", insertable = false, updatable = false)
    private Long compensationId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Column(name = "region_id", insertable = false, updatable = false)
    private Long regionId;

    @Column(name = "district_id", insertable = false, updatable = false)
    private Long districtId;

    @Column(name = "notification_type_id", insertable = false, updatable = false)
    private Long notificationTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_type_id", insertable = false, updatable = false)
    private MibNotificationType notificationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mib_owner_type_id", insertable = false, updatable = false)
    private MibOwnerType ownerType;


    public Long getDecisionId() {
        if (decision == null) return null;
        return decision.getId();
    }


    public Long getOwnerTypeId() {
        if (ownerTypeAlias == null) return null;
        return ownerTypeAlias.getId();
    }

    public Long getCompensationId() {
        if (compensation == null) return null;
        return compensation.getId();
    }

    public Long getUserId() {
        if (user == null) return null;
        return user.getId();
    }

    public Long getRegionId() {
        if (region == null) return null;
        return region.getId();
    }

    public Long getDistrictId() {
        if (district == null) return null;
        return district.getId();
    }

    public Long getNotificationTypeId() {
        if (notificationTypeAlias == null) return null;
        return (long) notificationTypeAlias.getId();
    }

}