package uz.ciasev.ubdd_service.dto.internal.request.resolution;

import uz.ciasev.ubdd_service.service.resolution.ResolutionCreateRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface ResolutionRequestDTO {

    List<? extends DecisionRequestDTO> getDecisions();

    ResolutionCreateRequest buildResolution();

    LocalDateTime getResolutionTime();
}
