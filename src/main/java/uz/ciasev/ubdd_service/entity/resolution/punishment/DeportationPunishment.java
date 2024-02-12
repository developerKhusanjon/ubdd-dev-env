package uz.ciasev.ubdd_service.entity.resolution.punishment;

import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.resolution.DeportationTerminal;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "deportation_punishment")
@NoArgsConstructor
@AllArgsConstructor
public class DeportationPunishment extends AbstractPunishmentDetail implements PunishmentDetail, Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deportation_punishment_id_seq")
    @SequenceGenerator(name = "deportation_punishment_id_seq", sequenceName = "deportation_punishment_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Setter
    private Integer years;

    @Getter
    @Setter
    private Integer months;

    @Getter
    @Setter
    private Integer days;

    @Getter
    @Setter
    private LocalDate deportationDate;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deportation_terminal_id")
    private DeportationTerminal deportationTerminal;

    public Long getDeportationTerminalId() {
        if (deportationTerminal == null) return null;
        return deportationTerminal.getId();
    }

    @Override
    public LocalDate getExecutionDate() {
        return deportationDate;
    }

    @Override
    public AdmStatusAlias calculateStatusAlias() {
        if (deportationDate != null) {
            return AdmStatusAlias.EXECUTED;
        }

        return AdmStatusAlias.DECISION_MADE;
    }
}
