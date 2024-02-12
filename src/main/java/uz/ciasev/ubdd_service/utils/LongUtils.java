package uz.ciasev.ubdd_service.utils;

import java.util.Optional;

public class LongUtils {

    public static Optional<Long> sum(Optional<Long> a, Optional<Long> b) {
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
                Long.sum(
                        a.get(),
                        b.get()
                )
        );
    }
}
