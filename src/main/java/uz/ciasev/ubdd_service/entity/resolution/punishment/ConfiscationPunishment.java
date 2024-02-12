package uz.ciasev.ubdd_service.entity.resolution.punishment;

import lombok.*;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "confiscation_punishment")
@NoArgsConstructor
@AllArgsConstructor
public class ConfiscationPunishment extends AbstractPunishmentDetail implements PunishmentDetail, Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "confiscation_punishment_id_seq")
    @SequenceGenerator(name = "confiscation_punishment_id_seq", sequenceName = "confiscation_punishment_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Setter
    private Long amount;

    public boolean isExecuted() {
       return true;
    }

    @Override
    public LocalDate getExecutionDate() {
        return null;
    }

    @Override
    public AdmStatusAlias calculateStatusAlias() {
        return AdmStatusAlias.EXECUTED;
    }
}
