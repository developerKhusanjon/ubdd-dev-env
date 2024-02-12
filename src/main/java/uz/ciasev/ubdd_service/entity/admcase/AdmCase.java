package uz.ciasev.ubdd_service.entity.admcase;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.Place;
import uz.ciasev.ubdd_service.entity.court.CourtCaseFields;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.court.CourtConsideringAddition;
import uz.ciasev.ubdd_service.entity.dict.court.CourtConsideringBasis;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatus;
import uz.ciasev.ubdd_service.entity.dict.court.ViolationPlaceType;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Builder(toBuilder = true)
@Table(name = "adm_case")
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
@EntityListeners(AuditingEntityListener.class)
public class AdmCase implements AdmEntity, Place, Serializable {

    @Getter
    @Transient
    private final EntityNameAlias entityNameAlias = EntityNameAlias.CASE;

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_case_id_seq")
    @SequenceGenerator(name = "adm_case_id_seq", sequenceName = "adm_case_id_seq", allocationSize = 1)
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
    @Builder.Default
    private boolean isDeleted = false;

    @Getter
    @Setter
    private Long mergedToAdmCaseId;

    @Getter
    private String series;

    @Getter
    private String number;

    @Getter
    private LocalDate openedDate;

    // Кто создал дело
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "adm_status_id")
    private AdmStatus status;

    // Поля определяющие у кого сейчас находиться дело
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consider_user_id")
    private User considerUser;

    @Getter
    @Setter
    private LocalDateTime consideredTime;

    @Getter
    @Setter
    private String considerInfo;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organ_id")
    private Organ organ;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;


    // Поля суда

    @Getter
    @Setter
    private Long claimId;

    @Getter
    @Setter
    private String courtOutNumber;

    @Getter
    @Setter
    private LocalDate courtOutDate;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "court_status_id")
    private CourtStatus courtStatus;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "court_region_id")
    private Region courtRegion;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "court_district_id")
    private District courtDistrict;

    @Getter
    @Setter
    @Builder.Default
    @Column(name = "is_308")
    private Boolean is308 = false;

    @Getter
    @Setter
    private String violationPlaceAddress;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "court_considering_basis_id")
    private CourtConsideringBasis courtConsideringBasis;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "court_considering_addition_id")
    private CourtConsideringAddition courtConsideringAddition;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "violation_place_type_id")
    private ViolationPlaceType violationPlaceType;

    @Getter
    @Setter
    private String fabula;


    // УДОБНЫЕ ПОЛЯ
    // Поля для удобства работы в jpql или criteria. НЕ ИСПОЛЬЗОВАТЬ В КОДЕ

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "admCase", fetch = FetchType.LAZY)
    private Set<Violator> violators;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "admCase", fetch = FetchType.LAZY)
    private Set<Resolution> resolutions;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "caseId", fetch = FetchType.LAZY)
    private Set<CourtCaseFields> courtCaseFields;

    @Column(name = "consider_user_id", insertable = false, updatable = false)
    private Long considerUserId;

    @Column(name = "court_status_id", insertable = false, updatable = false)
    private Long courtStatusId;

    @Column(name = "court_region_id", insertable = false, updatable = false)
    private Long courtRegionId;

    @Column(name = "court_district_id", insertable = false, updatable = false)
    private Long courtDistrictId;

    @Column(name = "court_considering_basis_id", insertable = false, updatable = false)
    private Long courtConsideringBasisId;

    @Column(name = "court_considering_addition_id", insertable = false, updatable = false)
    private Long courtConsideringAdditionId;

    @Column(name = "violation_place_type_id", insertable = false, updatable = false)
    private Long violationPlaceTypeId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Column(name = "adm_status_id", insertable = false, updatable = false)
    private Long statusId;

    @Column(name = "organ_id", insertable = false, updatable = false)
    private Long organId;

    @Column(name = "department_id", insertable = false, updatable = false)
    private Long departmentId;

    @Column(name = "region_id", insertable = false, updatable = false)
    private Long regionId;

    @Column(name = "district_id", insertable = false, updatable = false)
    private Long districtId;

    // ***

    //  Признак того, что дело создано через миграционные эндпоинты гаи (появился только после запуска гаи)
    @Column(insertable = false, updatable = false)
    private Boolean isMigration;

    public boolean getIsMigration() {
        return Boolean.TRUE.equals(isMigration);
    }


    public Long getUserId() {
        if (user == null) return null;
        return user.getId();
    }

    public Long getOrganId() {
        if (organ == null) return null;
        return organ.getId();
    }

    public Long getDepartmentId() {
        if (department == null) return null;
        return department.getId();
    }

    public Long getRegionId() {
        if (region == null) return null;
        return region.getId();
    }

    public Long getDistrictId() {
        if (district == null) return null;
        return district.getId();
    }

    public Long getConsiderUserId() {
        if (considerUser == null) return null;
        return considerUser.getId();
    }

    public Long getCourtStatusId() {
        if (courtStatus == null) return null;
        return courtStatus.getId();
    }

    public Long getStatusId() {
        return status.getId();
    }

    public Long getCourtRegionId() {
        if (courtRegion == null) return null;
        return courtRegion.getId();
    }

    public Long getCourtDistrictId() {
        if (courtDistrict == null) return null;
        return courtDistrict.getId();
    }

    public Long getCourtConsideringBasisId() {
        if (courtConsideringBasis == null) return null;
        return courtConsideringBasis.getId();
    }

    public Long getCourtConsideringAdditionId() {
        if (courtConsideringAddition == null) return null;
        return courtConsideringAddition.getId();
    }

    public Long getViolationPlaceTypeId() {
        if (violationPlaceType == null) return null;
        return violationPlaceType.getId();
    }
}