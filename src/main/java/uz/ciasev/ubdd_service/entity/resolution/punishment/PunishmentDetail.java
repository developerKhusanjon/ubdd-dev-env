package uz.ciasev.ubdd_service.entity.resolution.punishment;

import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;

import java.time.LocalDate;

public interface PunishmentDetail {

//    default boolean isExecuted() {
//        return Objects.nonNull(getExecutionDate());
//    }

    LocalDate getExecutionDate();

    AdmStatusAlias calculateStatusAlias();
}
