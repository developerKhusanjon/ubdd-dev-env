package uz.ciasev.ubdd_service.dto.internal.response.adm.protocol;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.dto.internal.response.AddressResponseDTO;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolUbddData;
import uz.ciasev.ubdd_service.entity.ubdd_data.old_structure.ProtocolUbddDataView;

import java.util.List;
import java.util.Optional;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProtocolUbddDataResponseUbddDTO extends ProtocolUbddDataResponseDTO {

    private Float radarSpeed;
    private Float maxSpeed;
    private Boolean isAttorney;
    private List<Long> confiscatedCategories;


    // TODO До устарения апи старой убдд структур
    // старые опля

    private Long groupId;
    private String violationPoint;
    private Long impoundId;
    private String regNumber;
    private Long vehicleBodyTypeId;
    private Long vehicleOwnerId;
    private Long vehicleMinistryId;
    private AddressResponseDTO registrationAddress;
    private String insuranceNumber;
    private String vehicleBrandType;

    private String attorneySeries;
    private String attorneyNumber;
    private Long confiscatedDocumentId;
    private String drivingLicenseSeries;
    private String drivingLicenseNumber;

    public ProtocolUbddDataResponseUbddDTO(ProtocolUbddData data) {
//        super(data);
        //this.groupId = Optional.ofNullable(data.getGroup()).map(AbstractDict::getId).orElse(null);
//        this.violationPoint = data.getViolationPoint();
//        this.impoundId = Optional.ofNullable(data.getImpound()).map(AbstractDict::getId).orElse(null);
//        this.regNumber = data.getRegNumber();
        this.radarSpeed = data.getRadarSpeed();
        this.maxSpeed = data.getMaxSpeed();
//        this.vehicleBodyTypeId = Optional.ofNullable(data.getVehicleBodyType()).map(AbstractDict::getId).orElse(null);
//        this.vehicleOwnerId = Optional.ofNullable(data.getVehicleOwner()).map(AbstractDict::getId).orElse(null);
//        this.vehicleMinistryId = Optional.ofNullable(data.getVehicleMinistry()).map(AbstractDict::getId).orElse(null);
//        this.registrationAddress = registrationAddress;
//        this.insuranceNumber = data.getInsuranceNumber();
//        this.vehicleBrandType = data.getVehicleBrandType();
        this.isAttorney = data.getIsAttorney();
//        this.attorneySeries = data.getAttorneySeries();
//        this.attorneyNumber = data.getAttorneyNumber();
//        this.confiscatedDocumentId = Optional.ofNullable(data.getConfiscatedDocument()).map(AbstractDict::getId).orElse(null);
        this.confiscatedCategories = Optional.ofNullable(data.getConfiscatedCategories()).orElse(List.of());
//        this.drivingLicenseSeries = data.getDrivingLicenseSeries();
//        this.drivingLicenseNumber = data.getDrivingLicenseNumber();
    }

    // TODO До устарения апи старой убдд структур
    public ProtocolUbddDataResponseUbddDTO(ProtocolUbddDataView data, AddressResponseDTO registrationAddress) {
        super(data);
        this.groupId = data.getGroupId();
        this.violationPoint = data.getViolationPoint();
        this.impoundId = data.getImpoundId();
        this.regNumber = data.getRegNumber();
        this.vehicleBodyTypeId = data.getVehicleBodyTypeId();
        this.vehicleOwnerId = data.getVehicleOwnerId();
        this.vehicleMinistryId = data.getVehicleMinistryId();
        this.registrationAddress = registrationAddress;
        this.insuranceNumber = data.getInsuranceNumber();
        this.vehicleBrandType = data.getVehicleBrandType();
        this.attorneySeries = data.getAttorneySeries();
        this.attorneyNumber = data.getAttorneyNumber();
        this.confiscatedDocumentId = data.getConfiscatedDocumentId();
        this.drivingLicenseSeries = data.getDrivingLicenseSeries();
        this.drivingLicenseNumber = data.getDrivingLicenseNumber();


        this.radarSpeed = data.getRadarSpeed();
        this.maxSpeed = data.getMaxSpeed();
        this.isAttorney = data.getIsAttorney();
    }
}
