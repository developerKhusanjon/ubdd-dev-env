package uz.ciasev.ubdd_service.dto.internal.response.adm.resolution;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.response.user.InspectorResponseDTO;
import uz.ciasev.ubdd_service.entity.resolution.cancellation.CancellationResolution;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;

@Getter
public class ResolutionDetailResponseDTO extends ResolutionListResponseDTO {

    private final InspectorResponseDTO user;

    public ResolutionDetailResponseDTO(Resolution resolution, CancellationResolution cancellationResolution, InspectorResponseDTO user) {
        super(resolution, cancellationResolution);
        this.user = user;
    }
}

