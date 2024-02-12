package uz.ciasev.ubdd_service.service.main.resolution.dto;

import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.exception.implementation.ImplementationException;

import java.util.List;

public class CreatedSingleResolutionDTO extends CreatedResolutionDTO {

    public CreatedSingleResolutionDTO(Resolution resolution, List<CreatedDecisionDTO> decisions) {
        super(resolution, decisions, List.of());

        if (this.getCreatedDecisions().size() != 1) {
            throw new ImplementationException("Single resolution mast content only one decision");
        }
    }

    public CreatedDecisionDTO getCreatedDecision() {
        return getCreatedDecisions().get(0);
    }

    public Decision getDecision() {
        return getDecisions().get(0);
    }
}
