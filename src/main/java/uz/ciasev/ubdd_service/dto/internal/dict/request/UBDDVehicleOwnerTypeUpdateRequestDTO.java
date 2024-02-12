package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.requests.UBDDVehicleOwnerTypeUpdateDTOI;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDVehicleOwnerType;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class UBDDVehicleOwnerTypeUpdateRequestDTO extends ExternalDictUpdateRequestDTO<UBDDVehicleOwnerType> implements UBDDVehicleOwnerTypeUpdateDTOI {

    @NotNull(message = ErrorCode.IS_JURIDIC_REQUIRED)
    private Boolean isJuridic;

    @Override
    public void applyToOld(UBDDVehicleOwnerType entity) {
        entity.update(this);
    }
}
