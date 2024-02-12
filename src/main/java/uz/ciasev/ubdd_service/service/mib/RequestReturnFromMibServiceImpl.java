package uz.ciasev.ubdd_service.service.mib;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.exception.MibApiApplicationException;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.service.MibApiDTOService;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.service.MibApiService;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.types.MibResult;
import uz.ciasev.ubdd_service.dto.internal.request.mib.MibReturnRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.SendResponse;
import uz.ciasev.ubdd_service.dto.internal.response.adm.mib.MibCardMovementReturnRequestResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.mib.MibSendResponseBuilder;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovementReturnRequest;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.mib.MibCardMovementNotAwaitExecution;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionAccessService;
import uz.ciasev.ubdd_service.utils.validator.Inspector;

@Validated
@Service
@RequiredArgsConstructor
public class RequestReturnFromMibServiceImpl implements RequestReturnFromMibService {
    private final MibCardMovementService movementService;
    private final MibCardService cardService;
    private final DecisionAccessService accessService;
    private final MibCardMovementReturnRequestService returnRequestService;
    private final MibApiService apiService;
    private final MibApiDTOService apiDTOService;


    @Override
    @Transactional
    public SendResponse sendReturnRequest(@Inspector User user, Long cardId, MibReturnRequestDTO requestDTO) {

        MibExecutionCard card = cardService.getById(cardId);
        // Если решение не активное, то мы толком не знаем при каком оно было статусе, так что пусть при любом запрашивают возврат
        // Изначально было сделано, что бы разрешить запрос возврата решений, отмененых на основание 315 статьи.
        if (card.getDecision().getResolution().isActive()) {
            accessService.checkAccess(user, card.getDecision());
        }

        MibCardMovement movement = movementService.getCurrentByCard(card);
        if (!movement.isSent()) {
            throw new MibCardMovementNotAwaitExecution(movement);
        }

        MibCardMovementReturnRequest returnRequest = returnRequestService.create(user, movement, requestDTO.build());

        MibResult sendResult;
        try {
            sendResult = apiService.sendExecutionCardReturnRequest(cardId, apiDTOService.buildReturnRequestDTO(card.getDecision(), returnRequest));
        } catch (MibApiApplicationException e) {
            returnRequestService.saveSendResult(returnRequest, e);
            return MibSendResponseBuilder.of(e);
        }

        returnRequestService.saveSendResult(returnRequest, sendResult.getStatus(), sendResult.getMessage());
        return MibSendResponseBuilder.of(sendResult, () -> new MibCardMovementReturnRequestResponseDTO(returnRequest));

    }
}
