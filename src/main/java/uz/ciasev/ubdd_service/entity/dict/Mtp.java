package uz.ciasev.ubdd_service.entity.dict;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.requests.DictUpdateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.MtpCreateDTOI;
import uz.ciasev.ubdd_service.utils.deserializer.dict.MtpCacheDeserializer;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "d_mtp")
@NoArgsConstructor
@JsonDeserialize(using = MtpCacheDeserializer.class)
public class Mtp extends AbstractEmiDict {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;


    //    JPA AND CRITERIA FIELDS

    @Column(name = "district_id", insertable = false, updatable = false)
    private Long districtId;

    public Long getDistrictId() {
        return district.getId();
    }

    public boolean isPartOfDistrict(District district) {
        if (district == null) return false;

        return Objects.equals(
                this.getDistrictId(),
                district.getId()
        );
    }

    public void construct(MtpCreateDTOI request) {
        super.construct(request);
        this.district = request.getDistrict();
    }

    public void update(DictUpdateDTOI request) {
        super.update(request);
    }
}
