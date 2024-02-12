package uz.ciasev.ubdd_service.entity.invoice;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.damage.DamageSettlementDetail;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;
import uz.ciasev.ubdd_service.utils.converter.InvoiceOwnerTypeIdToAliasConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoice")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Invoice implements AdmEntity {

    @Getter
    @Transient
    private final EntityNameAlias entityNameAlias = EntityNameAlias.INVOICE;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_id_seq")
    @SequenceGenerator(name = "invoice_id_seq", sequenceName = "invoice_id_seq", allocationSize = 1)
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

    @Getter
    @Setter
    @Convert(converter = InvoiceOwnerTypeIdToAliasConverter.class)
    @Column(name = "invoice_owner_type_id")
    private InvoiceOwnerTypeAlias ownerTypeAlias;

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
    private boolean isActive = true;

    @Getter
    @Setter
    @Column(name = "is_discount")
    private Boolean isDiscount70 = false;

    @Getter
    @Setter
    private Boolean isDiscount50 = false;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private InvoiceDeactivateReasonAlias deactivateReason;

    @Getter
    @Setter
    private LocalDateTime deactivateTime;

    @Getter
    @Setter
    private String invoiceInternalNumber;

    @Getter
    @Setter
    private Long invoiceId;

    @Getter
    @Setter
    private String invoiceSerial;

    @Getter
    @Setter
    private LocalDate invoiceDate;

    @Getter
    @Setter
    @Column(name = "discount_for_date")
    private LocalDate discount70ForDate;

    @Getter
    @Setter
    @Column(name = "discount50_for_date")
    private LocalDate discount50ForDate;

    @Getter
    @Setter
    private Long amount;

    @Getter
    @Setter
    @Column(name = "discount_amount")
    private Long discount70Amount;

    @Getter
    @Setter
    @Column(name = "discount50_amount")
    private Long discount50Amount;

    @Getter
    @Setter
    private String organName;

    @Getter
    @Setter
    private String bankInn;

    @Getter
    @Setter
    private String bankName;

    @Getter
    @Setter
    private String bankCode;

    @Getter
    @Setter
    private String bankAccount;

    @Getter
    @Setter
    private String treasuryAccount;

    @Getter
    @Setter
    private String payerName;

    @Getter
    @Setter
    private String payerAddress;

    @Getter
    @Setter
    private LocalDate payerBirthdate;

    @Getter
    @Setter
    private String payerInn;

    @Getter
    @Setter
    private String addition;

//    @Getter
//    @Setter
//    private String article;
//
//    @Getter
//    @Setter
//    private String prim;
//
//    @Getter
//    @Setter
//    private String part;

    @Getter
    @Setter
    private String deactivateReasonDesc;

    // УДОБНЫЕ ПОЛЯ

    @Column(name = "invoice_owner_type_id", insertable = false, updatable = false)
    private Long ownerTypeId;

    @Column(name = "penalty_punishment_id", insertable = false, updatable = false)
    private Long penaltyPunishmentId;

    @Column(name = "compensation_id", insertable = false, updatable = false)
    private Long compensationId;

    @Column(name = "damage_settlement_detail_id", insertable = false, updatable = false)
    private Long damageSettlementDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_owner_type_id", insertable = false, updatable = false)
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

    public boolean registeredInBilling() {
        return this.invoiceId != null;
    }

    public Long getOwnerTypeId() {
        if (ownerTypeAlias == null) return null;
        return ownerTypeAlias.getId();
    }

    public void setDiscountVersion(PenaltyPunishment.DiscountVersion discountVersion) {
        isDiscount70 = discountVersion.isDiscount70();
        isDiscount50 = discountVersion.isDiscount50();
    }
}
