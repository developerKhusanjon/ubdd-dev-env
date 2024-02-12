package uz.ciasev.ubdd_service.entity.resolution.punishment;

import lombok.*;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "withdrawal_punishment")
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalPunishment extends AbstractPunishmentDetail implements PunishmentDetail, Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "withdrawal_punishment_id_seq")
    @SequenceGenerator(name = "withdrawal_punishment_id_seq", sequenceName = "withdrawal_punishment_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Setter
    private Long amount;

    @Getter
    @Setter
    private LocalDate saleDate;

    @Getter
    @Setter
    private Long saleAmount;

    @Getter
    @Setter
    private String saleItems;

    @Getter
    @Setter
    private LocalDate repaymentDate;

    @Override
    public LocalDate getExecutionDate() {
        return repaymentDate;
    }

    @Override
    public AdmStatusAlias calculateStatusAlias() {
        if (repaymentDate != null) {
            return AdmStatusAlias.EXECUTED;
        }

        if (saleDate != null) {
            return AdmStatusAlias.IN_EXECUTION_PROCESS;
        }

        return AdmStatusAlias.DECISION_MADE;
    }
}
