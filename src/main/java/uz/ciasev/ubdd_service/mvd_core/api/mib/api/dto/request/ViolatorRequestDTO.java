package uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ViolatorRequestDTO {

    @JsonProperty("debitorRegion")
    private Long residenceAddressRegionId;

    @JsonProperty("debitorAddr")
    private String residenceAddress;

    @JsonProperty("debitorAddrReal")
    private String actualAddress;

    //    TI Фамилия совершившего лица (латиница)
    @JsonProperty("debitorLastName")
    private String lastName;

    //    TI Имя совершившего лица (латиница)
    @JsonProperty("debitorFirstName")
    private String firstName;

    //    TI Отчество совершившего лица (латиница)
    @JsonProperty("debitorMiddleName")
    private String secondName;

    @JsonProperty("debitorLastNameRu")
    private String lastNameRu;

    @JsonProperty("debitorFirstNameRu")
    private String firstNameRu;

    @JsonProperty("debitorMiddleNameRu")
    private String secondNameRu;

    @JsonProperty("debitorCitizenType")
    private Long citizenTypeId;

    @JsonProperty("debitorSex")
    private Long genderId;

    @JsonProperty("debitorOccupation")
    private Long occupationId;

    @JsonProperty("debitorPosition")
    private String employmentPosition;

    @JsonProperty("debitorPlaceWork")
    private String employmentPlace;

    @JsonProperty("debitorPinpp")
    private String pinpp;

    @JsonProperty("debitorTypeDoc")
    private Long personDocumentTypeId;

    @JsonProperty("debitorPassportSN")
    private String documentSeries;

    @JsonProperty("debitorPassportNum")
    private String documentNumber;

    @JsonProperty("debitorDocRegion")
    private String documentGivenAddress;

    @JsonProperty("debitorDocGetDate")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate documentGivenDate;

    @JsonProperty("debitorBirthDate")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthdate;

}
