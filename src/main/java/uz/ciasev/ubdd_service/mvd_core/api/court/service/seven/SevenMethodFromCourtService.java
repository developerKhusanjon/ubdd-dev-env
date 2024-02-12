package uz.ciasev.ubdd_service.mvd_core.api.court.service.seven;

import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.seventh.CourtDecisionFileRequestDTO;

public interface SevenMethodFromCourtService {

    void accept(CourtRequestDTO<CourtDecisionFileRequestDTO> requestDTO);
}
