package uz.ciasev.ubdd_service.dto.internal.request.resolution.manual_material;

import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.OrganPunishmentRequestDTO;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ValidPunishment;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class PunishmentManualMaterialDTO {

    @NotNull(message = ErrorCode.MATERIAL_REQUIRED)
    @Valid
    private ManualCourtMaterialDTO material;

    @NotNull(message = ErrorCode.PUNISHMENT_REQUIRED)
    @ValidPunishment(message = ErrorCode.PUNISHMENT_INVALID)
    @Valid
    private OrganPunishmentRequestDTO punishment;

}
