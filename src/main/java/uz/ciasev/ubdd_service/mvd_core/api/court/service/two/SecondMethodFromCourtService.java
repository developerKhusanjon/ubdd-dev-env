package uz.ciasev.ubdd_service.mvd_core.api.court.service.two;

import uz.ciasev.ubdd_service.mvd_core.api.court.dto.second.CourtRegistrationStatusRequestDTO;

public interface SecondMethodFromCourtService {

    void accept(CourtRegistrationStatusRequestDTO requestDTO);
}
