package uz.ciasev.ubdd_service.dto.internal.response.adm.protocol;

import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.ubdd_data.old_structure.ProtocolUbddDataView;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public abstract class ProtocolUbddDataResponseDTO {

    private String vehicleColor;
    private Long vehicleColorTypeId;
    private String vehicleBrand;
    private String vehicleNumber;
    private String vehicleAdditional;
    private String vehiclePassSeries;
    private String vehiclePassNumber;

    private String vehicleEngineSeries;
    private String vehicleBodySeries;
    private String vehicleChassisSeries;
    private Integer vehicleEngineHorsePower;
    private LocalDate vehicleRegistrationDate;
    private String vehicleOwnerPinpp;
    private String vehicleOwnerInn;
    private String vehicleOwnerDrivingLicenseCategory;

    public ProtocolUbddDataResponseDTO(ProtocolUbddDataView data) {

        // TODO До устарения апи старой убдд структур

        this.vehicleColor = data.getVehicleColor();
        this.vehicleColorTypeId = data.getVehicleColorTypeId();
        this.vehicleBrand = data.getVehicleBrand();
        this.vehicleNumber = data.getVehicleNumber();
        this.vehicleAdditional = data.getVehicleAdditional();
        this.vehiclePassSeries = data.getVehiclePassSeries();
        this.vehiclePassNumber = data.getVehiclePassNumber();
        this.vehicleEngineSeries = data.getVehicleEngineSeries();
        this.vehicleBodySeries = data.getVehicleBodySeries();
        this.vehicleChassisSeries = data.getVehicleChassisSeries();
        this.vehicleEngineHorsePower = data.getVehicleEngineHorsePower();
        this.vehicleRegistrationDate = data.getVehicleRegistrationDate();
        this.vehicleOwnerPinpp = data.getVehicleOwnerPinpp();
        this.vehicleOwnerInn = data.getVehicleOwnerInn();
        this.vehicleOwnerDrivingLicenseCategory = data.getVehicleOwnerDrivingLicenseCategory();
    }
}
