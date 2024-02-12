package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.requests.UBDDVehicleOwnerTypeCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDVehicleOwnerType;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class UBDDVehicleOwnerTypeCreateRequestDTO extends UBDDVehicleOwnerTypeUpdateRequestDTO implements UBDDVehicleOwnerTypeCreateDTOI, DictCreateRequest<UBDDVehicleOwnerType> {

    @NotNull(message = ErrorCode.ID_REQUIRED)
    private Long id;

    @Override
    public void applyToNew(UBDDVehicleOwnerType entity) {
        entity.construct(this);
    }
}
