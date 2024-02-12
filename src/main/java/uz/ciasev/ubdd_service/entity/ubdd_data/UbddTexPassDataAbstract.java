package uz.ciasev.ubdd_service.entity.ubdd_data;

import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.dict.ExternalSystemAlias;
import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipType;
import uz.ciasev.ubdd_service.entity.dict.person.Gender;
import uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentType;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDVehicleBodyType;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDVehicleOwnerType;
import uz.ciasev.ubdd_service.entity.dict.ubdd.VehicleNumberType;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.utils.FioUtils;
import uz.ciasev.ubdd_service.utils.converter.ExternalSystemToAliasConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UbddTexPassDataAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter(AccessLevel.NONE)
    protected Long id;

    @Getter
    @Setter(AccessLevel.NONE)
    protected LocalDateTime createdTime = LocalDateTime.now();

    @Getter
    @LastModifiedDate
    @Setter(AccessLevel.NONE)
    protected LocalDateTime editedTime;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    protected User user;

    // data source

    @Getter
    @Setter
    @Column(name = "external_system_id")
    @Convert(converter = ExternalSystemToAliasConverter.class)
    protected ExternalSystemAlias externalSystem;

    @Getter
    @Setter
    protected String externalId;

    // OWNER

    @ManyToOne
    @JoinColumn(name = "vehicle_owner_type_id")
    @Getter
    @Setter
    protected UBDDVehicleOwnerType vehicleOwnerType;

    @Getter
    @Setter
    protected String vehicleOwnerInn;

    @Getter
    @Setter
    protected String vehicleOwnerOrganizationName;

    @Getter
    @Setter
    protected String vehicleOwnerLastName;

    @Getter
    @Setter
    protected String vehicleOwnerFirstName;

    @Getter
    @Setter
    protected String vehicleOwnerSecondName;

    @Getter
    @Setter
    protected String vehicleOwnerSecondNameKir;

    @Getter
    @Setter
    protected String vehicleOwnerFirstNameKir;

    @Getter
    @Setter
    protected String vehicleOwnerLastNameKir;

    @Getter
    @Setter
    protected LocalDate vehicleOwnerBirthDate;

    @Getter
    @Setter
    protected String vehicleOwnerPinpp;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "vehicle_owner_address_id")
    protected Address vehicleOwnerAddress;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "vehicle_owner_citizenship_type_id")
    protected CitizenshipType vehicleOwnerCitizenshipType;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "vehicle_owner_gender_id")
    protected Gender vehicleOwnerGender;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "vehicle_owner_birth_address_id")
    protected Address vehicleOwnerBirthAddress;

    @Getter
    @Setter
    protected String vehicleOwnerDocumentSeries;

    @Getter
    @Setter
    protected String vehicleOwnerDocumentNumber;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "vehicle_owner_document_type_id")
    protected PersonDocumentType vehicleOwnerDocumentType;

    @Getter
    @Setter
    protected LocalDate vehicleOwnerDocumentGivenDate;

    @Getter
    @Setter
    protected LocalDate vehicleOwnerDocumentExpireDate;

    @ManyToOne
    @JoinColumn(name = "vehicle_owner_document_given_address_id")
    @Getter
    @Setter
    protected Address vehicleOwnerDocumentGivenAddress;

    // VEHICLE
    @Getter
    @Setter
    protected String vehicleNumber;

    @ManyToOne
    @JoinColumn(name = "vehicle_number_type_id")
    @Getter
    @Setter
    protected VehicleNumberType vehicleNumberType;

    @Getter
    @Setter
    protected String vehicleColorType;

    @Getter
    @Setter
    protected String vehicleColor;

    @ManyToOne
    @JoinColumn(name = "vehicle_body_type_id")
    @Getter
    @Setter
    protected UBDDVehicleBodyType vehicleBodyType;

    @Getter
    @Setter
    protected String vehicleBrand;

    @Getter
    @Setter
    protected String vehicleModel;

    @Getter
    @Setter
    protected String vehicleEngineSeries;

    @Getter
    @Setter
    protected Integer vehicleEngineHorsePower;

    @Getter
    @Setter
    protected LocalDate vehicleRegistrationDate;

    @Getter
    @Setter
    protected String vehicleChassisSeries;

    @Getter
    @Setter
    protected String texPassSeries;

    @Getter
    @Setter
    protected String texPassNumber;

    @Getter
    @Setter
    protected Integer vehicleYear;

    @Getter
    @Setter
    protected Long vehicleId;

    @Getter
    protected String vehicleBodySeries;

    @ManyToOne
    @JoinColumn(name = "tex_pass_given_address_id")
    @Getter
    @Setter
    protected Address texPassGivenAddress;

    @Getter
    @Setter
    protected String addition;

    @Getter
    @Setter
    protected String phoneInfo;

    // JPA AND CRITERIA ONLY FIELDS

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    public Long getUserId() {
        if (user == null) return null;
        return user.getId();
    }

    public Long getVehicleOwnerTypeId() {
        if (this.vehicleOwnerType == null) return null;
        return this.vehicleOwnerType.getId();
    }

    public Long getVehicleBodyTypeId() {
        if (this.vehicleBodyType == null) return null;
        return this.vehicleBodyType.getId();
    }

    public String getOwnerInfo() {
        if (this.getVehicleOwnerType().getIsJuridic()) {
            return this.getVehicleOwnerOrganizationName();
        } else {
            return FioUtils.buildFullFio(
                    this.getVehicleOwnerLastName(),
                    this.getVehicleOwnerFirstName(),
                    this.getVehicleOwnerSecondName()
            );
        }
    }
}
