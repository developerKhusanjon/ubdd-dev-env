package uz.ciasev.ubdd_service.mvd_core.api.court.dto.eight;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtBaseCaseRequestDTO;

@Data
@EqualsAndHashCode(callSuper = true)
public class EightCourtResolutionRequestDTO extends CourtBaseCaseRequestDTO {

    private boolean decisionIsActive;
}
