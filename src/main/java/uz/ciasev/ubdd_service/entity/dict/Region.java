package uz.ciasev.ubdd_service.entity.dict;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.requests.RegionDTOI;
import uz.ciasev.ubdd_service.utils.deserializer.dict.RegionCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_region")
@NoArgsConstructor
@JsonDeserialize(using = RegionCacheDeserializer.class)
public class Region extends AbstractEmiDict {

    public Region(Long id) {
       super(id);
    }

    @Getter
    private Boolean isState;

    @Getter
    private String serialName;

    public void construct(RegionDTOI request) {
        super.construct(request);
        set(request);
    }

    public void update(RegionDTOI request) {
        super.update(request);
        set(request);
    }

    private void set(RegionDTOI request) {
        this.isState = request.getIsState();
        this.serialName = request.getSerialName();
    }
}