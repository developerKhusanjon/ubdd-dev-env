package uz.ciasev.ubdd_service.dto.internal.ubdd_data;

import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.response.AddressResponseDTO;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddTexPassData;

import java.time.LocalDate;
import java.util.Optional;

@Data
public class UbddTexPassResponseDTO {

    private Long id;

    // OWNER
    private Long vehicleOwnerTypeId;
    private String vehicleOwnerInn;
    private String vehicleOwnerOrganizationName;
    private String vehicleOwnerLastName;
    private String vehicleOwnerFirstName;
    private String vehicleOwnerSecondName;
    private LocalDate vehicleOwnerBirthDate;
    private String vehicleOwnerPinpp;

    // VEHICLE
    private String vehicleNumber;
    private String vehicleColor;
    private String vehicleSubColor;
    private Long vehicleBodyTypeId;
    private String vehicleBrand;
    private String vehicleModel;
    private String vehicleEngineSeries;
    private Integer vehicleEngineHorsePower;
    private LocalDate vehicleRegistrationDate;
    private String vehicleChassisSeries;
    private String texPassSeries;
    private String texPassNumber;
    private Integer vehicleYear;
    private Long vehicleId;
    private String vehicleBodySeries;
    private AddressResponseDTO texPassGivenAddress;

    public UbddTexPassResponseDTO(UbddTexPassData entity) {

        this.id = entity.getId();

        // OWNER
        this.vehicleOwnerTypeId = entity.getVehicleOwnerTypeId();
        this.vehicleOwnerInn = entity.getVehicleOwnerInn();
        this.vehicleOwnerOrganizationName = entity.getVehicleOwnerOrganizationName();
        this.vehicleOwnerLastName = entity.getVehicleOwnerLastName();
        this.vehicleOwnerFirstName = entity.getVehicleOwnerFirstName();
        this.vehicleOwnerSecondName = entity.getVehicleOwnerSecondName();
        this.vehicleOwnerBirthDate = entity.getVehicleOwnerBirthDate();
        this.vehicleOwnerPinpp = entity.getVehicleOwnerPinpp();

        // VEHICLE
        this.vehicleNumber = entity.getVehicleNumber();
        this.vehicleColor = entity.getVehicleColor();
        this.vehicleSubColor = entity.getVehicleColor();
        this.vehicleBodyTypeId = entity.getVehicleBodyTypeId();
        this.vehicleBrand = entity.getVehicleBrand();
        this.vehicleModel = entity.getVehicleModel();
        this.vehicleEngineSeries = entity.getVehicleEngineSeries();
        this.vehicleEngineHorsePower = entity.getVehicleEngineHorsePower();
        this.vehicleRegistrationDate = entity.getVehicleRegistrationDate();
        this.vehicleChassisSeries = entity.getVehicleChassisSeries();
        this.texPassSeries = entity.getTexPassSeries();
        this.texPassNumber = entity.getTexPassNumber();
        this.vehicleYear = entity.getVehicleYear();
        this.vehicleId = entity.getVehicleId();
        this.vehicleBodySeries = entity.getVehicleBodySeries();
        this.texPassGivenAddress = Optional.ofNullable(entity.getTexPassGivenAddress()).map(AddressResponseDTO::new).orElse(null);
    }
}
