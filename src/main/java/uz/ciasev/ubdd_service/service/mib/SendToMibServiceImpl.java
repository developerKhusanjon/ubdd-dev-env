package uz.ciasev.ubdd_service.service.mib;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.util.Pair;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.exception.MibApiApplicationException;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.service.MibApiService;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.service.MibApiDTOService;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.types.MibResult;
import uz.ciasev.ubdd_service.config.base.CiasevDBConstraint;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.envelop.MibSendDecisionRequestApiDTO;
import uz.ciasev.ubdd_service.dto.internal.response.SendResponse;
import uz.ciasev.ubdd_service.dto.internal.response.adm.mib.MibCardResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.mib.MibSendResponseBuilder;
import uz.ciasev.ubdd_service.entity.dict.mib.MibSendStatus;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.single_thread_operation.SingleThreadOperationTypeAlias;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.MibRequestIdAlreadyExists;
import uz.ciasev.ubdd_service.exception.implementation.ImplementationException;
import uz.ciasev.ubdd_service.service.publicapi.eventdata.PublicApiWebhookEventPopulationService;
import uz.ciasev.ubdd_service.repository.mib.MibExecutionCardRepository;
import uz.ciasev.ubdd_service.service.aop.single_thread_operation.SingleThreadOperation;
import uz.ciasev.ubdd_service.service.aop.single_thread_operation.SingleThreadOperationResource;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionAccessService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionActionService;
import uz.ciasev.ubdd_service.utils.DBHelper;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class SendToMibServiceImpl implements SendToMibService {

    private final DecisionActionService decisionService;
    private final MibExecutionCardRepository cardRepository;
    private final MibCardMovementService cardMovementService;


    @Override
    public void doSend(MibExecutionCard card, @Nullable User user, MibResult mibResult) {

        cardRepository.save(card);

        sendExecutionCard(user, card, mibResult);

        Decision decision = card.getDecision();
        decisionService.saveStatus(decision, AdmStatusAlias.SEND_TO_MIB);

    }


    private Pair<MibCardMovement, MibResult> sendExecutionCard(User user, MibExecutionCard card, MibResult mibResponse) {
        MibCardMovement move = new MibCardMovement();
        move.setSendTime(mibResponse.getSendTime());
        move.setMibRequestId(mibResponse.getRequestId());
        move.setSendStatusId(1L);
        move.setSendMessage(mibResponse.getMessage());

        try {
            cardMovementService.create(user, card, move);
        } catch (DataIntegrityViolationException e) {
            if (DBHelper.isConstraintViolation(e, CiasevDBConstraint.UniqueMibCardMovementRequestId)) {
                throw new MibRequestIdAlreadyExists();
            }
            throw e;
        }
        return Pair.of(move, mibResponse);
    }
}