package uz.ciasev.ubdd_service.entity.trans.mail;

import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.MailTransGeographyCreateDTOI;
import uz.ciasev.ubdd_service.entity.trans.AbstractTransEntity;

import javax.persistence.*;

@Entity
@Table(name = "mail_trans_geography")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MailTransGeography extends AbstractTransEntity {

    @Getter
    private Long externalRegionId;

    @Getter
    private Long externalDistrictId;

    @Getter
    @ManyToOne
    @JoinColumn(name = "internal_region_id")
    private Region region;

    @Getter
    @ManyToOne
    @JoinColumn(name = "internal_district_id")
    private District district;


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

    public void construct(MailTransGeographyCreateDTOI request) {
        this.externalRegionId = request.getExternalRegionId();
        this.externalDistrictId = request.getExternalDistrictId();
        this.region = request.getRegion();
        this.district = request.getDistrict();
    }
}
