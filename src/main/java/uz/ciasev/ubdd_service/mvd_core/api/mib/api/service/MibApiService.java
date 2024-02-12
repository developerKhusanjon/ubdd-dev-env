package uz.ciasev.ubdd_service.mvd_core.api.mib.api.service;


import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.request.CourtMibCardMovementSubscribeRequestApiDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.request.ReturnRequestApiDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.types.MibResult;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.response.MibSverkaResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.envelop.MibSendDecisionRequestApiDTO;

public interface MibApiService {

    MibResult sendExecutionCard(Long cardId, MibSendDecisionRequestApiDTO requestDTO);

    MibResult sendSubscribeOnCourtEnvelope(Long cardId, CourtMibCardMovementSubscribeRequestApiDTO requestDTO);

    MibSverkaResponseDTO getMibCase(Long cardId, String serial, String number);

    MibResult sendExecutionCardReturnRequest(Long cardId, ReturnRequestApiDTO requestDTO);
}