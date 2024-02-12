package uz.ciasev.ubdd_service.entity.protocol;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.article.Article;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.entity.dict.resolution.DecisionType;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentType;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.utils.FioUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@EqualsAndHashCode(of = {"id"})
@Table(name = "repeatability")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Repeatability implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "repeatability_id_seq")
    @SequenceGenerator(name = "repeatability_id_seq", sequenceName = "repeatability_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime editedTime;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "protocol_id")
    private Long protocolId;

    @Column(name = "decision_id")
    private Long decisionId;

    private Long fromProtocolId;

    private String protocolSeries;

    private String protocolNumber;

    @Column(name = "region_id")
    private Long regionId;

    @Column(name = "district_id")
    private Long districtId;

    private LocalDateTime violationTime;

    private String violatorFio;

    private LocalDate violatorBirthDate;

    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "article_part_id")
    private Long articlePartId;

    @Column(name = "article_violation_type_id")
    private Long articleViolationTypeId;

    private Long fromDecisionId;

    private String decisionSeries;

    private String decisionNumber;

    private LocalDateTime resolutionTime;

    @Column(name = "decision_type_id")
    private Long decisionTypeId;

    @Column(name = "punishment_type_id")
    private Long punishmentTypeId;

    private String punishmentAmount;

    @Column(name = "decision_adm_status_id")
    private Long decisionStatusId;


    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_id", insertable = false, updatable = false)
    private Protocol protocol;

    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decision_id", insertable = false, updatable = false)
    private Decision decision;

    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private Region region;

    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", insertable = false, updatable = false)
    private District district;

    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private Article article;

    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_part_id", insertable = false, updatable = false)
    private ArticlePart articlePart;

    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_violation_type_id", insertable = false, updatable = false)
    private ArticleViolationType articleViolationType;

    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decision_type_id", insertable = false, updatable = false)
    private DecisionType decisionType;

    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "punishment_type_id", insertable = false, updatable = false)
    private PunishmentType punishmentType;

    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decision_adm_status_id", insertable = false, updatable = false)
    private AdmStatus decisionStatus;

    private Repeatability(User user, ViolationListView violation) {
        this.userId = user.getId();

        this.fromProtocolId = violation.getId();
        this.protocolSeries = violation.getProtocolSeries();
        this.protocolNumber = violation.getProtocolNumber();
        this.violationTime = violation.getViolationTime();
        this.violatorFio = FioUtils.buildFullFio(violation.getViolatorFirstName(), violation.getViolatorSecondName(), violation.getViolatorLastName());
        this.violatorBirthDate = violation.getViolatorBirthDate();
        this.fromDecisionId = violation.getDecisionId();
        this.decisionSeries = violation.getDecisionSeries();
        this.decisionNumber = violation.getDecisionNumber();
        this.decisionStatusId = violation.getDecisionStatusId();
        this.resolutionTime = violation.getResolutionTime();
        this.regionId = violation.getProtocolRegionId();
        this.districtId = violation.getProtocolDistrictId();
        this.articleId = violation.getProtocolArticleId();
        this.articlePartId = violation.getProtocolArticlePartId();
        this.articleViolationTypeId = violation.getProtocolArticleViolationTypeId();
        this.decisionTypeId = violation.getDecisionTypeId();
        this.punishmentTypeId = violation.getMainPunishmentTypeId();
        this.punishmentAmount = violation.getMainPunishmentAmountText();
    }

    public Repeatability(User user, Protocol protocol, ViolationListView violation) {
        this(user, violation);
        this.protocolId = protocol.getId();
        this.decisionId = null;
    }

    public Repeatability(User user, Decision decision, ViolationListView violation) {
        this(user, violation);
        this.decisionId = decision.getId();
        this.protocolId = null;
    }
}
