package uz.ciasev.ubdd_service.entity.resolution.punishment;

import lombok.*;
import uz.ciasev.ubdd_service.entity.CiasevEntity;
import uz.ciasev.ubdd_service.entity.dict.resolution.ArrestPlaceType;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "arrest_punishment")
@NoArgsConstructor
@AllArgsConstructor
public class ArrestPunishment extends AbstractPunishmentDetail implements CiasevEntity, PunishmentDetail, Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arrest_punishment_id_seq")
    @SequenceGenerator(name = "arrest_punishment_id_seq", sequenceName = "arrest_punishment_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Setter
    private int days;

    @Getter
    @Setter
    private LocalDate inDate;

    @Getter
    @Setter
    private String inState;

    @Getter
    @Setter
    private LocalDate outDate;

    @Getter
    @Setter
    private String outState;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrest_place_type_id")
    private ArrestPlaceType arrestPlaceType;


//    JPA AND CRITERIA FIELD ONLY

    @Column(name = "arrest_place_type_id", insertable = false, updatable = false)
    private Long arrestPlaceTypeId;

    public Long getArrestPlaceTypeId() {
        if (arrestPlaceType == null) return null;
        return arrestPlaceType.getId();
    }

    @Override
    public LocalDate getExecutionDate() {
        return outDate;
    }

    @Override
    public AdmStatusAlias calculateStatusAlias() {
        if (outDate != null) {
            return AdmStatusAlias.EXECUTED;
        }

        if (inDate != null) {
            return AdmStatusAlias.IN_EXECUTION_PROCESS;
        }

        return AdmStatusAlias.DECISION_MADE;
    }
}
