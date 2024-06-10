package uz.ciasev.ubdd_service.mvd_core.api.mib.api.service;


import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.request.CourtMibCardMovementSubscribeRequestApiDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.request.ReturnRequestApiDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.types.MibResult;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.response.MibSverkaResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.envelop.MibSendDecisionRequestApiDTO;

public interface MibApiService {

    MibSverkaResponseDTO getMibCase(Long cardId, String serial, String number);

}