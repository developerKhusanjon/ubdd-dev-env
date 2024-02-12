package uz.ciasev.ubdd_service.entity.court;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@Entity
@Builder
@Table(name = "court_case_fields")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class CourtCaseFields implements AdmEntity {

    @Builder.Default
    @Transient
    private EntityNameAlias entityNameAlias = EntityNameAlias.COURT_CASE_FIELDS;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    @JsonProperty(access = READ_ONLY)
    private LocalDateTime createdTime;

    @Column(nullable = false)
    @LastModifiedDate
    @JsonProperty(access = READ_ONLY)
    private LocalDateTime editedTime;

    @Builder.Default
    private boolean isActive = true;

    private Long caseId;
    private Long claimId;

//    private String regNumber;
//    private LocalDate regDate;
//
//    private LocalDate declinedDate;
//    @Type(type = "jsonb")
//    @Column(columnDefinition = "jsonb")
//    @Basic(fetch = FetchType.LAZY)
//    private List<Long> declinedReasons;

    @ManyToOne
    @JoinColumn(name = "court_status_id")
    private CourtStatus status;

    private Long instance;
    private LocalDateTime hearingDate;
    private boolean isProtest;
    private boolean useVcc;
    private String caseNumber;
    private String judge;

    private Long caseMergeId;
    private Long caseReviewId;
    private Long caseSeparationClaimId;

    private Long returnReason;

    private Long regionId;
    private Long districtId;

    @Builder.Default
    private Boolean isPaused = false;

    @Column(name = "court_status_id", insertable = false, updatable = false)
    private Long statusId;
}