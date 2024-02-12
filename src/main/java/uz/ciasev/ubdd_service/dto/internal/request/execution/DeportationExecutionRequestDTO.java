package uz.ciasev.ubdd_service.dto.internal.request.execution;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.FileFormatAlias;
import uz.ciasev.ubdd_service.entity.dict.resolution.DeportationTerminal;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.punishment.DeportationPunishment;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;
import uz.ciasev.ubdd_service.utils.validator.ValidFileUri;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class DeportationExecutionRequestDTO implements PunishmentExecutionRequestDTO {

    @NotNull(message = ErrorCode.DEPORTATION_DATE_REQUIRED)
    private LocalDate deportationDate;

    @NotNull(message = ErrorCode.DEPORTATION_TERMINAL_REQUIRED)
    @ActiveOnly(message = ErrorCode.DEPORTATION_TERMINAL_DEACTIVATED)
    @JsonProperty(value = "deportationTerminalId")
    private DeportationTerminal deportationTerminal;

    @ValidFileUri(allow = FileFormatAlias.PDF, message = ErrorCode.URI_INVALID)
    private String uri;

    @Override
    public Punishment applyTo(Punishment punishment) {

        DeportationPunishment dp = punishment.getDeportation();

        dp.setDeportationDate(this.deportationDate);
        dp.setDeportationTerminal(this.deportationTerminal);

        punishment.setExecutionUri(this.uri);

        return punishment;
    }

    @Override
    public LocalDate getExecutionDate() {
        return deportationDate;
    }

    @Override
    public PunishmentTypeAlias getPunishmentTypeAlias() {
        return PunishmentTypeAlias.DEPORTATION;
    }
}
