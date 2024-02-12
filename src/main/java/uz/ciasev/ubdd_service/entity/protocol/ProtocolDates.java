package uz.ciasev.ubdd_service.entity.protocol;

import java.time.LocalDateTime;

public interface ProtocolDates {

    LocalDateTime getRegistrationTime();
    LocalDateTime getViolationTime();
}
