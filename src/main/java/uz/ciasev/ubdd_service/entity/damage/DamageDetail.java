package uz.ciasev.ubdd_service.entity.damage;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.dict.DamageType;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "damage_detail")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DamageDetail implements AdmEntity, Serializable {

    @Transient
    @Getter
    private EntityNameAlias entityNameAlias = EntityNameAlias.DAMAGE;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "damage_detail_id_seq")
    @SequenceGenerator(name = "damage_detail_id_seq", sequenceName = "damage_detail_id_seq", allocationSize = 1)
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

    private boolean isActive = true;

    @Getter
    private Long amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_id", updatable = false)
    private Protocol protocol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "damage_id")
    @Setter
    private Damage damage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "damage_type_id")
    private DamageType damageType;


    // JPA AND CRITERIA FIELDS ONLY

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Column(name = "damage_id", insertable = false, updatable = false)
    private Long damageId;

    @Column(name = "protocol_id", insertable = false, updatable = false)
    private Long protocolId;

    @Column(name = "damage_type_id", insertable = false, updatable = false)
    private Long damageTypeId;


    public DamageDetail(User user, Damage damage, Protocol protocol, DamageType damageType, Long amount) {
        this.user = user;
        this.damage = damage;
        this.protocol = protocol;
        this.damageType = damageType;
        this.amount = amount;
    }

    public DamageDetail(User user) {
        this.user = user;
    }

    public DamageDetail update(DamageType damageType, Long amount) {
        this.damageType = damageType;
        this.amount = amount;

        return this;
    }

    public Long getUserId() {
        if (this.user == null) return null;
        return user.getId();
    }

    public Long getDamageId() {
        if (this.damage == null) return null;
        return damage.getId();
    }

    public Long getProtocolId() {
        if (this.protocol == null) return null;
        return protocol.getId();
    }

    public Long getDamageTypeId() {
        if (this.damageType == null) return null;
        return damageType.getId();
    }
}
