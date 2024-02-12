package uz.ciasev.ubdd_service.dto.internal.ubdd_data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.request.AddressRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.ExternalSystemAlias;
import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipType;
import uz.ciasev.ubdd_service.entity.dict.person.Gender;
import uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentType;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDVehicleBodyType;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDVehicleOwnerType;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ValidAddress;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class UbddTexPassDTO implements UbddTexPassDTOI {

    // OWNER
    @JsonProperty(value = "vehicleOwnerTypeId")
    @NotNull(message = ErrorCode.UBDD_VEHICLE_OWNER_TYPE_REQUIRED)
    private UBDDVehicleOwnerType vehicleOwnerType;

    @Size(max = 20, message = ErrorCode.UBDD_VEHICLE_OWNER_INN_MIN_MAX_LENGTH)
    private String vehicleOwnerInn;

    @Size(max = 500, message = ErrorCode.UBDD_VEHICLE_OWNER_ORGANIZATION_MIN_MAX_LENGTH)
    private String vehicleOwnerOrganizationName;

    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_OWNER_LAST_NAME_MIN_MAX_LENGTH)
    private String vehicleOwnerLastName;

    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_OWNER_FIRST_NAME_MIN_MAX_LENGTH)
    private String vehicleOwnerFirstName;

    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_OWNER_SECOND_NAME_MIN_MAX_LENGTH)
    private String vehicleOwnerSecondName;

    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_OWNER_SECOND_NAME_KIR_MIN_MAX_LENGTH)
    private String vehicleOwnerSecondNameKir;

    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_OWNER_FIRST_NAME_KIR_MIN_MAX_LENGTH)
    private String vehicleOwnerFirstNameKir;

    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_OWNER_LAST_NAME_KIR_MIN_MAX_LENGTH)
    private String vehicleOwnerLastNameKir;

    private LocalDate vehicleOwnerBirthDate;

    @Size(max = 20, message = ErrorCode.UBDD_VEHICLE_OWNER_PINPP_MIN_MAX_LENGTH)
    private String vehicleOwnerPinpp;

    // VEHICLE
    @Size(max = 20, message = ErrorCode.VEHICLE_NUMBER_MIN_MAX_SIZE)
    @NotNull(message = ErrorCode.UBDD_VEHICLE_NUMBER_REQUIRED)
    private String vehicleNumber;

    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_COLOR_MIN_MAX_LENGTH)
    @NotNull(message = ErrorCode.UBDD_VEHICLE_COLOR_REQUIRED)
    private String vehicleColor;

    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_COLOR_TYPE_MIN_MAX_LENGTH)
    private String vehicleSubColor;

    @JsonProperty(value = "vehicleBodyTypeId")
    //@NotNull(message = ErrorCode.UBDD_VEHICLE_BODY_TYPE_REQUIRED)
    private UBDDVehicleBodyType vehicleBodyType;

    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_BRAND_MIN_MAX_LENGTH)
    @NotNull(message = ErrorCode.UBDD_VEHICLE_BRAND_REQUIRED)
    private String vehicleBrand;

    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_MODEL_MIN_MAX_LENGTH)
    @NotNull(message = ErrorCode.UBDD_VEHICLE_MODEL_REQUIRED)
    private String vehicleModel;

    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_ENGINE_SERIES_MIN_MAX_LENGTH)
    private String vehicleEngineSeries;

    private Integer vehicleEngineHorsePower;
    private LocalDate vehicleRegistrationDate;

    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_CHASSIS_SERIES_MIN_MAX_LENGTH)
    private String vehicleChassisSeries;

    @Size(max = 20, message = ErrorCode.UBDD_VEHICLE_PASS_SERIES_MIN_MAX_LENGTH)
    @NotNull(message = ErrorCode.UBDD_VEHICLE_PASS_SERIES_REQUIRED)
    private String texPassSeries;

    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_PASS_NUMBER_MIN_MAX_LENGTH)
    @NotNull(message = ErrorCode.UBDD_VEHICLE_PASS_NUMBER_REQUIRED)
    private String texPassNumber;

    private Integer vehicleYear;
    private Long vehicleId;

    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_BODY_SERIES_MIN_MAX_LENGTH)
    private String vehicleBodySeries;

    @Valid
    @ValidAddress(message = ErrorCode.DOCUMENT_GIVEN_ADDRESS_INVALID)
    private AddressRequestDTO texPassGivenAddress;

    private AddressRequestDTO vehicleOwnerAddress;

    @JsonProperty(value = "vehicleOwnerCitizenshipTypeId")
    private CitizenshipType vehicleOwnerCitizenshipType;

    @JsonProperty(value = "vehicleOwnerGenderId")
    private Gender vehicleOwnerGender;

    private AddressRequestDTO vehicleOwnerBirthAddress;

    @Size(max = 20, message = ErrorCode.UBDD_VEHICLE_OWNER_DOCUMENT_SERIES_MIN_MAX_LENGTH)
    private String vehicleOwnerDocumentSeries;

    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_OWNER_DOCUMENT_NUMBER_MIN_MAX_LENGTH)
    private String vehicleOwnerDocumentNumber;

    @JsonProperty(value = "vehicleOwnerDocumentTypeId")
    private PersonDocumentType vehicleOwnerDocumentType;

    private LocalDate vehicleOwnerDocumentGivenDate;

    private LocalDate vehicleOwnerDocumentExpireDate;

    private AddressRequestDTO vehicleOwnerDocumentGivenAddress;

    @JsonProperty(value = "externalSystemId")
    private ExternalSystemAlias externalSystem;

    @Size(max = 32, message = ErrorCode.UBDD_EXTERNAL_ID_MIN_MAX_LENGTH)
    private String externalId;

    private String addition;

    @Size(max = 500, message = ErrorCode.UBDD_PHONE_INFO_MIN_MAX_LENGTH)
    private String phoneInfo;
}
