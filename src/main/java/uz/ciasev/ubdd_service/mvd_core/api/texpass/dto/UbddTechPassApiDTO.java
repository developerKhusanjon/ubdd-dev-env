package uz.ciasev.ubdd_service.mvd_core.api.texpass.dto;

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
public class UbddTechPassApiDTO implements UbddTexPassDTOI {

    @JsonProperty("externalSystemId")
    private ExternalSystemAlias externalSystem;
    private Long id; // 28927940,
    private Long vehicleId; // 28927940,
    private LocalDate expireDate; // null,
    private String registrationNumber; // "VL0002437040",
    private String texPassNumber; // "1829607",
    private String texPassSeries; // "AAF",
    private String vehicleBodySeries; // "XWB5V31BDGA504398",

    @JsonProperty("vehicleBodyTypeId")
    private UBDDVehicleBodyType vehicleBodyType; // 27,
    private String vehicleBrand; // "-",
    private String vehicleChassisSeries; // "РАКАМСИЗ",
    private String vehicleColor; // "ОҚ",
    private Integer vehicleEngineHorsePower; // 106,
    private String vehicleEngineSeries; // "12152032DFEX0423",
    private String vehicleModel; // "LACETTI",
    private LocalDate vehicleRegistrationDate; // "2020-05-22
    private String vehicleSubColor; // "БЕЛОДЫМЧАТЫЙ",
    private String vehicleVinCode; // "XWB5V31BDGA504398",
    private Integer vehicleYear; // 2015
    private String vehicleNumber;

    private AddressRequestDTO texPassGivenAddress;
    private String addition;

    @JsonProperty("phone")
    private String phoneInfo;
    private AddressRequestDTO vehicleOwnerAddress;

    @JsonProperty("vehicleOwnerTypeId")
    private UBDDVehicleOwnerType vehicleOwnerType;

    private String vehicleOwnerInn;
    private String vehicleOwnerOrganizationName;


    private String vehicleOwnerPinpp;
    private LocalDate vehicleOwnerBirthDate;
    private String vehicleOwnerFirstName;
    private String vehicleOwnerLastName;
    private String vehicleOwnerSecondName;
    private String vehicleOwnerFirstNameKir;
    private String vehicleOwnerLastNameKir;
    private String vehicleOwnerSecondNameKir;

    private CitizenshipType vehicleOwnerCitizenshipType;
    private Gender vehicleOwnerGender;
    private AddressRequestDTO vehicleOwnerBirthAddress;
    private String vehicleOwnerDocumentSeries;
    private String vehicleOwnerDocumentNumber;
    private PersonDocumentType vehicleOwnerDocumentType;
    private LocalDate vehicleOwnerDocumentGivenDate;
    private LocalDate vehicleOwnerDocumentExpireDate;
    private AddressRequestDTO vehicleOwnerDocumentGivenAddress;

    @Override
    public String getExternalId() {
        if (vehicleId == null) return null;
        return vehicleId.toString();
    }
}
