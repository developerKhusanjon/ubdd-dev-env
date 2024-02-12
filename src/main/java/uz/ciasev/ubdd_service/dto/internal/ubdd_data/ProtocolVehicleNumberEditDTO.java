package uz.ciasev.ubdd_service.dto.internal.ubdd_data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.ChangeReasonType;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ProperUbddDataBind;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ProtocolVehicleNumberEditDTO {

    @NotBlank(message = ErrorCode.UBDD_VEHICLE_NUMBER_REQUIRED)
    @Size(max = 20, message = ErrorCode.VEHICLE_NUMBER_MIN_MAX_SIZE)
    private String newVehicleNumber;

    @NotNull(message = ErrorCode.CHANGE_REASON_REQUIRED)
    //private String changeReason;
    @JsonProperty(value = "changeReasonId")
    private ChangeReasonType changeReason;

    @NotNull(message = ErrorCode.UBDD_DATA_BIND_REQUIRED)
    @ProperUbddDataBind(message = ErrorCode.UBDD_DATA_BIND_REQUIRED)
    private UbddDataToProtocolBindInternalDTO ubddDataBind;
}
