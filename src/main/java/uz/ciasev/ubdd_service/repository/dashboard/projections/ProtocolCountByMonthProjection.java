package uz.ciasev.ubdd_service.repository.dashboard.projections;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

public interface ProtocolCountByMonthProjection {

    Timestamp getMonthDate();
    Long getCountValue();

    default LocalDate getLocalMonthDate() {
        return Optional.ofNullable(getMonthDate()).map(d -> d.toLocalDateTime().toLocalDate()).orElse(null);
    }
}
