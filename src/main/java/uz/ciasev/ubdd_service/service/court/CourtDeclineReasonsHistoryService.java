package uz.ciasev.ubdd_service.service.court;

import uz.ciasev.ubdd_service.mvd_core.api.court.dto.second.CourtRegistrationStatusRequestDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.court.CourtDeclineReasonsHistory;

import java.util.List;

public interface CourtDeclineReasonsHistoryService {

    List<CourtDeclineReasonsHistory> findAllByCaseId(Long caseId);

    CourtDeclineReasonsHistory save(AdmCase admCase, CourtRegistrationStatusRequestDTO registration);
}
