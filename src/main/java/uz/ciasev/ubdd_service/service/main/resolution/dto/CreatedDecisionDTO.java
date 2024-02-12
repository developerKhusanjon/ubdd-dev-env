package uz.ciasev.ubdd_service.service.main.resolution.dto;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.VictimTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

import java.util.List;
import java.util.Optional;

@Data
public class CreatedDecisionDTO {
    private final Decision decision;
    private final List<Compensation> compensations;

    public Optional<Compensation> getGovCompensation() {
        return compensations.stream()
                .filter(c -> c.getVictimType().is(VictimTypeAlias.GOVERNMENT))
                .findFirst();
    }
}
