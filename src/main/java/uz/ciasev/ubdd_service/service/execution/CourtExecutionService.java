package uz.ciasev.ubdd_service.service.execution;

import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

import java.time.LocalDate;

public interface CourtExecutionService {

    void executionLicenseRevocation(Decision decision, LocalDate decisionDate);
}
