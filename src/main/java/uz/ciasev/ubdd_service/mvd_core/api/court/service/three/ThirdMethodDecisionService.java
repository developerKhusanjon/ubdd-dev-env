package uz.ciasev.ubdd_service.mvd_core.api.court.service.three;

import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtResolutionRequestDTO;

public interface ThirdMethodDecisionService {

    void handleFirstInstance(ThirdCourtResolutionRequestDTO requestDTO);

    void handleOtherInstance(ThirdCourtResolutionRequestDTO requestDTO, Long claimId);
}
