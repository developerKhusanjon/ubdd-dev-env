package uz.ciasev.ubdd_service.dto.internal.ubdd_data;

import uz.ciasev.ubdd_service.dto.internal.request.AddressRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.ExternalSystemAlias;
import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipType;
import uz.ciasev.ubdd_service.entity.dict.person.Gender;
import uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentType;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDVehicleBodyType;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDVehicleOwnerType;
import java.time.LocalDate;

public interface UbddTexPassDTOI {

    default boolean isVehicleOwnerPinppEmpty() {
        return getVehicleOwnerPinpp() == null || getVehicleOwnerPinpp().isBlank();
    }

    default String getOwnerInfo() {
        return String.format("%s, Phone: %s", getAddition(), getPhoneInfo());
    }

    ExternalSystemAlias getExternalSystem();

    String getExternalId();

    String getVehicleNumber();
    String getVehicleColor();
    String getVehicleSubColor();
    UBDDVehicleBodyType getVehicleBodyType();
    String getVehicleBrand();
    String getVehicleModel();
    String getVehicleEngineSeries();
    Integer getVehicleEngineHorsePower();
    LocalDate getVehicleRegistrationDate();
    String getVehicleChassisSeries();
    String getTexPassSeries();
    String getTexPassNumber();
    Integer getVehicleYear();
    Long getVehicleId();
    String getVehicleBodySeries();
    AddressRequestDTO getTexPassGivenAddress();

    String getAddition();
    String getPhoneInfo();

    AddressRequestDTO getVehicleOwnerAddress();
    UBDDVehicleOwnerType getVehicleOwnerType();

    String getVehicleOwnerInn();
    String getVehicleOwnerOrganizationName();

    String getVehicleOwnerPinpp();
    String getVehicleOwnerLastName();
    String getVehicleOwnerFirstName();
    String getVehicleOwnerSecondName();
    String getVehicleOwnerSecondNameKir();
    String getVehicleOwnerFirstNameKir();
    String getVehicleOwnerLastNameKir();
    LocalDate getVehicleOwnerBirthDate();
    CitizenshipType getVehicleOwnerCitizenshipType();
    Gender getVehicleOwnerGender();
    AddressRequestDTO getVehicleOwnerBirthAddress();
    String getVehicleOwnerDocumentSeries();
    String getVehicleOwnerDocumentNumber();
    PersonDocumentType getVehicleOwnerDocumentType();
    LocalDate getVehicleOwnerDocumentGivenDate();
    LocalDate getVehicleOwnerDocumentExpireDate();
    AddressRequestDTO getVehicleOwnerDocumentGivenAddress();
}
