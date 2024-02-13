package uz.ciasev.ubdd_service.mvd_core.api.court.service.three;

import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtResolutionRequestDTO;

public interface UbddCourtResolutionService {

    void accept(ThirdCourtResolutionRequestDTO requestDTO);
}
