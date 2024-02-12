package uz.ciasev.ubdd_service.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.dict.Country;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "address_reference_book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class AddressReferenceBook implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String alias;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @ManyToOne
    @JoinColumn(name = "country_id")
    @Getter
    private Country country;

    @ManyToOne
    @JoinColumn(name = "region_id")
    @Getter
    private Region region;

    @ManyToOne
    @JoinColumn(name = "district_id")
    @Getter
    private District district;

    @Getter
    @Setter
    private String address;


    //    JPA AND CRITERIA FIELDS

    @Column(name = "country_id", insertable = false, updatable = false)
    private Long countryId;

    @Column(name = "region_id", insertable = false, updatable = false)
    private Long regionId;

    @Column(name = "district_id", insertable = false, updatable = false)
    private Long districtId;
}
