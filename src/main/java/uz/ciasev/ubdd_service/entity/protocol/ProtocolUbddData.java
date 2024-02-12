package uz.ciasev.ubdd_service.entity.protocol;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.dict.ubdd.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "protocol_ubdd_data")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class ProtocolUbddData implements ProtocolData {

    // PREDEFINED

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createdTime = LocalDateTime.now();

    @LastModifiedDate
    private LocalDateTime editedTime;


    // COMMON

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_id")
    private Protocol protocol;

    @Column(name = "protocol_id", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Long protocolId;

    // UBDD

//    @ManyToOne
//    @JoinColumn(name = "group_id")
//    private UBDDGroup group;

//    private String violationPoint;
//
//    @ManyToOne
//    @JoinColumn(name = "impound_id")
//    private UBDDImpound impound;

//    private String regNumber;
    private Float radarSpeed;
    private Float maxSpeed;

//    @ManyToOne
//    @JoinColumn(name = "vehicle_body_type_id")
//    private UBDDVehicleBodyType vehicleBodyType;
//
//    private String vehicleColor;
//
//    @ManyToOne
//    @JoinColumn(name = "vehicle_owner_id")
//    private UBDDVehicleOwnerType vehicleOwner;
//
//    @ManyToOne
//    @JoinColumn(name = "vehicle_ministry_id")
//    private UBDDVehicleMinistry vehicleMinistry;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "registration_address_id")
//    private Address registrationAddress;
//
//    private String insuranceNumber;
//
//    private String vehicleBrandType;

    private Boolean isAttorney;
//    private String attorneySeries;
//    private String attorneyNumber;
//
//    @ManyToOne
//    @JoinColumn(name = "confiscated_document_id")
//    private UBDDConfiscatedCategory confiscatedDocument;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", name = "confiscated_categories_id")
    private List<Long> confiscatedCategories;

//    private String drivingLicenseSeries;
//    private String drivingLicenseNumber;
//
//
//    // TRANSPORT
//
//    private String vehicleYear;
//    private LocalDate vehicleOwnerBirthdate;
//    private String vehicleOwnerPass;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "owner_address_id")
//    private Address vehicleOwnerAddress;
//
//    private String vehiclePassSeries;
//    private String vehiclePassNumber;
//    private String vehicleOwnerLastName;
//    private String vehicleOwnerFirstName;
//    private String vehicleOwnerSecondName;
//
//    // UBDD + TRANSPORT
//
//    @ManyToOne
//    @JoinColumn(name = "vehicle_color_type_id")
//    private UBDDVehicleColorType vehicleColorType;
//
//    private String vehicleBrand;
//    private String vehicleNumber;
    private String vehicleAdditional;

    //

//    private String vehicleEngineSeries;
//    private String vehicleBodySeries;
//    private String vehicleChassisSeries;
//    private Integer vehicleEngineHorsePower;
//    private LocalDate vehicleRegistrationDate;
//    private String vehicleOwnerPinpp;
//    private String vehicleOwnerInn;
//    private String vehicleOwnerDrivingLicenseCategory;

    public void setConfiscatedCategories(List<UBDDConfiscatedCategory> categories) {
        this.confiscatedCategories = Optional.ofNullable(categories)
                .orElse(List.of())
                .stream()
                .map(UBDDConfiscatedCategory::getId)
                .collect(Collectors.toList());
    }
}
