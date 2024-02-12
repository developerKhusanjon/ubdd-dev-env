package uz.ciasev.ubdd_service.entity.trans.court;

import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.CourtTransferCreateDTOI;
import uz.ciasev.ubdd_service.entity.trans.AbstractTransEntity;

import javax.persistence.*;


// todo отрефакторить. переименовать в Court. externalId -> id
@Entity
@Table(name = "court_trans_court")
@AllArgsConstructor
@NoArgsConstructor
public class CourtTransfer extends AbstractTransEntity {

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
    private Long internalRegionId;

    @Column(name = "internal_district_id", insertable = false, updatable = false)
    private Long internalDistrictId;

    public Long getRegionId() {
        if (region == null) return null;
        return region.getId();
    }

    public Long getDistrictId() {
        if (district == null) return null;
        return district.getId();
    }

    public void construct(CourtTransferCreateDTOI request) {
        this.externalId = request.getExternalId();
        this.region = request.getRegion();
        this.district = request.getDistrict();
    }
}