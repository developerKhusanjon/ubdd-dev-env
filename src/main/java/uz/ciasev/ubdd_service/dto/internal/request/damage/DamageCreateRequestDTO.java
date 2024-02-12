package uz.ciasev.ubdd_service.dto.internal.request.damage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.VictimType;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;
import uz.ciasev.ubdd_service.utils.validator.ValidDamageDetail;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ValidDamageDetail
public class DamageCreateRequestDTO extends DamageRequestDTO {

    @NotNull(message = ErrorCode.PROTOCOL_ID_REQUIRED)
    private Long protocolId;

    @NotNull(message = ErrorCode.VICTIM_TYPE_REQUIRED)
    @ActiveOnly(message = ErrorCode.VICTIM_TYPE_DEACTIVATED)
    @JsonProperty(value = "victimTypeId")
    private VictimType victimType;

    private Long victimId;
}
