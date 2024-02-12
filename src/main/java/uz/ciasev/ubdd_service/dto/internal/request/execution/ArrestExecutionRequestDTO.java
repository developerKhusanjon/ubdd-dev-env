package uz.ciasev.ubdd_service.dto.internal.request.execution;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.FileFormatAlias;
import uz.ciasev.ubdd_service.entity.dict.resolution.ArrestPlaceType;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.punishment.ArrestPunishment;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ValidArrestOutInformation;
import uz.ciasev.ubdd_service.utils.validator.ValidFileUri;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@ValidArrestOutInformation
public class ArrestExecutionRequestDTO implements PunishmentExecutionRequestDTO {

    @NotNull(message = ErrorCode.IN_DATE_REQUIRED)
    private LocalDate inDate;

    @NotBlank(message = ErrorCode.IN_STATE_REQUIRED)
    private String inState;

    private LocalDate outDate;

    private String outState;

    @ValidFileUri(allow = FileFormatAlias.PDF, message = ErrorCode.URI_INVALID)
    private String uri;

    @NotNull(message = ErrorCode.ARREST_PLACE_TYPE_REQUIRED)
    @JsonProperty(value = "arrestPlaceTypeId")
    private ArrestPlaceType arrestPlaceType;

    @Override
    public Punishment applyTo(Punishment punishment) {

        ArrestPunishment ap = punishment.getArrest();

        ap.setInDate(this.inDate);
        ap.setInState(this.inState);
        ap.setOutDate(this.outDate);
        ap.setOutState(this.outState);
        ap.setArrestPlaceType(this.arrestPlaceType);

        punishment.setExecutionUri(this.uri);

        return punishment;
    }

    @Override
    public LocalDate getExecutionDate() {
        return outDate;
    }

    @Override
    public PunishmentTypeAlias getPunishmentTypeAlias() {
        return PunishmentTypeAlias.ARREST;
    }
}
