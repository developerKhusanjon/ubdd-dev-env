package uz.ciasev.ubdd_service.dto.internal.request.resolution.manual_material;

import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class LicenseReturningManualMaterialDTO {

    @NotNull(message = ErrorCode.MATERIAL_REQUIRED)
    @Valid
    private ManualCourtMaterialDTO material;
}
