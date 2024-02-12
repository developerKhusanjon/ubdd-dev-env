package uz.ciasev.ubdd_service.entity.trans.court;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.Country;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.CourtTransGeographyCreateDTOI;
import uz.ciasev.ubdd_service.entity.trans.AbstractTransEntity;

import javax.persistence.*;

@Entity
@Table(name = "court_trans_geography")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CourtTransGeography extends AbstractTransEntity {

    @Getter
    private Long externalCountryId;

    @Getter
    private Long externalRegionId;

    @Getter
    private Long externalDistrictId;

    @Getter
    @ManyToOne
    @JoinColumn(name = "internal_country_id")
    private Country country;

    @Getter
    @ManyToOne
    @JoinColumn(name = "internal_region_id")
    private Region region;

    @Getter
    @ManyToOne
    @JoinColumn(name = "internal_district_id")
    private District district;


    //    JPA AND CRITERIA FIELDS

    @Column(name = "internal_country_id", updatable = false, insertable = false)
    private Long countryId;

    @Column(name = "internal_region_id", updatable = false, insertable = false)
    private Long regionId;

    @Column(name = "internal_district_id", updatable = false, insertable = false)
    private Long districtId;

    public Long getCountryId() {
        if (this.country == null) return null;
        return this.country.getId();
    }

    public Long getRegionId() {
        if (this.region == null) return null;
        return this.region.getId();
    }

    public Long getDistrictId() {
        if (this.district == null) return null;
        return this.district.getId();
    }

    public void construct(CourtTransGeographyCreateDTOI request) {
        this.externalCountryId = request.getExternalCountryId();
        this.externalRegionId = request.getExternalRegionId();
        this.externalDistrictId = request.getExternalDistrictId();
        this.country = request.getCountry();
        this.region = request.getRegion();
        this.district = request.getDistrict();
    }
}