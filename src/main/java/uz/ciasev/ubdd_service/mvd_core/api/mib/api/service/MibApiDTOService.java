package uz.ciasev.ubdd_service.mvd_core.api.mib.api.service;

import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.request.CourtMibCardMovementSubscribeRequestApiDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.envelop.MibSendDecisionRequestApiDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.request.ReturnRequestApiDTO;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovementReturnRequest;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

public interface MibApiDTOService {

    MibSendDecisionRequestApiDTO buildSendDecisionRequest(MibExecutionCard card);

    CourtMibCardMovementSubscribeRequestApiDTO buildCourtSubscribeRequestDTO(MibCardMovement movement);

    ReturnRequestApiDTO buildReturnRequestDTO(Decision decision, MibCardMovementReturnRequest returnRequest);
}
