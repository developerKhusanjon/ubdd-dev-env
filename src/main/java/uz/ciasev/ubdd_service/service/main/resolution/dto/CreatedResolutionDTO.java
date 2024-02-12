package uz.ciasev.ubdd_service.service.main.resolution.dto;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.evidence_decision.EvidenceDecision;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CreatedResolutionDTO implements AdmEntity {
    private final Resolution resolution;
    private final List<CreatedDecisionDTO> createdDecisions;
    private final List<EvidenceDecision> evidenceDecisions;

    @Override
    public Long getId() {
        return resolution.getId();
    }

    @Override
    public EntityNameAlias getEntityNameAlias() {
        return EntityNameAlias.RESOLUTION;
    }

    public List<Decision> getDecisions() {
        return getCreatedDecisions().stream()
                .map(CreatedDecisionDTO::getDecision)
                .collect(Collectors.toList());
    }
}
