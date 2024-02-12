package uz.ciasev.ubdd_service.service.court.methods;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.UbddCourtRequest;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtResolutionRequestDTO;

public interface UbddCourtService {

    void sentCourt(UbddCourtRequest request);

    CourtResponseDTO courtResolution(CourtRequestDTO<ThirdCourtResolutionRequestDTO> requestDTO);

}
