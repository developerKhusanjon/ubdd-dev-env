package uz.ciasev.ubdd_service.dto.internal.response.court;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.court.CourtAdmCaseMovement;

import java.time.LocalDateTime;

@Data
public class CourtAdmCaseMovementResponseDTO {

    private final Long id;
    private final LocalDateTime createdTime;
    private final LocalDateTime statusTime;
    private final Long caseId;
    private final Long claimId;
    private final Long statusId;
    private final String validationErrors;

    public CourtAdmCaseMovementResponseDTO(CourtAdmCaseMovement caseMovement) {
        this.id = caseMovement.getId();
        this.createdTime = caseMovement.getCreatedTime();
        this.caseId = caseMovement.getCaseId();
        this.claimId = caseMovement.getClaimId();
        this.statusId = caseMovement.getStatusId();
        this.validationErrors = caseMovement.getValidationErrors();
        this.statusTime = caseMovement.getStatusTime() == null ? caseMovement.getCreatedTime() : caseMovement.getStatusTime();
    }
}
