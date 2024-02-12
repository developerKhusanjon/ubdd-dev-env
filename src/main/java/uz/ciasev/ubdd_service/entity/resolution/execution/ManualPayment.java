package uz.ciasev.ubdd_service.entity.resolution.execution;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Immutable;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.damage.DamageSettlementDetail;
import uz.ciasev.ubdd_service.entity.dict.resolution.ManualPaymentSourceType;
import uz.ciasev.ubdd_service.entity.invoice.InvoiceOwnerType;
import uz.ciasev.ubdd_service.entity.dict.resolution.ManualPaymentSourceTypeAlias;
import uz.ciasev.ubdd_service.entity.invoice.InvoiceOwnerTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.utils.converter.InvoiceOwnerTypeIdToAliasConverter;
import uz.ciasev.ubdd_service.utils.converter.ManualPaymentSourceTypeAliasConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Immutable
@Entity
@Table(name = "manual_payment")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
public class ManualPayment {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Getter
    @Setter
    @Convert(converter = InvoiceOwnerTypeIdToAliasConverter.class)
    @Column(name = "owner_type_id")
    private InvoiceOwnerTypeAlias ownerTypeAlias;

    @Getter
    @Setter
    @Column(name = "source_type_id")
    @Convert(converter = ManualPaymentSourceTypeAliasConverter.class)
    private ManualPaymentSourceTypeAlias sourceTypeAlias;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "penalty_punishment_id")
    private PenaltyPunishment penaltyPunishment;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compensation_id")
    private Compensation compensation;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "damage_settlement_detail_id")
    private DamageSettlementDetail damageSettlementDetail;

    @Getter
    @Setter
    private Long amount;

    @Getter
    @Setter
    private LocalDate paymentDate;

    @Getter
    @Setter
    private String description;


    // УДОБНЫЕ ПОЛЯ

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Column(name = "owner_type_id", insertable = false, updatable = false)
    private Long ownerTypeId;

    @Column(name = "penalty_punishment_id", insertable = false, updatable = false)
    private Long penaltyPunishmentId;

    @Column(name = "compensation_id", insertable = false, updatable = false)
    private Long compensationId;

    @Column(name = "damage_settlement_detail_id", insertable = false, updatable = false)
    private Long damageSettlementDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_type_id", insertable = false, updatable = false)
    private ManualPaymentSourceType sourceType;

    @Column(name = "source_type_id", insertable = false, updatable = false)
    private Long sourceTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_type_id", insertable = false, updatable = false)
    private InvoiceOwnerType ownerType;


    public Long getPenaltyPunishmentId() {
        if (penaltyPunishment == null) return null;
        return penaltyPunishment.getId();
    }

    public Long getCompensationId() {
        if (compensation == null) return null;
        return compensation.getId();
    }

    public Long getDamageSettlementDetailId() {
        if (damageSettlementDetail == null) return null;
        return damageSettlementDetail.getId();
    }
}
