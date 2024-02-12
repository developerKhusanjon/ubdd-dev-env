package uz.ciasev.ubdd_service.mvd_core.api.court.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.nine.CourtVictimRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.nine.CourtVictimRequestExternalDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtResolutionRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;

@Service
@RequiredArgsConstructor
public class CourtRequestMapperFacade {

    private final CourtRequestMapper<ThirdCourtResolutionRequestDTO, ThirdCourtRequest> mapperRequest;
    private final CourtRequestMapper<CourtVictimRequestExternalDTO, CourtVictimRequestDTO> mapperVictim;

    public ThirdCourtRequest map(ThirdCourtResolutionRequestDTO requestDTO) {
        return mapperRequest.map(requestDTO);
    }

    public CourtVictimRequestDTO map(CourtVictimRequestExternalDTO requestDTO) {
        return mapperVictim.map(requestDTO);
    }
}
