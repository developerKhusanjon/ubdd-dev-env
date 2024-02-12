package uz.ciasev.ubdd_service.dto.internal.request.resolution.organ;

import uz.ciasev.ubdd_service.service.resolution.ResolutionCreateRequest;
import uz.ciasev.ubdd_service.utils.validator.ValidDecision;

@ValidDecision
public class SimplifiedSingleResolutionRequestDTO extends SingleResolutionRequestDTO {

    public ResolutionCreateRequest buildResolution() {
        ResolutionCreateRequest resolution = super.buildResolution();
        resolution.setSimplified(true);
        return resolution;
    }
}
