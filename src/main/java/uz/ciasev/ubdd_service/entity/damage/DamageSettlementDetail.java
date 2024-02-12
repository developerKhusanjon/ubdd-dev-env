package uz.ciasev.ubdd_service.entity.damage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.dict.DamageType;
import uz.ciasev.ubdd_service.entity.invoice.InvoiceOwnerTypeAlias;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.service.execution.BillingEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "damage_settlement_detail")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DamageSettlementDetail implements AdmEntity, Serializable, BillingEntity {

    @Transient
    @Getter
    private EntityNameAlias entityNameAlias = EntityNameAlias.DAMAGE_SETTLEMENT_DETAIL;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "damage_settlement_detail_id_seq")
    @SequenceGenerator(name = "damage_settlement_detail_id_seq", sequenceName = "damage_settlement_detail_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    @Getter
    private Long id;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    @Getter
    private LocalDateTime createdTime;

    @Column(nullable = false)
    @LastModifiedDate
    @Getter
    private LocalDateTime editedTime;

    @Getter
    @Setter
    private LocalDateTime lastPayTime;

    @Getter
    private Long amount;

    @Getter
    @Setter
    private Long paidAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_id", updatable = false)
    private Protocol protocol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "damage_detail_id", updatable = false)
    private DamageDetail damageDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "damage_settlement_id", updatable = false)
    private DamageSettlement damageSettlement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "damage_type_id")
    private DamageType damageType;


    // JPA AND CRITERIA FIELDS ONLY

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Column(name = "damage_detail_id", insertable = false, updatable = false)
    private Long damageDetailId;

    @Column(name = "damage_settlement_id", insertable = false, updatable = false)
    private Long damageSettlementId;

    @Column(name = "protocol_id", insertable = false, updatable = false)
    private Long protocolId;

    @Column(name = "damage_type_id", insertable = false, updatable = false)
    private Long damageTypeId;



    public Long getUserId() {
        if (this.user == null) return null;
        return user.getId();
    }

    public Long getDamageDetailId() {
        if (this.damageDetail == null) return null;
        return damageDetail.getId();
    }

    public Long getDamageSettlementId() {
        if (this.damageSettlement == null) return null;
        return damageSettlement.getId();
    }

    public Long getProtocolId() {
        if (this.protocol == null) return null;
        return protocol.getId();
    }

    public Long getDamageTypeId() {
        if (this.damageType == null) return null;
        return damageType.getId();
    }

    @Override
    public InvoiceOwnerTypeAlias getInvoiceOwnerTypeAlias() {
        return InvoiceOwnerTypeAlias.DAMAGE;
    }

    @Override
    public boolean isExecuted() {
        return false;
    }

    @Override
    public Long getCurrentAmount() {
        return amount;
    }
}
