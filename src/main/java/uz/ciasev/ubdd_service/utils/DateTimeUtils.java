package uz.ciasev.ubdd_service.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

public class DateTimeUtils {


    public static int getFullYearsFor(LocalDate a) {
        return Period.between(a, LocalDate.now()).getYears();
    }


    public static int getFullYearsBetween(LocalDate a, LocalDate b) {
        return Period.between(a, b).getYears();
    }

    public static LocalDateTime of(Date date) {
        if (date == null) {
            return null;
        }

        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static int compereNullable(LocalDateTime d1, LocalDateTime d2) {
        if (d1 == null && d2 == null) return 0;

        if (d1 == null) return -1;

        if (d2 == null) return 1;

        return d1.compareTo(d2);
    }

    public static LocalDateTime max(LocalDateTime d1, LocalDateTime d2) {
        int compere = compereNullable(d1, d2);

        if (compere >= 0) {
            return d1;
        }

        return d2;
    }

    public static Optional<LocalDateTime> max(Optional<LocalDateTime> a, Optional<LocalDateTime> b) {
        if (a.isEmpty() && b.isEmpty()) {
            return Optional.empty();
        }

        if (a.isEmpty()) {
            return b;
        }

        if (b.isEmpty()) {
            return a;
        }

        return Optional.of(
                max(
                        a.get(),
                        b.get()
                )
        );
    }

    public static boolean isInFuture(LocalDateTime localDateTime) {
        return localDateTime.isAfter(LocalDateTime.now().plusMinutes(15));
    }

    public static boolean isInFuture(LocalDate localDate) {
        return localDate.isAfter(LocalDate.now());
    }
}
