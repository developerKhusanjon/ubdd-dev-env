package uz.ciasev.ubdd_service.repository.dashboard.projections;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

public interface AmountByMonthProjection {

    Timestamp getMonthDate();
    Long getAmount();

    default LocalDate getLocalMonthDate() {
        return Optional.ofNullable(getMonthDate()).map(d -> d.toLocalDateTime().toLocalDate()).orElse(null);
    }
}
