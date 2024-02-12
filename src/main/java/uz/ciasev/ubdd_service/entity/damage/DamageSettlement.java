package uz.ciasev.ubdd_service.entity.damage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.dict.VictimType;
import uz.ciasev.ubdd_service.entity.victim.Victim;
import uz.ciasev.ubdd_service.entity.violator.Violator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "damage_settlement")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DamageSettlement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "damage_settlement_id_seq")
    @SequenceGenerator(name = "damage_settlement_id_seq", sequenceName = "damage_settlement_id_seq", allocationSize = 1)
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
    private Long totalAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "violator_id", updatable = false)
    Violator violator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "victim_id", updatable = false)
    Victim victim;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "victim_type_id")
    @Getter
    VictimType victimType;


    // JPA AND CRITERIA FIELDS ONLY

    @Column(name = "violator_id", insertable = false, updatable = false)
    private Long violatorId;

    @Column(name = "victim_id", insertable = false, updatable = false)
    private Long victimId;

    @Column(name = "victim_type_id", insertable = false, updatable = false)
    private Long victimTypeId;


    public Long getViolatorId() {
        if (this.violator == null) return null;
        return violator.getId();
    }

    public Long getVictimId() {
        if (this.victim == null) return null;
        return victim.getId();
    }

    public Long getVictimTypeId() {
        if (this.victimType == null) return null;
        return victimType.getId();
    }
}
