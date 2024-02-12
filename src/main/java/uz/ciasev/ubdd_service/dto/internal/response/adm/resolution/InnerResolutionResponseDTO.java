package uz.ciasev.ubdd_service.dto.internal.response.adm.resolution;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.resolution.cancellation.CancellationResolution;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;


@Getter
public class InnerResolutionResponseDTO extends AbstractResolutionResponseDTO {

    private final Long resolutionId;
    private final Boolean isResolutionActive;

    public InnerResolutionResponseDTO(Resolution resolution, CancellationResolution cancellation) {
        super(resolution, cancellation);
        this.resolutionId = resolution.getId();
        this.isResolutionActive = resolution.isActive();
    }
}

