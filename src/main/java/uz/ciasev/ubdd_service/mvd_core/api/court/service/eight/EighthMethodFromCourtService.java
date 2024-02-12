package uz.ciasev.ubdd_service.mvd_core.api.court.service.eight;

import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.eight.EightCourtResolutionRequestDTO;

public interface EighthMethodFromCourtService {

    CourtRequestDTO<EightCourtResolutionRequestDTO> sendResolution(String series, String number);
}
