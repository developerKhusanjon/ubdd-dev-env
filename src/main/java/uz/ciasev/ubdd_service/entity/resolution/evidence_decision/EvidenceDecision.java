package uz.ciasev.ubdd_service.entity.resolution.evidence_decision;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.entity.dict.evidence.Currency;
import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceCategory;
import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceResult;
import uz.ciasev.ubdd_service.entity.dict.evidence.Measures;
import uz.ciasev.ubdd_service.service.resolution.evidence_decision.EvidenceDecisionCreateRequest;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "evidence_decision")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class EvidenceDecision implements AdmEntity, Serializable {

    @Getter
    @Transient
    private final EntityNameAlias entityNameAlias = EntityNameAlias.EVIDENCE_DECISION;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "evidence_decision_id_seq")
    @SequenceGenerator(name = "evidence_decision_id_seq", sequenceName = "evidence_decision_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @Getter
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime editedTime;

    @Getter
    @Column(updatable = false)
    private boolean isActive = true;

    @Getter
    @Column(updatable = false)
    private String personDescription;

    @Getter
    @Column(updatable = false)
    private Double quantity;

    @Getter
    @Column(updatable = false)
    private Long cost;

    @Getter
    @Column(updatable = false)
    private Long evidenceSudId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resolution_id", updatable = false)
    private Resolution resolution;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "evidence_id", updatable = false)
//    private Evidence evidence;
    // С поля убрали ограничение внешнего ключа, так как:
    //  - после отмены решения суда, инспектор может удалить улику
    //  - суд может прислать эдитинг, с id улики, ктаорая у нас уже удалена.
    @Getter
    @Column(name = "evidence_id", updatable = false)
    private Long evidenceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evidence_result_id", updatable = false)
    private EvidenceResult evidenceResult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evidence_category_id", updatable = false)
    private EvidenceCategory evidenceCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "measure_id", updatable = false)
    private Measures measure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", updatable = false)
    private Currency currency;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adm_status_id")
    private AdmStatus status;


    //    JPA AND CRITERIA FIELDS ONLY

    @Column(name = "resolution_id", insertable = false, updatable = false)
    private Long resolutionId;

    @Column(name = "evidence_result_id", insertable = false, updatable = false)
    private Long evidenceResultId;

    @Column(name = "adm_status_id", insertable = false, updatable = false)
    private Long statusId;

    @Column(name = "evidence_category_id", insertable = false, updatable = false)
    private Long evidenceCategoryId;

    @Column(name = "measure_id", insertable = false, updatable = false)
    private Long measureId;

    @Column(name = "currency_id", insertable = false, updatable = false)
    private Long currencyId;

    public EvidenceDecision(EvidenceDecisionCreateRequest request) {
        this.resolution = request.getResolution();
        this.status = request.getStatus();
        this.evidenceSudId = request.getEvidenceSudId();
        this.evidenceResult = request.getEvidenceResult();
        this.evidenceId = request.getEvidenceId();
        this.evidenceCategory = request.getEvidenceCategory();
        this.measure = request.getMeasure();
        this.quantity = request.getQuantity();
        this.cost = request.getCost();
        this.currency = request.getCurrency();
        this.personDescription = request.getPersonDescription();
    }


    public Long getResolutionId() {
        if (resolution == null) return null;
        return resolution.getId();
    }

    public Long getEvidenceResultId() {
        if (evidenceResult == null) return null;
        return evidenceResult.getId();
    }

    public Long getStatusId() {
        if (status == null) return null;
        return status.getId();
    }

    public Long getEvidenceCategoryId() {
        if (evidenceCategory == null) return null;
        return evidenceCategory.getId();
    }

    public Long getMeasureId() {
        if (measure == null) return null;
        return measure.getId();
    }

    public Long getCurrencyId() {
        if (currency == null) return null;
        return currency.getId();
    }
}

