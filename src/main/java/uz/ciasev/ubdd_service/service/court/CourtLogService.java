package uz.ciasev.ubdd_service.service.court;

import uz.ciasev.ubdd_service.mvd_core.api.court.CourtSendResult;
import uz.ciasev.ubdd_service.mvd_core.api.court.CourtMethod;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtBaseDTO;
import uz.ciasev.ubdd_service.entity.court.CourtLog;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

public interface CourtLogService {

    CourtLog save(CourtBaseDTO requestDTO, CourtMethod method);

    CourtLog save(Long caseId, CourtMethod method, CourtSendResult obj);

    boolean hasSearchRequest(Decision decision);
}
