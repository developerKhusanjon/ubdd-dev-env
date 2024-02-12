package uz.ciasev.ubdd_service.entity.resolution.compensation;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.dict.VictimType;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.invoice.InvoiceOwnerTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.execution.ExecutionOrganNameHelper;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.settings.BankAccount;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.entity.victim.Victim;
import uz.ciasev.ubdd_service.service.execution.BillingEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static uz.ciasev.ubdd_service.entity.status.AdmStatusAlias.EXECUTED;

@Entity
@Table(name = "compensation")
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
@EntityListeners(AuditingEntityListener.class)
public class Compensation implements AdmEntity, BillingEntity, Serializable {

    private Boolean isStatusSync = false;
    private String statusSyncMessage;

    @Getter
    @Setter
    private Boolean isManualPaymentAmountSet;

    @Getter
    @Setter
    private String manualPaymentAmountMessage;

    @Getter
    @Transient
    private final EntityNameAlias entityNameAlias = EntityNameAlias.COMPENSATION;

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compensation_id_seq")
    @SequenceGenerator(name = "compensation_id_seq", sequenceName = "compensation_id_seq", allocationSize = 1)
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

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "adm_status_id")
    private AdmStatus status;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decision_id")
    private Decision decision;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "victim_type_id")
    private VictimType victimType;

    @Getter
    @Setter
    private Long payerTypeId;

    @Getter
    @Setter
    private String payerAdditionalInfo;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "victim_id")
    private Victim victim;

    @Getter
    @Setter
    private Long amount;

    @Getter
    @Setter
    private Long paidAmount = 0L;

    @Getter
    @Setter
    private LocalDateTime lastPayTime;

    @Getter
    private String executionOrganName;

    @Getter
    @Setter
    private LocalDate executionDate;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount account;

    @OneToMany(mappedBy = "compensation", fetch = FetchType.EAGER)
    private List<Invoice> invoices;

    // JPA AND CRITERIA ONLY

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @Column(name = "victim_id", insertable = false, updatable = false)
    private Long victimId;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @Column(name = "victim_type_id", insertable = false, updatable = false)
    private Long victimTypeId;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @Column(name = "decision_id", insertable = false, updatable = false)
    private Long decisionId;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @Column(name = "adm_status_id", insertable = false, updatable = false)
    private Long statusId;


    public boolean isExecuted() {
        return this.status.is(EXECUTED);
    }

    public void setInvoice(Invoice i) {

        if (invoices == null) {
            invoices = new ArrayList<>();
        }
        this.invoices.add(i);
    }

    public Invoice getInvoice() {

        if (invoices == null) {
            return null;
        }
        if (invoices.isEmpty()) {
            return null;
        }
        return invoices.get(0);
    }

    public Optional<Invoice> getInvoiceOptional() {
        return Optional.ofNullable(getInvoice());
    }

    public String getInvoiceSerial() {
        return invoices.stream().map(Invoice::getInvoiceSerial).findFirst().orElse(null);
    }

    public Long getDecisionId() {
        return decision.getId();
    }

    public Long getStatusId() {
        return status.getId();
    }

    public Long getVictimId() {
        if (victim == null) return null;
        return victim.getId();
    }

    public Long getVictimTypeId() {
        return victimType.getId();
    }

    public void appendExecutionOrganName(String name) {
        executionOrganName = ExecutionOrganNameHelper.append(executionOrganName, name);
    }

    public void removeExecutionOrganName(String name) {
        executionOrganName = ExecutionOrganNameHelper.remove(executionOrganName, name);
    }

    @Override
    public InvoiceOwnerTypeAlias getInvoiceOwnerTypeAlias() {
        return InvoiceOwnerTypeAlias.COMPENSATION;
    }

    @Override
    public Long getCurrentAmount() {
        return amount;
    }

    public AdmStatusAlias calculateStatusAlias() {
        int compareResult = paidAmount.compareTo(amount);
        if (compareResult >= 0) {
//        if (isExecuted()) {
            return AdmStatusAlias.EXECUTED;
        }

        if (paidAmount != 0L) {
            return AdmStatusAlias.IN_EXECUTION_PROCESS;
        }

        return AdmStatusAlias.DECISION_MADE;
    }
}
