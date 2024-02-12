package uz.ciasev.ubdd_service.entity.settings;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.dict.*;
import uz.ciasev.ubdd_service.service.settings.OrganInfoDescription;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "organ_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class OrganInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organ_info_id_seq")
    @SequenceGenerator(name = "organ_info_id_seq", sequenceName = "organ_info_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    @Getter
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

    @Column(name = "organ_id")
    @Getter
    private Long organId;

    @Column(name = "department_id")
    @Getter
    private Long departmentId;

    @Column(name = "region_id")
    @Getter
    private Long regionId;

    @Column(name = "district_id")
    @Getter
    private Long districtId;

    @Getter
    private String address;

    @Getter
    private String landline;

    @Getter
    private String postIndex;


    // JPA AND CRITERIA FIELDS ONLY

    @ManyToOne
    @JoinColumn(name = "organ_id", insertable = false, updatable = false)
    private Organ organ;

    @ManyToOne
    @JoinColumn(name = "department_id", insertable = false, updatable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private Region region;

    @ManyToOne
    @JoinColumn(name = "district_id", insertable = false, updatable = false)
    private District district;


    public OrganInfo(Organ organ, OrganInfoDescription descriptiveFields) {
        this(organ.getId(), null, null, null, descriptiveFields);
    }

    public OrganInfo(Department department, OrganInfoDescription descriptiveFields) {
        this(department.getOrganId(), department.getId(), null, null, descriptiveFields);
    }

    public OrganInfo(Organ organ, Region region, OrganInfoDescription descriptiveFields) {
        this(organ.getId(), null, region.getId(), null, descriptiveFields);
    }

    public OrganInfo(Organ organ, District district, OrganInfoDescription descriptiveFields) {
        this(organ.getId(), null, district.getRegionId(), district.getId(), descriptiveFields);
    }

    public OrganInfo(Department department, Region region, OrganInfoDescription descriptiveFields) {
        this(department.getOrganId(), department.getId(), region.getId(), null, descriptiveFields);
    }

    public OrganInfo(Department department, District district, OrganInfoDescription descriptiveFields) {
        this(department.getOrganId(), department.getId(), district.getRegionId(), district.getId(), descriptiveFields);
    }

    public OrganInfo(Organ organ, Department department, Region region, District district, OrganInfoDescription descriptiveFields) {
        this(
            organ.getId(),
            Optional.ofNullable(department).map(Department::getId).orElse(null),
            Optional.ofNullable(region).map(Region::getId).orElse(null),
            Optional.ofNullable(district).map(District::getId).orElse(null),
            descriptiveFields
        );
    }

    private OrganInfo(Long organId, Long departmentId, Long regionId, Long districtId, OrganInfoDescription descriptiveFields) {
        this.organId = organId;
        this.departmentId = departmentId;
        this.regionId = regionId;
        this.districtId = districtId;
        this.setDescriptiveFields(descriptiveFields);
    }

    public void setDescriptiveFields(OrganInfoDescription descriptiveFields) {
        this.address = descriptiveFields.getAddress();
        this.landline = descriptiveFields.getLandline();
        this.postIndex = descriptiveFields.getPostIndex();
    }
}
