package uz.ciasev.ubdd_service.dto.internal.request.violation_event;

import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.MultiSystemTechPassIdRequestDTO;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ViolationEventDecisionRequestDTO implements MultiSystemTechPassIdRequestDTO {

    @NotNull(message = ErrorCode.VIOLATION_EVENT_ID_REQUIRED)
    private Long violationEventId;

    @NotBlank(message = ErrorCode.VEHICLE_NUMBER_REQUIRED)
    @Size(min = 3, max = 20, message = ErrorCode.VEHICLE_NUMBER_MIN_MAX_SIZE)
    private String vehicleNumber;

    private Long vehicleId;

    private Long customsEventId;
}
