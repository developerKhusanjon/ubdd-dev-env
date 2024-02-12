package uz.ciasev.ubdd_service.dto.internal.response.dict.ubdd;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.dict.response.DictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDVehicleOwnerType;

@Getter
public class UBDDVehicleOwnerTypeResponseDTO extends DictResponseDTO {
    private final Boolean isJuridic;

    public UBDDVehicleOwnerTypeResponseDTO(UBDDVehicleOwnerType entity) {
        super(entity);
        this.isJuridic = entity.getIsJuridic();
    }
}
