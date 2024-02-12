package uz.ciasev.ubdd_service.entity.trans.gcp;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import uz.ciasev.ubdd_service.entity.dict.Country;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.gcp.GcpTransDivisionCreateDTOI;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import javax.persistence.*;

@Entity
@Table(name = "gcp_division")
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class GcpTransDivision extends AbstractGcpTransEntity {

    @Getter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private MultiLanguage name;

    @Getter
    private String address;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;


    // JPA AND CRITERIA FIELDS ONLY

    @Column(name = "country_id", updatable = false, insertable = false)
    private Long countryId;

    @Column(name = "region_id", updatable = false, insertable = false)
    private Long regionId;

    @Column(name = "district_id", updatable = false, insertable = false)
    private Long districtId;

    public Long getCountryId() {
        if (country == null) return null;
        return country.getId();
    }

    public Long getRegionId() {
        if (region == null) return null;
        return region.getId();
    }

    public Long getDistrictId() {
        if (district == null) return null;
        return district.getId();
    }


    public void construct(GcpTransDivisionCreateDTOI request) {
        super.construct(request);
        this.name = request.getName();
        this.address = request.getAddress();
        this.country = request.getCountry();
        this.region = request.getRegion();
        this.district = request.getDistrict();
    }


}
