package uz.ciasev.ubdd_service.dto.internal.request.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDVehicleColorType;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolUbddData;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class ProtocolUbddDataRequestDTO {


    // Поля остаються

    @Size(max = 4000, message = ErrorCode.UBDD_VEHICLE_ADDITIONAL_MIN_MAX_LENGTH)
    private String vehicleAdditional;



    // Старые поля, сохраняться в бинд сущности

    @Size(max = 128, message = ErrorCode.UBDD_VEHICLE_COLOR_MIN_MAX_LENGTH)
    private String vehicleColor;

    @ActiveOnly(message = ErrorCode.UBDD_VEHICLE_COLOR_TYPE_DEACTIVATED)
    @JsonProperty(value = "vehicleColorTypeId")
    private UBDDVehicleColorType vehicleColorType;

    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_COLOR_TYPE_MIN_MAX_LENGTH)
    @NotNull(message = ErrorCode.UBDD_VEHICLE_BRAND_REQUIRED)
    private String vehicleBrand;

    @Size(max = 20, message = ErrorCode.UBDD_VEHICLE_BRAND_MIN_MAX_LENGTH)
    @NotNull(message = ErrorCode.UBDD_VEHICLE_NUMBER_REQUIRED)
    private String vehicleNumber;

    @Size(max = 10, message = ErrorCode.UBDD_VEHICLE_PASS_SERIES_MIN_MAX_LENGTH)
    private String vehiclePassSeries;

    @Size(max = 20, message = ErrorCode.UBDD_VEHICLE_PASS_NUMBER_MIN_MAX_LENGTH)
    private String vehiclePassNumber;

    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_ENGINE_SERIES_MIN_MAX_LENGTH)
    private String vehicleEngineSeries;

    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_BODY_SERIES_MIN_MAX_LENGTH)
    private String vehicleBodySeries;

    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_CHASSIS_SERIES_MIN_MAX_LENGTH)
    private String vehicleChassisSeries;

    private Integer vehicleEngineHorsePower;

    private LocalDate vehicleRegistrationDate;

    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_OWNER_PINPP_MIN_MAX_LENGTH)
    private String vehicleOwnerPinpp;

    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_OWNER_INN_MIN_MAX_LENGTH)
    private String vehicleOwnerInn;

    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_OWNER_DRIVING_LICENSE_CATEGORY_MIN_MAX_LENGTH)
    private String vehicleOwnerDrivingLicenseCategory;

    public ProtocolUbddData build(Protocol protocol) {
        ProtocolUbddData rsl = new ProtocolUbddData();
        return apply(rsl, protocol);
    }

    public ProtocolUbddData apply(ProtocolUbddData data, Protocol protocol) {

        data.setProtocol(protocol);
//        data.setVehicleColor(this.vehicleColor);
//        data.setVehicleColorType(this.vehicleColorType);
//        data.setVehicleBrand(this.vehicleBrand);
//        data.setVehicleNumber(this.vehicleNumber);
        data.setVehicleAdditional(this.vehicleAdditional);
//        data.setVehiclePassSeries(this.vehiclePassSeries);
//        data.setVehiclePassNumber(this.vehiclePassNumber);
//
//        data.setVehicleEngineSeries(this.vehicleEngineSeries);
//        data.setVehicleBodySeries(this.vehicleBodySeries);
//        data.setVehicleChassisSeries(this.vehicleChassisSeries);
//        data.setVehicleEngineHorsePower(this.vehicleEngineHorsePower);
//        data.setVehicleRegistrationDate(this.vehicleRegistrationDate);
//        data.setVehicleOwnerPinpp(this.vehicleOwnerPinpp);
//        data.setVehicleOwnerInn(this.vehicleOwnerInn);
//        data.setVehicleOwnerDrivingLicenseCategory(this.vehicleOwnerDrivingLicenseCategory);
        return data;
    }
}
