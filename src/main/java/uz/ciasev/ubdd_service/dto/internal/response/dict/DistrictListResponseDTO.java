package uz.ciasev.ubdd_service.dto.internal.response.dict;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.dict.response.DictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.District;

@Getter
public class DistrictListResponseDTO extends DictResponseDTO {
    private final Long regionId;

    public DistrictListResponseDTO(District entity) {
        super(entity);
        this.regionId = entity.getRegionId();
    }
}
