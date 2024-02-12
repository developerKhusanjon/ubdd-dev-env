package uz.ciasev.ubdd_service.entity.dict;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.requests.DistrictCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.DistrictUpdateDTOI;
import uz.ciasev.ubdd_service.utils.deserializer.dict.DistrictCacheDeserializer;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "d_district")
@NoArgsConstructor
@JsonDeserialize(using = DistrictCacheDeserializer.class)
public class District extends AbstractEmiDict {

    public District(Long id) {
        super(id);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @Getter
    private Boolean isState;

    @Getter
    private String reportName;

    @Getter
    private Boolean isNotDistrict;

    //    JPA AND CRITERIA FIELDS

    @Column(name = "region_id", insertable = false, updatable = false)
    private Long regionId;

    public Long getRegionId() {
        return region.getId();
    }

    public boolean isPartOfRegion(Region region) {
        if (region == null) return false;

        return Objects.equals(
                this.getRegionId(),
                region.getId()
        );
    }

    public void construct(DistrictCreateDTOI request) {
        super.construct(request);
        set(request);
        this.region = request.getRegion();
    }

    public void update(DistrictUpdateDTOI request) {
        super.update(request);
        set(request);        
    }

    private void set(DistrictUpdateDTOI request) {
        this.isState = request.getIsState();
        this.isNotDistrict = request.getIsNotDistrict();
        this.reportName = request.getReportName();
    }
}
