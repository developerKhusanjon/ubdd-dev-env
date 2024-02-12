package uz.ciasev.ubdd_service.service.court.methods;

import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtResponseDTO;

public interface CourtHandlingResponseService {

    void handleResponse(CourtResponseDTO response, Long caseId, boolean is308, boolean isSentToCourt);
}
