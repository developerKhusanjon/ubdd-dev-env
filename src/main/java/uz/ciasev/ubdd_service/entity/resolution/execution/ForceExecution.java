package uz.ciasev.ubdd_service.entity.resolution.execution;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "force_execution")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ForceExecution implements Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "punishment_id")
    private Punishment punishment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compensation_id")
    private Compensation compensation;

    @Getter
    private LocalDate executionDate;

    @Getter
    @Enumerated(EnumType.STRING)
    protected ForceExecutionType type;


    //  JPA AND CRITERIA ONLY FIELDS

    @Column(name = "punishment_id", insertable = false, updatable = false)
    private Long punishmentId;

    @Column(name = "compensation_id", insertable = false, updatable = false)
    private Long compensationId;

    public Long getPunishmentId() {
        if (punishment == null) return null;
        return punishment.getId();
    }

    public Long getCompensationId() {
        if (compensation == null) return null;
        return compensation.getId();
    }

    public ForceExecution(Punishment punishment, LocalDate executionDate, ForceExecutionType type) {
        this.punishment = punishment;
        this.compensation = null;
        this.executionDate = executionDate;
        this.type = type;
    }

    public ForceExecution(Compensation compensation, LocalDate executionDate, ForceExecutionType type) {
        this.compensation = compensation;
        punishment = null;
        this.executionDate = executionDate;
        this.type = type;
    }

}
