package uz.ciasev.ubdd_service.entity.court;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatus;
import uz.ciasev.ubdd_service.service.court.CourtCaseChancelleryDataRequest;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Entity
@Table(name = "court_case_chancellery_data")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class CourtCaseChancelleryData implements AdmEntity {

    @Getter
    @Transient
    private final EntityNameAlias entityNameAlias = EntityNameAlias.COURT_CASE_CHANCELLERY_DATA;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false, updatable = false)
    @CreatedDate
    @JsonProperty(access = READ_ONLY)
    private LocalDateTime createdTime;

    @Getter
    @Column(nullable = false)
    @LastModifiedDate
    @JsonProperty(access = READ_ONLY)
    private LocalDateTime editedTime;

    @Getter
    private Long caseId;

    @Getter
    private Long claimId;

    @Getter
    private String registrationNumber;

    @Getter
    private LocalDate registrationDate;

    @Getter
    private LocalDate declinedDate;

    @Getter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private List<Long> declinedReasons;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "court_status_id")
    private CourtStatus status;

    @Getter
    private Long regionId;

    @Getter
    private Long districtId;

    // JPA AND CRITERIA FIELDS ONLY

    @Column(name = "court_status_id", insertable = false, updatable = false)
    private Long statusId;

    public CourtCaseChancelleryData(CourtCaseChancelleryDataRequest request) {
        this.caseId = request.getCaseId();
        this.claimId = request.getClaimId();
        this.setData(request);

    }

    public CourtCaseChancelleryData setData(CourtCaseChancelleryDataRequest request) {
        this.registrationNumber = request.getRegistrationNumber();
        this.registrationDate = request.getRegistrationDate();
        this.declinedDate = request.getDeclinedDate();
        this. declinedReasons = request.getDeclinedReasons();
        this.status = request.getStatus();
        this.regionId = request.getRegionId();
        this.districtId = request.getDistrictId();
        return this;
    }

    public Long getStatusId() {
        if (status == null) return null;
        return status.getId();
    }
}