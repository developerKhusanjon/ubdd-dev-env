package uz.ciasev.ubdd_service.entity.court;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.court.CourtReturnReason;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatus;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;


@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class CourtFields {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Boolean isActive = true;

    @Getter
    @Setter
    private Long claimId;

    @Getter
    @ManyToOne
    @JoinColumn(name = "court_status_id")
    private CourtStatus courtStatus;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "information_court_status_id")
    private CourtStatus informationCourtStatus;

    @Getter
    @Setter
    private String registrationNumber;

    @Getter
    @Setter
    private LocalDate registrationDate;

    @Getter
    private Long regionId;

    @Getter
    private Long districtId;

    @Getter
    @Setter
    private Long instance;

    @Getter
    @Setter
    private String judgeInfo;

    @Getter
    @Setter
    private String caseNumber;

    @Getter
    @Setter
    private LocalDateTime hearingDate;

    @Getter
    @Setter
    private Boolean isVccUsed;

    @Getter
    @Setter
    private Boolean isProtest;

    // этот процес был передан в другой суд
    @Getter
    @Setter
    private Long movedToClaimId;

    // этот процес яляеться пересмотром другого процесса
    @Getter
    @Setter
    private Long reviewFromClaimId;

    // этот процес окончлся возвратом судьи
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "court_return_reason_id")
    private CourtReturnReason courtReturnReason;

    // этот процес окончился вынесением постановления
    @Getter
    private Long resolutionId;

    public Long getCourtStatusId() {
        if (courtStatus == null) return null;
        return courtStatus.getId();
    }

    public Long getCourtReturnReasonId() {
        if (courtReturnReason == null) return null;
        return courtReturnReason.getId();
    }

    public void setCourtStatus(CourtStatus courtStatus) {
        this.courtStatus = courtStatus;
        this.informationCourtStatus = courtStatus;
    }

    public void setRegion(Region region) {
        this.regionId = Optional.ofNullable(region).map(Region::getId).orElse(null);
    }

    public void setDistrict(District district) {
        this.districtId = Optional.ofNullable(district).map(District::getId).orElse(null);
    }

    public void setResolution(Resolution resolution) {
        resolutionId = resolution.getId();
    }


//    @Builder.Default
//    private Boolean isPaused = false;

//    // этот процес был обьеденен в другой процес
//    private Long margetToClaimId;

//    // этот процес образовался в результате выделения нарушителей из другого процесса
//    private Long separatedFromClaimId;
}