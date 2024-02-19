package uz.ciasev.ubdd_service.service.mib;

import org.springframework.data.util.Pair;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.envelop.MibSendDecisionRequestApiDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.types.MibResult;
import uz.ciasev.ubdd_service.dto.internal.response.SendResponse;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.user.User;

public interface SendToMibService {

    SendResponse send(User user, Long cardId);

    Pair<MibCardMovement, MibResult> doSend(MibExecutionCard card, User user);

    void doSend(MibExecutionCard card, User user, MibResult mibResult);

    Pair<MibCardMovement, MibResult> doSendManual(MibExecutionCard card, User user);

    MibSendDecisionRequestApiDTO getSendJson(User user, Long mibCardId);
}
