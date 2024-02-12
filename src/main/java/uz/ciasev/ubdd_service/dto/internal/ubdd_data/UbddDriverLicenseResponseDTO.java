package uz.ciasev.ubdd_service.dto.internal.ubdd_data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.response.AddressResponseDTO;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddDrivingLicenseData;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Data
public class UbddDriverLicenseResponseDTO {

    private Long id;

    // OWNER

    @JsonProperty(value = "pinpp")
    private String vehicleOwnerPinpp;

    @JsonProperty(value = "birthDate")
    private LocalDate vehicleOwnerBirthDate;

    @JsonProperty(value = "lastNameLat")
    private String vehicleOwnerLastName;

    @JsonProperty(value = "firstNameLat")
    private String vehicleOwnerFirstName;

    @JsonProperty(value = "secondNameLat")
    private String vehicleOwnerSecondName;

    // DOCUMENT

    @JsonProperty(value = "givenDate")
    private LocalDate drivingLicenseFromDate;

    @JsonProperty(value = "expireDate")
    private LocalDate drivingLicenseToDate;

    @JsonProperty(value = "issuedBy")
    private String drivingLicenseIssuedBy;

    @JsonProperty(value = "serial")
    private String drivingLicenseSerial;

    @JsonProperty(value = "number")
    private String drivingLicenseNumber;

    private List<UbddDriverLicenseDocumentCategoryDTO> categories;

    @JsonProperty(value = "givenAddress")
    private AddressResponseDTO drivingLicenseGivenAddress;

    public UbddDriverLicenseResponseDTO(UbddDrivingLicenseData entity) {

        this.id = entity.getId();

        // OWNER
        this.vehicleOwnerPinpp = entity.getVehicleOwnerPinpp();
        this.vehicleOwnerBirthDate = entity.getVehicleOwnerBirthDate();
        this.vehicleOwnerLastName = entity.getVehicleOwnerLastName();
        this.vehicleOwnerFirstName = entity.getVehicleOwnerFirstName();
        this.vehicleOwnerSecondName = entity.getVehicleOwnerSecondName();

        // DOCUMENT
        this.drivingLicenseFromDate = entity.getDrivingLicenseFromDate();
        this.drivingLicenseToDate = entity.getDrivingLicenseToDate();
        this.drivingLicenseIssuedBy = entity.getDrivingLicenseIssuedBy();
        this.drivingLicenseSerial = entity.getDrivingLicenseSerial();
        this.drivingLicenseNumber = entity.getDrivingLicenseNumber();
        this.categories = entity.getCategories();
        this.drivingLicenseGivenAddress = Optional.ofNullable(entity.getDrivingLicenseGivenAddress()).map(AddressResponseDTO::new).orElse(null);
    }
}
