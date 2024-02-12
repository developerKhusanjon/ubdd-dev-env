package uz.ciasev.ubdd_service.entity.resolution.punishment;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractPunishmentDetail implements PunishmentDetail, Serializable {

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "punishment_id")
    private Punishment punishment;


//    JPA AND CRITERIA FIELD ONLY

    @Column(name = "punishment_id", insertable = false, updatable = false)
    private Long punishmentId;

    public Long getPunishmentId() {
        if (punishment == null) return null;
        return punishment.getId();
    }
}
