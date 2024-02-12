package uz.ciasev.ubdd_service.entity.resolution.punishment;

import lombok.*;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "community_work_punishment")
@NoArgsConstructor
@AllArgsConstructor
public class CommunityWorkPunishment extends AbstractPunishmentDetail implements PunishmentDetail, Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "community_work_punishment_id_seq")
    @SequenceGenerator(name = "community_work_punishment_id_seq", sequenceName = "community_work_punishment_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Setter
    private int hours;

    @Override
    public LocalDate getExecutionDate() {
        return null;
    }

    @Override
    public AdmStatusAlias calculateStatusAlias() {
        return AdmStatusAlias.EXECUTED;
    }
}
