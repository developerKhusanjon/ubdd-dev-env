package uz.ciasev.ubdd_service.dto.internal.response.adm.protocol;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.history.EditProtocolVehicleNumberRegistration;

import java.time.LocalDateTime;

@Data
public class EditProtocolVehicleNumberRegistrationResponseDTO {

    private LocalDateTime createdTime;
    private Long userId;
    private Long protocolId;
    private String fromVehicleNumber;
    private String toVehicleNumber;
    //private String changeReason;
    private Long changeReasonId;

    public EditProtocolVehicleNumberRegistrationResponseDTO(EditProtocolVehicleNumberRegistration registration) {

        this.createdTime = registration.getCreatedTime();
        this.userId = registration.getUserId();
        this.protocolId = registration.getProtocolId();
        this.fromVehicleNumber = registration.getFromVehicleNumber();
        this.toVehicleNumber = registration.getToVehicleNumber();
        this.changeReasonId = registration.getChangeReason().getId();
    }
}
