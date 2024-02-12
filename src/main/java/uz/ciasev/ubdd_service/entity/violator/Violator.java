package uz.ciasev.ubdd_service.entity.violator;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.*;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.court.EducationLevel;
import uz.ciasev.ubdd_service.entity.dict.court.HealthStatus;
import uz.ciasev.ubdd_service.entity.dict.court.MaritalStatus;
import uz.ciasev.ubdd_service.entity.dict.court.ViolationRepeatabilityStatus;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.utils.types.ArticlePairJson;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Entity
@Builder(toBuilder = true)
@Table(name = "violator")
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Violator implements AdmEntity, Serializable {

    @Getter
    @Transient
    private final EntityNameAlias entityNameAlias = EntityNameAlias.VIOLATOR;

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "violator_id_seq")
    @SequenceGenerator(name = "violator_id_seq", sequenceName = "violator_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @Getter
    @Builder.Default
    private Boolean isArchived = false;

    @Getter
    private LocalDate archivedDate;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adm_case_id")
    private AdmCase admCase;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actual_address_id")
    private Address actualAddress;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_address_id")
    private Address postAddress;

    @Getter
    @Setter
    private String inn;

    @Getter
    @Setter
    private String mobile;

    @Getter
    @Setter
    private String landline;

    @Getter
    @Setter
    private String photoUri;

    // --- ONLY FOR SEND TO COURT ---

    @Getter
    @Setter
    private Long childrenAmount;

    @Getter
    @Setter
    private Long dependentAmount;

    @Setter
    @ManyToOne
    @JoinColumn(name = "health_status_id")
    private HealthStatus healthStatus;

    @Setter
    @ManyToOne
    @JoinColumn(name = "education_level_id")
    private EducationLevel educationLevel;

    @Setter
    @ManyToOne
    @JoinColumn(name = "marital_status_id")
    private MaritalStatus maritalStatus;

    @Setter
    @ManyToOne
    @JoinColumn(name = "violation_repeatability_status_id")
    private ViolationRepeatabilityStatus violationRepeatabilityStatus;

    @Setter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private List<ArticlePairJson> earlierViolatedArticleParts;

    // --- COURT DATA ---

    @Getter
    @Setter
    private Long defendantId;

    @Getter
    @Setter
    private Long courtReturnReasonId;

    @Getter
    @Setter
    private Boolean isParticipated;

    @Getter
    private Long prosecutorRegionId;

    @Getter
    @Setter
    @Builder.Default
    private boolean notificationViaSms = false;

    @Getter
    @Setter
    @Builder.Default
    private boolean notificationViaMail = false;

    @Getter
    @Setter
    @Column(insertable = false, updatable = false)
    private Long mergedToViolatorId;

    @Getter
    @Setter
    @Column(insertable = true, updatable = false)
    private Long separatedFromViolatorId;


    // JPA AND CRITERIA ONLY FIELDS

    @Column(name = "adm_case_id", insertable = false, updatable = false)
    private Long admCaseId;

    @Column(name = "person_id", insertable = false, updatable = false)
    private Long personId;

    @Column(name = "actual_address_id", insertable = false, updatable = false)
    private Long actualAddressId;

    @Column(name = "post_address_id", insertable = false, updatable = false)
    private Long postAddressId;

    @Column(name = "health_status_id", insertable = false, updatable = false)
    private Long healthStatusId;

    @Column(name = "education_level_id", insertable = false, updatable = false)
    private Long educationLevelId;

    @Column(name = "marital_status_id", insertable = false, updatable = false)
    private Long maritalStatusId;

    @Column(name = "violation_repeatability_status_id", insertable = false, updatable = false)
    private Long violationRepeatabilityStatusId;

    @OneToMany(mappedBy = "violator", fetch = FetchType.LAZY)
    private Set<ViolatorDetail> violatorDetails;

    @OneToMany(mappedBy = "violator", fetch = FetchType.LAZY)
    private Set<Decision> decisions;

    public AdmCase getAdmCaseOnlyForSetInEntityForSaving() {
        return admCase;
    }

    public void setProsecutorRegion(Region prosecutorRegion) {
        this.prosecutorRegionId = Optional.ofNullable(prosecutorRegion).map(Region::getId).orElse(null);
    }

    public Long getAdmCaseId() {
        if (admCase == null) return null;
        return admCase.getId();
    }

    public Long getPersonId() {
        if (person == null) return null;
        return person.getId();
    }

    public Long getActualAddressId() {
        if (actualAddress == null) return null;
        return actualAddress.getId();
    }

    public Long getPostAddressId() {
        if (postAddress == null) return null;
        return postAddress.getId();
    }

    public Long getHealthStatusId() {
        if (healthStatus == null) return null;
        return healthStatus.getId();
    }

    public Long getEducationLevelId() {
        if (educationLevel == null) return null;
        return educationLevel.getId();
    }

    public Long getMaritalStatusId() {
        if (maritalStatus == null) return null;
        return maritalStatus.getId();
    }

    public Long getViolationRepeatabilityStatusId() {
        if (violationRepeatabilityStatus == null) return null;
        return violationRepeatabilityStatus.getId();
    }



//    @OneToMany(mappedBy = "violator")
//    private Set<Decision> decisions;

    public List<ArticlePairJson> getEarlierViolatedArticleParts() {
        return this.earlierViolatedArticleParts == null
                ? List.of()
                : this.earlierViolatedArticleParts;
    }

    public void cleanForCopySave() {
        this.id = null;
        this.violatorDetails = null;
        this.decisions = null;
    }
}
