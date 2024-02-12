package uz.ciasev.ubdd_service.service.mib;

import uz.ciasev.ubdd_service.dto.internal.request.notification.ManualNotificationRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.mib.MibCardRequestDTO;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface MibCardService {

    List<MibExecutionCard> findPenaltyAndCompensationByDecisionId(Long decisionId);

    MibExecutionCard getById(Long id);

    MibExecutionCard getByDecisionId(Long decisionId);

    Optional<MibExecutionCard> findByCompensationId(Long compensationId);

    MibExecutionCard getByCompensationId(Long id);

    Optional<MibExecutionCard> findByDecisionId(Long id);

    MibExecutionCard openCardForDecisionByUser(User user, Long decisionId, MibCardRequestDTO requestDTO);

    MibExecutionCard openCardForDecision(User user, Decision decision, MibCardRequestDTO requestDTO);

    MibExecutionCard updateCard(User user, Long id, MibCardRequestDTO requestDTO);

    MibExecutionCard setManualNotification(User user, Long cardId, ManualNotificationRequestDTO requestDTO);

    void prepareSend(MibExecutionCard card);

    MibExecutionCard getCardForEdit(User user, Long decisionId);
}