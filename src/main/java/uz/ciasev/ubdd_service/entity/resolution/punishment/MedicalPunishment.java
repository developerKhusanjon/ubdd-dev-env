package uz.ciasev.ubdd_service.entity.resolution.punishment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "medical_punishment")
@NoArgsConstructor
@AllArgsConstructor
public class MedicalPunishment extends AbstractPunishmentDetail implements PunishmentDetail, Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medical_punishment_id_seq")
    @SequenceGenerator(name = "medical_punishment_id_seq", sequenceName = "medical_punishment_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Setter
    private int days;

    @Override
    public LocalDate getExecutionDate() {
        return null;
    }

    @Override
    public AdmStatusAlias calculateStatusAlias() {
        return AdmStatusAlias.DECISION_MADE;
    }
}
