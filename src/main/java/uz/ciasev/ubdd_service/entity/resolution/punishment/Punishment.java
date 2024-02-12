package uz.ciasev.ubdd_service.entity.resolution.punishment;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.invoice.InvoiceOwnerTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.execution.ExecutionOrganNameHelper;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentType;
import uz.ciasev.ubdd_service.exception.implementation.LogicalException;
import uz.ciasev.ubdd_service.service.execution.BillingEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static uz.ciasev.ubdd_service.entity.status.AdmStatusAlias.EXECUTED;

@Entity
@Table(name = "punishment")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
public class Punishment implements AdmEntity, BillingEntity, Serializable {

    @Getter
    @Transient
    private final EntityNameAlias entityNameAlias = EntityNameAlias.PUNISHMENT;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "punishment_id_seq")
    @SequenceGenerator(name = "punishment_id_seq", sequenceName = "punishment_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @Getter
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime editedTime;

    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decision_id")
    private Decision decision;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "adm_status_id")
    private AdmStatus status;

    @Getter
    private boolean isActive = true;

    @Getter
    @Setter
    private boolean isMain = false;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "type_id")
    private PunishmentType type;

    @Getter
    @Setter
    private String amountText;

    @Getter
    @Setter
    private LocalDate executionDate;

    @Getter
    @Setter
    @Column(name = "execution_user_id")
    private Long executionUserId;

    @Getter
    @Setter
    private String executionOrganName;

    @Getter
    @Setter
    @OneToOne(mappedBy = "punishment", cascade = CascadeType.ALL)
    private ArrestPunishment arrest;

    @Getter
    @Setter
    @OneToOne(mappedBy = "punishment", cascade = CascadeType.ALL)
    private CommunityWorkPunishment communityWork;

    @Getter
    @Setter
    @OneToOne(mappedBy = "punishment", cascade = CascadeType.ALL)
    private ConfiscationPunishment confiscation;

    @Getter
    @Setter
    @OneToOne(mappedBy = "punishment", cascade = CascadeType.ALL)
    private DeportationPunishment deportation;

    @Getter
    @Setter
    @OneToOne(mappedBy = "punishment", cascade = CascadeType.ALL)
    private LicenseRevocationPunishment licenseRevocation;

    @Getter
    @Setter
    @OneToOne(mappedBy = "punishment", cascade = CascadeType.ALL)
    private MedicalPunishment medical;

    @Getter
    @Setter
    @OneToOne(mappedBy = "punishment", cascade = CascadeType.ALL)
    private PenaltyPunishment penalty;

    @Getter
    @Setter
    @OneToOne(mappedBy = "punishment", cascade = CascadeType.ALL)
    private WithdrawalPunishment withdrawal;

    @Getter
    @Setter
    private String executionUri;

    // CRITERIA AND JPA ONLY FIELDS

    @Column(name = "decision_id", insertable = false, updatable = false)
    private Long decisionId;

    //    todo переименовать при миграцие
//    @Column(name = "punishment_type_id")
    @Column(name = "type_id", insertable = false, updatable = false)
    private Long punishmentTypeId;

    @Column(name = "adm_status_id", insertable = false, updatable = false)
    private Long statusId;

    public Long getDecisionId() {
        if (decision == null) return null;
        return decision.getId();
    }

    public Long getPunishmentTypeId() {
        if (type == null) return null;
        return type.getId();
    }

    public Long getStatusId() {
        if (status == null) return null;
        return status.getId();
    }

    public boolean isExecuted() {
        return this.status.is(EXECUTED);
    }

    public void appendExecutionOrganName(String name) {
        executionOrganName = ExecutionOrganNameHelper.append(executionOrganName, name);
    }

    public void removeExecutionOrganName(String name) {
        executionOrganName = ExecutionOrganNameHelper.remove(executionOrganName, name);
    }

    public PunishmentDetail getDetail() {
        switch (type.getAlias()) {
            case ARREST:
                return arrest;
            case PENALTY:
                return penalty;
            case WITHDRAWAL:
                return withdrawal;
            case DEPORTATION:
                return deportation;
            case CONFISCATION:
                return confiscation;
            case COMMUNITY_WORK:
                return communityWork;
            case MEDICAL_PENALTY:
                return medical;
            case LICENSE_REVOCATION:
                return licenseRevocation;
            default:
                throw new RuntimeException("Unexpecter punishment type");
        }
    }


    @Override
    public InvoiceOwnerTypeAlias getInvoiceOwnerTypeAlias() {
        if (penalty == null) throw new LogicalException("Not penaltyPunishment");
        return InvoiceOwnerTypeAlias.PENALTY;
    }

//    public Invoice getInvoice() {
//        if (penalty == null) throw new LogicalException("Not penaltyPunishment");
//        return penalty.getInvoice();
//    }

    @Override
    public Long getAmount() {
        if (penalty == null) throw new LogicalException("Not penaltyPunishment");
        return penalty.getAmount();
    }

    @Override
    public Long getPaidAmount() {
        if (penalty == null) throw new LogicalException("Not penaltyPunishment");
        return penalty.getPaidAmount();
    }

    @Override
    public LocalDateTime getLastPayTime() {
        if (penalty == null) throw new LogicalException("Not penaltyPunishment");
        return penalty.getLastPayTime();
    }

    @Override
    public void setPaidAmount(Long value) {
        if (penalty == null) throw new LogicalException("Not penaltyPunishment");
        penalty.setPaidAmount(value);
    }

    @Override
    public void setLastPayTime(LocalDateTime value) {
        if (penalty == null) throw new LogicalException("Not penaltyPunishment");
        penalty.setLastPayTime(value);
    }

    @Override
    public Long getCurrentAmount() {
        if (penalty == null) throw new LogicalException("Not penaltyPunishment");
        return penalty.getCurrentAmount();
    }

//    @Override
//    public Long getActualAmountForDate(LocalDate value) {
//        if (penalty == null) throw new LogicalException("Not penaltyPunishment");
//        return penalty.getAmountForDate(value);
//    }
}
