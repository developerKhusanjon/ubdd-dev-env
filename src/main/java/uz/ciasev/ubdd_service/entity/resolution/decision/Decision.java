package uz.ciasev.ubdd_service.entity.resolution.decision;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.dict.article.Article;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.entity.dict.resolution.CriminalCaseTransferResultType;
import uz.ciasev.ubdd_service.entity.dict.resolution.DecisionType;
import uz.ciasev.ubdd_service.entity.dict.resolution.DecisionTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.resolution.TerminationReason;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.service.generator.AdmDocumentNumber;
import uz.ciasev.ubdd_service.utils.converter.DecisionTypeIdToAliasConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static uz.ciasev.ubdd_service.entity.status.AdmStatusAlias.EXECUTED;

@Entity
@Table(name = "decision")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
public class Decision implements AdmEntity, Serializable {

    @Getter
    @Transient
    private final EntityNameAlias entityNameAlias = EntityNameAlias.DECISION;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "decision_id_seq")
    @SequenceGenerator(name = "decision_id_seq", sequenceName = "decision_id_seq", allocationSize = 1)
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
    private boolean isActive = true;

    @Setter
    @Column(updatable = false)
    private Boolean isSavedPdf = false;

    public Boolean getIsSavedPdf() {
        if (isSavedPdf == null) return false;
        return isSavedPdf;
    }

    @Getter
    @Column(updatable = false)
    private Boolean isCourt;

    @Getter
    @Setter
    @Column(updatable = false)
    private Long defendantId;

    @Getter
    @Setter
    @Column(updatable = false)
    private Boolean isJuridic;

    @Getter
    @Column(updatable = false)
    private String series;

    @Getter
    @Column(updatable = false)
    private String number;

    @Column(updatable = false)
    private Boolean isNumberUnique = true;

    @Getter
    @Setter
    @Column(updatable = false)
    private LocalDate executionFromDate;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", updatable = false)
    private Article article;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_part_id", updatable = false)
    private ArticlePart articlePart;


    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_violation_type_id", updatable = false)
    private ArticleViolationType articleViolationType;


    @Getter
    @Setter
    @Column(updatable = false)
    private boolean isArticle33;

    @Getter
    @Setter
    @Column(updatable = false)
    private boolean isArticle34;

    @Getter
    @Setter
    private Boolean handleByMibPreSend = false;

    @Getter
    @Setter
    private LocalDate executedDate;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "violator_id", updatable = false)
    private Violator violator;

    @Getter
    @ManyToOne
    @JoinColumn(name = "resolution_id", updatable = false)
    private Resolution resolution;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "adm_status_id")
    private AdmStatus status;

    @Getter
    @Setter
    @Convert(converter = DecisionTypeIdToAliasConverter.class)
    @Column(name = "decision_type_id", updatable = false)
    protected DecisionTypeAlias decisionTypeAlias;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "termination_reason_id", updatable = false)
    private TerminationReason terminationReason;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criminal_case_transfer_result_type_id", updatable = false)
    private CriminalCaseTransferResultType criminalCaseTransferResultType;

    @Getter
    @OneToMany(mappedBy = "decision", fetch = FetchType.EAGER)
    private List<Punishment> punishments = new ArrayList<>();


    // УДОБНЫЕ ПОЛЯ

    @Column(name = "resolution_id", insertable = false, updatable = false)
    private Long resolutionId;

    @Column(name = "adm_status_id", insertable = false, updatable = false)
    private Long statusId;

    @Column(name = "violator_id", insertable = false, updatable = false)
    private Long violatorId;

    @Column(name = "decision_type_id", insertable = false, updatable = false)
    private Long decisionTypeId;

    @Column(name = "termination_reason_id", insertable = false, updatable = false)
    private Long terminationReasonId;

    @Column(name = "article_part_id", updatable = false, insertable = false)
    private Long articlePartId;

    @Column(name = "article_violation_type_id", updatable = false, insertable = false)
    private Long articleViolationTypeId;

    @Column(name = "criminal_case_transfer_result_type_id", updatable = false, insertable = false)
    private Long criminalCaseTransferResultTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decision_type_id", updatable = false, insertable = false)
    private DecisionType decisionType;

    @OneToMany(mappedBy = "decision", fetch = FetchType.LAZY)
    private List<MibExecutionCard> mibExecutionCards;

    public Long getResolutionId() {
        return resolution.getId();
    }

    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
        this.isCourt = resolution.getOrgan().isCourt();
    }

    public Long getStatusId() {
        if (status==null) return null;
        return status.getId();
    }

    public Long getViolatorId() {
        if (violator==null) return null;
        return violator.getId();
    }

    public Long getDecisionTypeId() {
        if (decisionTypeAlias == null) return null;
        return decisionTypeAlias.getId();
    }

    public Long getTerminationReasonId() {
        if (terminationReason == null) return null;
        return terminationReason.getId();
    }

    public Long getArticleId() {
        if (article == null) return null;
        return article.getId();
    }

    public Long getArticlePartId() {
        if (articlePart == null) return null;
        return articlePart.getId();
    }

    public Long getArticleViolationTypeId() {
        if (articleViolationType == null) return null;
        return articleViolationType.getId();
    }

    public Long getCriminalCaseTransferResultTypeId() {
        if (criminalCaseTransferResultType == null) return null;
        return criminalCaseTransferResultType.getId();
    }


    public Punishment getMainPunishment() {
        if (Objects.nonNull(this.punishments)) {
            return punishments.stream().filter(Punishment::isMain).findFirst().orElse(null);
        }

        return null;
    }


    public Long getMainPunishmentId() {
        return Optional.ofNullable(getMainPunishment()).map(Punishment::getId).orElse(null);
    }

    public Punishment getAdditionPunishment() {
        if (Objects.nonNull(this.punishments)) {
            return punishments.stream().filter(p -> !p.isMain()).findFirst().orElse(null);
        }

        return null;
    }

    public void setMainPunishment(Punishment mainPunishment) {
        if (Objects.nonNull(mainPunishment)) {
            this.punishments.add(mainPunishment);
        }
    }

    public void setAdditionPunishment(Punishment additionPunishment) {
        if (Objects.nonNull(additionPunishment)) {
            this.punishments.add(additionPunishment);
        }
    }

    public boolean isExecuted() {
        return this.status.is(EXECUTED);
    }

    public LocalDate getMastByExecutedBeforeDate() {
        return this.executionFromDate.plusDays(30);
    }

    public LocalDate getExecuteBeforeDate() {
        return executionFromDate.plusDays(30);
    }

    public Optional<PenaltyPunishment> getPenalty() {
        return Optional.ofNullable(getMainPunishment()).map(Punishment::getPenalty);
    }

    public void setSeriesAndNumber(AdmDocumentNumber documentNumber) {
        this.series = documentNumber.getSeries();
        this.number = documentNumber.getNumber();
        this.isNumberUnique = documentNumber.isUniqueNumber();

    }
}
