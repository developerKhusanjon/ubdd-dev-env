package uz.ciasev.ubdd_service.dto.internal.request.execution;

import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;

import java.time.LocalDate;

public interface PunishmentExecutionRequestDTO {

    Punishment applyTo(Punishment punishment);
    LocalDate getExecutionDate();
    PunishmentTypeAlias getPunishmentTypeAlias();
}
