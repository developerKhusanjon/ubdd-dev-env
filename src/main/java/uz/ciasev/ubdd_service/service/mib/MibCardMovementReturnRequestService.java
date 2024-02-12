package uz.ciasev.ubdd_service.service.mib;

import uz.ciasev.ubdd_service.mvd_core.api.mib.api.exception.MibApiApplicationException;
import uz.ciasev.ubdd_service.entity.dict.mib.MibSendStatus;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovementReturnRequest;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.annotation.Nullable;
import java.util.List;

public interface MibCardMovementReturnRequestService {

    List<MibCardMovementReturnRequest> findByCardId(Long cardId);

    List<MibCardMovementReturnRequest> findByMovementId(Long movementId);

    MibCardMovementReturnRequest create(@Nullable User user, MibCardMovement movement, MibCardMovementReturnRequest.CreateRequest request);

    MibCardMovementReturnRequest saveSendResult(MibCardMovementReturnRequest request, MibSendStatus sendStatus, String sendMessage);

    MibCardMovementReturnRequest saveSendResult(MibCardMovementReturnRequest request, MibApiApplicationException sendError);

    MibCardMovementReturnRequest getById(Long id);
}
