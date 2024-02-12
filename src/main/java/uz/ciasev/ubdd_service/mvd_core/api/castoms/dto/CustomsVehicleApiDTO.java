package uz.ciasev.ubdd_service.mvd_core.api.castoms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.request.AddressRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddTexPassDTOI;
import uz.ciasev.ubdd_service.entity.dict.ExternalSystemAlias;
import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipType;
import uz.ciasev.ubdd_service.entity.dict.person.Gender;
import uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentType;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDVehicleBodyType;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDVehicleOwnerType;

import java.time.LocalDate;

@Data
public class CustomsVehicleApiDTO implements UbddTexPassDTOI {

    private String externalId;

    @JsonProperty(value = "id")
    private String customsEventId;

    private String vehicleNumber;

    private String vehicleOwnerLastName;

    private String vehicleOwnerFirstName;

    private String vehicleOwnerSecondName;

    private String vehicleModel;

    private String vehicleEngineSeries;

    private String vehicleChassisSeries;

    private String vehicleBodySeries;

    private Integer vehicleYear;

    private String vehicleOwnerDocumentNumber;

    private String comment;

    private String address;

    private LocalDate vehicleOwnerBirthDate;

    @JsonProperty(value = "externalSystemId")
    private ExternalSystemAlias externalSystem;

    private UBDDVehicleOwnerType vehicleOwnerType;

    @Override
    public String getVehicleBrand() {
        return vehicleModel;
    }

    @Override
    public String getOwnerInfo() {
        return String.format("Address: %s. %s", getAddress(), getComment());
    }

    @Override
    public String getVehicleOwnerInn() {
        return null;
    }

    @Override
    public String getVehicleOwnerOrganizationName() {
        return null;
    }

    @Override
    public String getVehicleOwnerPinpp() {
        return null;
    }

    @Override
    public String getVehicleOwnerSecondNameKir() {
        return null;
    }

    @Override
    public String getVehicleOwnerFirstNameKir() {
        return null;
    }

    @Override
    public String getVehicleOwnerLastNameKir() {
        return null;
    }

    @Override
    public String getVehicleColor() {
        return null;
    }

    @Override
    public String getVehicleSubColor() {
        return null;
    }

    @Override
    public UBDDVehicleBodyType getVehicleBodyType() {
        return null;
    }

    @Override
    public Integer getVehicleEngineHorsePower() {
        return null;
    }

    @Override
    public LocalDate getVehicleRegistrationDate() {
        return null;
    }

    @Override
    public String getTexPassSeries() {
        return null;
    }

    @Override
    public String getTexPassNumber() {
        return null;
    }

    @Override
    public Long getVehicleId() {
        return null;
    }

    @Override
    public AddressRequestDTO getTexPassGivenAddress() {
        return null;
    }

    @Override
    public String getAddition() {
        return null;
    }

    @Override
    public String getPhoneInfo() {
        return null;
    }

    @Override
    public AddressRequestDTO getVehicleOwnerAddress() {
        return null;
    }

    @Override
    public CitizenshipType getVehicleOwnerCitizenshipType() {
        return null;
    }

    @Override
    public Gender getVehicleOwnerGender() {
        return null;
    }

    @Override
    public AddressRequestDTO getVehicleOwnerBirthAddress() {
        return null;
    }

    @Override
    public String getVehicleOwnerDocumentSeries() {
        return null;
    }


    @Override
    public PersonDocumentType getVehicleOwnerDocumentType() {
        return null;
    }

    @Override
    public LocalDate getVehicleOwnerDocumentGivenDate() {
        return null;
    }

    @Override
    public LocalDate getVehicleOwnerDocumentExpireDate() {
        return null;
    }

    @Override
    public AddressRequestDTO getVehicleOwnerDocumentGivenAddress() {
        return null;
    }

    public boolean isVehicleOwnerPinppEmpty() {
        return true;
    }
}
