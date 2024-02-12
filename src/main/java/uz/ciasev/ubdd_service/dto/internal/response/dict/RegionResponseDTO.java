package uz.ciasev.ubdd_service.dto.internal.response.dict;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.dict.response.DictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.Region;

@Getter
public class RegionResponseDTO extends DictResponseDTO {
    private final Boolean isState;
    private final String serialName;

    public RegionResponseDTO(Region entity) {
        super(entity);
        this.isState = entity.getIsState();
        this.serialName = entity.getSerialName();
    }
}
