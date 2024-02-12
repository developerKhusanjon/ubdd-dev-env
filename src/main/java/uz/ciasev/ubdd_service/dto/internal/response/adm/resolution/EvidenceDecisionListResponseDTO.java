package uz.ciasev.ubdd_service.dto.internal.response.adm.resolution;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.resolution.evidence_decision.EvidenceDecision;

import java.time.LocalDateTime;

@Data
public class EvidenceDecisionListResponseDTO {

    private final Long id;
    private final LocalDateTime createdTime;
    private final LocalDateTime editedTime;
    private final boolean isActive;
    private final Long resolutionId;
    private final Long evidenceId;
    private final Long evidenceResultId;
    private final String personDescription;
    private final Long evidenceCategoryId;
    private final Long measureId;
    private final Long currencyId;
    private final Double quantity;
    private final Long cost;

    public EvidenceDecisionListResponseDTO(EvidenceDecision evidenceDecision) {
        this.id = evidenceDecision.getId();
        this.createdTime = evidenceDecision.getCreatedTime();
        this.editedTime = evidenceDecision.getEditedTime();
        this.isActive = evidenceDecision.isActive();
        this.resolutionId = evidenceDecision.getResolutionId();
        this.evidenceId = evidenceDecision.getEvidenceId();
        this.evidenceResultId = evidenceDecision.getEvidenceResultId();

        this.personDescription = evidenceDecision.getPersonDescription();
        this.evidenceCategoryId = evidenceDecision.getEvidenceCategoryId();
        this.measureId = evidenceDecision.getMeasureId();
        this.currencyId = evidenceDecision.getCurrencyId();
        this.quantity = evidenceDecision.getQuantity();
        this.cost = evidenceDecision.getCost();

    }
}

