package uz.ciasev.ubdd_service.entity.ubdd_data.old_structure;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "v_protocol_ubdd_data")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProtocolUbddDataView {

    @Id
    private Long id;

    private LocalDateTime createdTime;

    private LocalDateTime editedTime;

    private Long protocolId;

    private Long groupId;

    private String violationPoint;

    private Long impoundId;

    private String regNumber;

    private Float radarSpeed;

    private Float maxSpeed;

    private Long vehicleBodyTypeId;

    private String vehicleColor;

    private Long vehicleOwnerId;

    private Long vehicleMinistryId;

    private Long registrationAddressId;

    private String insuranceNumber;

    private String vehicleBrandType;

    private Boolean isAttorney;

    private String attorneySeries;

    private String attorneyNumber;

    private Long confiscatedDocumentId;

    private String drivingLicenseSeries;

    private String drivingLicenseNumber;

    private String vehicleYear;

    private LocalDate vehicleOwnerBirthdate;

    private String vehicleOwnerPass;

    private Long vehicleOwnerAddressId;

    private String vehiclePassSeries;

    private String vehiclePassNumber;

    private String vehicleOwnerLastName;

    private String vehicleOwnerFirstName;

    private String vehicleOwnerSecondName;

    private Long vehicleColorTypeId;

    private String vehicleBrand;

    private String vehicleNumber;

    private String vehicleAdditional;

    private String vehicleEngineSeries;

    private String vehicleBodySeries;

    private String vehicleChassisSeries;

    private Integer vehicleEngineHorsePower;

    private LocalDate vehicleRegistrationDate;

    private String vehicleOwnerPinpp;

    private String vehicleOwnerInn;

    private String vehicleOwnerDrivingLicenseCategory;

}
