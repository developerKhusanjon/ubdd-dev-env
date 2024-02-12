package uz.ciasev.ubdd_service.entity.trans.mib;

import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.MibTransGeographyCreateDTOI;
import uz.ciasev.ubdd_service.entity.trans.AbstractTransEntity;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "mib_trans_geography")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MibTransGeography extends AbstractTransEntity {

    @Getter
    @ManyToOne
    @JoinColumn(name = "internal_region_id")
    private Region region;

    @Getter
    @ManyToOne
    @JoinColumn(name = "internal_district_id")
    private District district;

    @Getter
    private Long externalId;

    @Getter
    private Boolean isAvailableForSendMibExecutionCard = false;

    @Getter
    private Boolean isAvailableForMibProtocolRegistration = false;

    //    JPA AND CRITERIA FIELDS

    @Column(name = "internal_region_id", insertable = false, updatable = false)
    private Long regionId;

    @Column(name = "internal_district_id", insertable = false, updatable = false)
    private Long districtId;

    public Long getRegionId() {
        if (region == null) return null;
        return region.getId();
    }

    public Long getDistrictId() {
        if (district == null) return null;
        return district.getId();
    }

    public void construct(MibTransGeographyCreateDTOI request) {
        this.externalId = request.getExternalId();
        this.region = request.getRegion();
        this.district = request.getDistrict();
        this.isAvailableForMibProtocolRegistration = Optional.ofNullable(request.getIsAvailableForMibProtocolRegistration()).orElse(false);
        this.isAvailableForSendMibExecutionCard = Optional.ofNullable(request.getIsAvailableForSendMibExecutionCard()).orElse(false);
    }
}
