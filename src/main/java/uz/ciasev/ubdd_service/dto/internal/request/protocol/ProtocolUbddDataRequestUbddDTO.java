package uz.ciasev.ubdd_service.dto.internal.request.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.dto.internal.request.AddressRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.ubdd.*;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolUbddData;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnlyList;
import uz.ciasev.ubdd_service.utils.validator.ValidAddress;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class ProtocolUbddDataRequestUbddDTO extends ProtocolUbddDataRequestDTO implements ProtocolDataDTO<ProtocolUbddData> {


    // Поля остаются
    @DecimalMax(value = "999.99", message = ErrorCode.RADAR_SPEED_MAX_LENGTH)
    private Float radarSpeed;

    @DecimalMax(value = "999.99", message = ErrorCode.MAX_SPEED_MAX_LENGTH)
    private Float maxSpeed;

    private Boolean isAttorney;

    @ActiveOnlyList(message = ErrorCode.UBDD_CONFISCATED_DOCUMENT_DEACTIVATED)
    @JsonProperty(value = "confiscatedCategoriesId")
    private List<UBDDConfiscatedCategory> confiscatedCategories;


    // Старые поля
    // TODO До устарения апи старой убдд структур

    @ActiveOnly(message = ErrorCode.UBDD_GROUP_DEACTIVATED)
    @JsonProperty(value = "groupId")
    private UBDDGroup group;

    @Size(max = 100, message = ErrorCode.VIOLATION_POINT_MAX_LENGTH)
    private String violationPoint;

    @ActiveOnly(message = ErrorCode.UBDD_IMPOUND_DEACTIVATED)
    @JsonProperty(value = "impoundId")
    private UBDDImpound impound;

    @Size(max = 10, message = ErrorCode.REG_NUMBER_MAX_LENGTH)
    private String regNumber;

    @ActiveOnly(message = ErrorCode.UBDD_VEHICLE_BODY_TYPE_DEACTIVATED)
    @JsonProperty(value = "vehicleBodyTypeId")
    private UBDDVehicleBodyType vehicleBodyType;

    @ActiveOnly(message = ErrorCode.UBDD_VEHICLE_OWNER_DEACTIVATED)
    @JsonProperty(value = "vehicleOwnerId")
    private UBDDVehicleOwnerType vehicleOwner;

    @ActiveOnly(message = ErrorCode.UBDD_VEHICLE_MINISTRY_DEACTIVATED)
    @JsonProperty(value = "vehicleMinistryId")
    private UBDDVehicleMinistry vehicleMinistry;

    @ValidAddress(message = ErrorCode.UBDD_REGISTRATION_ADDRESS_INVALID)
    private AddressRequestDTO registrationAddress;

    @Size(max = 50, message = ErrorCode.INSURANCE_NUMBER_MAX_LENGTH)
    private String insuranceNumber;

    @Size(max = 128, message = ErrorCode.VEHICLE_BRAND_TYPE_MAX_LENGTH)
    private String vehicleBrandType;

    @Size(max = 10, message = ErrorCode.ATTORNEY_SERIES_MAX_LENGTH)
    private String attorneySeries;

    @Size(max = 20, message = ErrorCode.ATTORNEY_NUMBER_MAX_LENGTH)
    private String attorneyNumber;

//    @ActiveOnly(message = ErrorCode.UBDD_CONFISCATED_DOCUMENT_DEACTIVATED)
    @JsonProperty(value = "confiscatedDocumentId")
    private UBDDConfiscatedCategory confiscatedDocument;

    @Size(max = 10, message = ErrorCode.DRIVING_LICENSE_SERIES_MAX_LENGTH)
    private String drivingLicenseSeries;

    @Size(max = 20, message = ErrorCode.DRIVING_LICENSE_NUMBER_MAX_LENGTH)
    private String drivingLicenseNumber;

    @Override
    public ProtocolUbddData build(Protocol protocol) {
        ProtocolUbddData rsl = super.build(protocol);
        return apply(rsl, protocol);
    }

    @Override
    public ProtocolUbddData apply(ProtocolUbddData data, Protocol protocol) {
        ProtocolUbddData rsl = super.apply(data, protocol);
        //rsl.setGroup(this.group);
//        rsl.setViolationPoint(this.violationPoint);
//        rsl.setImpound(this.impound);
//        rsl.setRegNumber(this.regNumber);
        rsl.setRadarSpeed(this.radarSpeed);
        rsl.setMaxSpeed(this.maxSpeed);
//        rsl.setVehicleBodyType(this.vehicleBodyType);
//        rsl.setVehicleOwner(this.vehicleOwner);
//        rsl.setVehicleMinistry(this.vehicleMinistry);
//        rsl.setInsuranceNumber(this.insuranceNumber);
//        rsl.setVehicleBrandType(this.vehicleBrandType);
        rsl.setIsAttorney(this.isAttorney);
//        rsl.setAttorneySeries(this.attorneySeries);
//        rsl.setAttorneyNumber(this.attorneyNumber);
//        rsl.setConfiscatedDocument(this.confiscatedDocument);


        if (this.confiscatedCategories == null) {
            Optional.ofNullable(this.confiscatedDocument).map(List::of).ifPresent(rsl::setConfiscatedCategories);
        } else {
            rsl.setConfiscatedCategories(this.confiscatedCategories);
        }

//        rsl.setDrivingLicenseSeries(this.drivingLicenseSeries);
//        rsl.setDrivingLicenseNumber(this.drivingLicenseNumber);
//        Optional.ofNullable(this.registrationAddress).map(AddressRequestDTO::buildAddress).ifPresent(rsl::setRegistrationAddress);
        return rsl;
    }
}
