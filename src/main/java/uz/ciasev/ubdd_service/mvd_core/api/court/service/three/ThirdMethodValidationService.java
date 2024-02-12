package uz.ciasev.ubdd_service.mvd_core.api.court.service.three;

import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtDefendantRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtEvidenceRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtResolutionRequestDTO;

import java.util.List;

public interface ThirdMethodValidationService {

    void validate(ThirdCourtResolutionRequestDTO resolution);

    void validateEvidences(List<ThirdCourtEvidenceRequestDTO> courtEvidences);

    void validateSecondInstanceFields(List<ThirdCourtDefendantRequestDTO> defendants);

    void validateDecision(ThirdCourtResolutionRequestDTO requestDTO);

    void validateReturnReason(ThirdCourtResolutionRequestDTO defendants);
}
