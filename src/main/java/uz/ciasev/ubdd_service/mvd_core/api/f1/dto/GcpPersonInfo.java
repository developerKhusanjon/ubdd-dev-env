package uz.ciasev.ubdd_service.mvd_core.api.f1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.mvd_core.api.ExternalApiAddress;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class GcpPersonInfo {

    @JsonProperty("pinfl")
    private String pinpp;
    @JsonProperty("firstNameKir")
    private String firstNameKir;
    @JsonProperty("secondNameKir")
    private String secondNameKir;
    @JsonProperty("lastNameKir")
    private String lastNameKir;

    @JsonProperty("firstNameLat")
    private String firstNameLat;
    @JsonProperty("secondNameLat")
    private String secondNameLat;
    @JsonProperty("lastNameLat")
    private String lastNameLat;

    @JsonProperty("birthDate")
    private LocalDate birthDate;
    @JsonProperty("genderId")
    private Long genderId;
    @JsonProperty("nationalityId")
    private Long nationalityId;

    @JsonProperty("birthAddress")
    private ExternalApiAddress birthAddress;
    @JsonProperty("givenAddress")
    private ExternalApiAddress givenAddress;
    @JsonProperty("residentAddress")
    private ExternalApiAddress residentAddress;
    @JsonProperty("personDocumentTypeId")
    private Long personDocumentTypeId;

    @JsonProperty("series")
    private String series;
    @JsonProperty("number")
    private String number;

    @JsonProperty("givenDate")
    private LocalDate givenDate;
    @JsonProperty("expireDate")
    private LocalDate expireDate;
    @JsonProperty("photoId")
    private BigInteger photoId;

    @JsonProperty("photoType")
    private String photoType;

    public String getId() {
        return String.valueOf(UUID.randomUUID());
    }
}
