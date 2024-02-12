package uz.ciasev.ubdd_service.entity.trans.court;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.CourtTransDistrictCreateDTOI;
import uz.ciasev.ubdd_service.entity.trans.AbstractTransEntity;

import javax.persistence.*;

@Entity
@Table(name = "court_trans_district")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CourtTransDistrict extends AbstractTransEntity {

    @Getter
    private Long externalId;

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
        if (this.region == null) return null;
        return this.region.getId();
    }

    public Long getDistrictId() {
        if (this.district == null) return null;
        return this.district.getId();
    }

    public void construct(CourtTransDistrictCreateDTOI request) {
        this.externalId = request.getExternalId();
        this.region = request.getRegion();
        this.district = request.getDistrict();
    }
}