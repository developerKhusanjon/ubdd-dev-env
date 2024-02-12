package uz.ciasev.ubdd_service.dto.internal.ubdd_data;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddVehicleInspectionData;

import java.time.LocalDateTime;

@Data
public class UbddVehicleInspectionResponseDTO {

    private final Long id;

    private final String vehicleNumber;

    private final Boolean isInspectionPass;

    private final LocalDateTime inspectionTime;

    private final LocalDateTime nextInspectionTime;

    private final String inspectorInfo;

    private final String additionally;

    public UbddVehicleInspectionResponseDTO(UbddVehicleInspectionData data) {
        this.id = data.getId();
        this.vehicleNumber = data.getVehicleNumber();
        this.isInspectionPass = data.getIsInspectionPass();
        this.inspectionTime = data.getInspectionTime();
        this.nextInspectionTime = data.getNextInspectionTime();
        this.inspectorInfo = data.getInspectorInfo();
        this.additionally = data.getAdditionally();
    }
}
