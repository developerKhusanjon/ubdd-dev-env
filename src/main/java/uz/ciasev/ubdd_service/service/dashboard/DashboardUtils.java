package uz.ciasev.ubdd_service.service.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Primary
@Service
@RequiredArgsConstructor
public class DashboardUtils {

    public static LocalDateTime from(LocalDate date) {
        return date.atStartOfDay();
    }

    public static LocalDateTime to(LocalDate date) {
        return date.plusDays(1).atStartOfDay();
    }

    public static long orElseZero(Long value) {
        return Optional.ofNullable(value).orElse(0L);
    }
}
