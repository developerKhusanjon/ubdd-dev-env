package uz.ciasev.ubdd_service.dto.internal.ubdd_data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.request.AddressRequestDTO;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ValidAddress;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
public class UbddDriverLicenseDTO {

    // OWNER
    @JsonProperty(value = "pinpp")
    @Size(max = 20, message = ErrorCode.UBDD_VEHICLE_OWNER_PINPP_MIN_MAX_LENGTH)
    private String vehicleOwnerPinpp;

    @JsonProperty(value = "birthDate")
    private LocalDate vehicleOwnerBirthDate;

    @JsonProperty(value = "lastNameLat")
    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_OWNER_LAST_NAME_MIN_MAX_LENGTH)
    private String vehicleOwnerLastName;

    @JsonProperty(value = "firstNameLat")
    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_OWNER_FIRST_NAME_MIN_MAX_LENGTH)
    private String vehicleOwnerFirstName;

    @JsonProperty(value = "secondNameLat")
    @Size(max = 100, message = ErrorCode.UBDD_VEHICLE_OWNER_SECOND_NAME_MIN_MAX_LENGTH)
    private String vehicleOwnerSecondName;

    // DOCUMENT

    @JsonProperty(value = "givenDate")
    private LocalDate drivingLicenseFromDate;

    @JsonProperty(value = "expireDate")
    private LocalDate drivingLicenseToDate;

    @JsonProperty(value = "issuedBy")
    @Size(max = 500, message = ErrorCode.DRIVING_LICENSE_ISSUED_BY_MIN_MAX_SIZE)
    private String drivingLicenseIssuedBy;

    @JsonProperty(value = "serial")
    @Size(max = 20, message = ErrorCode.DRIVING_LICENSE_SERIES_MAX_LENGTH)
    private String drivingLicenseSerial;

    @JsonProperty(value = "number")
    @Size(max = 100, message = ErrorCode.DRIVING_LICENSE_NUMBER_MAX_LENGTH)
    private String drivingLicenseNumber;

    private List<UbddDriverLicenseDocumentCategoryDTO> categories;

    @JsonProperty(value = "givenAddress")
    @Valid
    @ValidAddress(message = ErrorCode.DOCUMENT_GIVEN_ADDRESS_INVALID)
    private AddressRequestDTO drivingLicenseGivenAddress;
}
