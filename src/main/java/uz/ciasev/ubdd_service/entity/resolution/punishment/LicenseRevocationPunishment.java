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
@Table(name = "license_revocation_punishment")
@NoArgsConstructor
@AllArgsConstructor
public class LicenseRevocationPunishment extends AbstractPunishmentDetail implements PunishmentDetail, Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "license_revocation_punishment_id_seq")
    @SequenceGenerator(name = "license_revocation_punishment_id_seq", sequenceName = "license_revocation_punishment_id_seq", allocationSize = 1)
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
    private LocalDate mayBeReturnedAfterDate;

    @Override
    public LocalDate getExecutionDate() {
        return mayBeReturnedAfterDate;
    }

    @Override
    public AdmStatusAlias calculateStatusAlias() {
        if (LocalDate.now().isAfter(mayBeReturnedAfterDate)) {
            return AdmStatusAlias.EXECUTED;
        }

        return AdmStatusAlias.IN_EXECUTION_PROCESS;
    }
}
