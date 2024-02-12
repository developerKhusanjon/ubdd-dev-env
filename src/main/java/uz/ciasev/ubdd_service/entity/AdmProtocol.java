package uz.ciasev.ubdd_service.entity;

import java.time.LocalDateTime;

public interface AdmProtocol {

    LocalDateTime getViolationTime();

    LocalDateTime getRegistrationTime();
}
