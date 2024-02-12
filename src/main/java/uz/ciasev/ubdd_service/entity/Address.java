package uz.ciasev.ubdd_service.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.dict.Country;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.utils.FormatUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "address")
@NoArgsConstructor
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
@EntityListeners(AuditingEntityListener.class)
public class Address implements Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_id_seq")
    @SequenceGenerator(name = "address_id_seq", sequenceName = "address_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @Getter
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;

    @Getter
    @Setter
    private String fullAddressText;


    //    JPA AND CRITERIA FIELDS

    @Column(name = "country_id", insertable = false, updatable = false)
    private Long countryId;

    @Column(name = "region_id", insertable = false, updatable = false)
    private Long regionId;

    @Column(name = "district_id", insertable = false, updatable = false)
    private Long districtId;

    public Address(Country country, Region region, District district, String address) {
        set(country, region, district, address);
    }

    public void set(Country country, Region region, District district, String address) {
        this.address = address;
        this.country = country;
        this.region = region;
        this.district = district;
        this.fullAddressText = FormatUtils.buildAddressText(country, region, district, address);

    }

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

    public Optional<Long> getRegionIdOpt() {
        return Optional.ofNullable(getRegionId());
    }

    public Optional<Long> getDistrictIdOpt() {
        return Optional.ofNullable(getDistrictId());

    }

    public Optional<Long> getCountryIdOpt() {
        return Optional.ofNullable(getCountryId());
    }

    public boolean isUzbekistan() {
        return Country.isUzbekistan(getCountryId());
    }
}
