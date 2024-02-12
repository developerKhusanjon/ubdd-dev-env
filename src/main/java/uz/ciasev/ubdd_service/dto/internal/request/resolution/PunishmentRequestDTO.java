package uz.ciasev.ubdd_service.dto.internal.request.resolution;

import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentType;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;

public interface PunishmentRequestDTO {

    PunishmentType getPunishmentType();

    Long getAmount();

    Integer getYears();

    Integer getMonths();

    Integer getDays();

    Integer getHours();

    Punishment buildPunishment();
}
