package uz.ciasev.ubdd_service.mvd_core.api.court.dto.nine;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class PersonRequestExternalDTO {

    @Size(max = 50, message = ErrorCode.MAX_FIRST_NAME_KIR_LENGTH)
    private String firstNameKir;

    @Size(max = 50, message = ErrorCode.MAX_SECOND_NAME_KIR_LENGTH)
    private String secondNameKir;

    @Size(max = 50, message = ErrorCode.MAX_LAST_NAME_KIR_LENGTH)
    private String lastNameKir;

    @Size(max = 50, message = ErrorCode.MAX_FIRST_NAME_LAT_LENGTH)
    @NotNull(message = ErrorCode.FIRST_NAME_LAT_REQUIRED)
    private String firstNameLat;

    @Size(max = 50, message = ErrorCode.MAX_SECOND_NAME_LAT_LENGTH)
    private String secondNameLat;

    @Size(max = 50, message = ErrorCode.MAX_LAST_NAME_LAT_LENGTH)
    @NotNull(message = ErrorCode.LAST_NAME_LAT_REQUIRED)
    private String lastNameLat;

    @NotNull(message = ErrorCode.BIRTH_DATE_REQUIRED)
    @NotNull(message = ErrorCode.BIRTH_DATE_REQUIRED)
    private LocalDate birthDate;

    private AddressRequestExternalDTO birthAddress;

    @JsonProperty(value = "citizenshipTypeId")
    private Long citizenshipTypeId;

    @JsonProperty(value = "genderId")
    private Long genderId;

    private Long nationalityId;
}

