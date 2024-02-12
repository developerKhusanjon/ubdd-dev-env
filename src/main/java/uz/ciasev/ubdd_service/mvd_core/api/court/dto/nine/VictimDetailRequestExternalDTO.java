package uz.ciasev.ubdd_service.mvd_core.api.court.dto.nine;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.Size;

@Data
public class VictimDetailRequestExternalDTO {

    @JsonProperty(value = "occupationId")
    private Long occupationId;

    @Size(max = 250, message = ErrorCode.MAX_EMPLOYMENT_POSITION_LENGTH)
    private String employmentPlace;

    @Size(max = 250, message = ErrorCode.MAX_EMPLOYMENT_POSITION_LENGTH)
    private String employmentPosition;

    @JsonProperty(value = "intoxicationTypeId")
    private Long intoxicationTypeId;

    private String signature;
}
