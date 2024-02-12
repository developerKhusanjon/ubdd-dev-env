package uz.ciasev.ubdd_service.dto.internal.ubdd_data;

import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class UbddVehicleInspectionDTO {

    @Size(max = 20, message = ErrorCode.UBDD_VEHICLE_INSPECTION_VEHICLE_NUMBER_MIN_MAX_SIZE)
    private String vehicleNumber;

    private Boolean isInspectionPass;

    private LocalDateTime inspectionTime;

    private LocalDateTime nextInspectionTime;

    @Size(max = 500, message = ErrorCode.UBDD_VEHICLE_INSPECTION_INSPECTOR_INFO_MIN_MAX_SIZE)
    private String inspectorInfo;

    @Size(max = 500, message = ErrorCode.UBDD_VEHICLE_INSPECTION_ADDITIONALLY_MIN_MAX_SIZE)
    private String additionally;
}
