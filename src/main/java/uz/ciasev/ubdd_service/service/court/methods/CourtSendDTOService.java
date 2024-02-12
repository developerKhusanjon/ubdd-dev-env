package uz.ciasev.ubdd_service.service.court.methods;

import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.first.FirstCourtAdmCaseRequestDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;

public interface CourtSendDTOService {

    CourtRequestDTO<FirstCourtAdmCaseRequestDTO> buildFirstMethod(AdmCase admCase);
}
