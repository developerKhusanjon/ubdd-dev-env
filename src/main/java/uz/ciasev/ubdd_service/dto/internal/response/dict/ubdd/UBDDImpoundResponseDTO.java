package uz.ciasev.ubdd_service.dto.internal.response.dict.ubdd;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.dict.response.DictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDImpound;

@Getter
public class UBDDImpoundResponseDTO extends DictResponseDTO {

    private final Long vehicleArrestPlaceId;

    public UBDDImpoundResponseDTO(UBDDImpound entity) {
        super(entity);
        this.vehicleArrestPlaceId = entity.getVehicleArrestPlaceId();
    }
}
