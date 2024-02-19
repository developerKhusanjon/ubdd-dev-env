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

    private final MibCardService cardService;
    private final MibValidationService validationService;
    private final DecisionActionService decisionService;
    private final DecisionAccessService decisionAccessService;
    private final MibApiService mibApiService;
    private final MibExecutionCardRepository cardRepository;
    private final PublicApiWebhookEventPopulationService publicApiWebhookEventPopulationService;
    private final MibApiDTOService apiDTOService;
    private final MibCardMovementService cardMovementService;

    @Override
    @Transactional
    @SingleThreadOperation(type = SingleThreadOperationTypeAlias.DECISION_TO_MIB)
    public SendResponse send(User user, @SingleThreadOperationResource Long cardId) {

        MibExecutionCard card = cardService.getById(cardId);
        validationService.checkSendAvailable(user, card);

        Pair<MibCardMovement, MibResult> moveWithStatus;
        try {
            moveWithStatus = doSend(card, user);
        } catch (MibApiApplicationException e) {
            return MibSendResponseBuilder.of(e);
        }

        MibCardMovement move = moveWithStatus.getFirst();
        MibResult sendResult = moveWithStatus.getSecond();

        return MibSendResponseBuilder.of(sendResult, () -> new MibCardResponseDTO(card, move));
    }

    @Override
    public Pair<MibCardMovement, MibResult> doSend(MibExecutionCard card, @Nullable User user) {

        validationService.checkAutoSendPermitOnDecision(user, card.getDecision());

        cardService.prepareSend(card);
        validationService.validateSend(card);
        cardRepository.save(card);

        Pair<MibCardMovement, MibResult> moveWithStatus = sendExecutionCard(user, card);
        MibCardMovement move = moveWithStatus.getFirst();
        MibResult sendResult = moveWithStatus.getSecond();
        MibSendStatus status = sendResult.getStatus();

        if (status.isSuccessfully()) {
            Decision decision = card.getDecision();
            decisionService.saveStatus(decision, AdmStatusAlias.SEND_TO_MIB);
            publicApiWebhookEventPopulationService.addSendToMibEvent(move);
        } else {
            publicApiWebhookEventPopulationService.addValidationMibEvent(move);
        }

        return moveWithStatus;
    }

    @Override
    public void doSend(MibExecutionCard card, @Nullable User user, MibResult mibResult) {

        // cardService.prepareSend(card); // this is for auto Notification
        // validationService.validateSend(card);

        cardRepository.save(card);

        Pair<MibCardMovement, MibResult> moveWithStatus = sendExecutionCard(user, card, mibResult);
        // MibCardMovement move = moveWithStatus.getFirst();
        MibResult sendResult = moveWithStatus.getSecond();
        MibSendStatus status = sendResult.getStatus();

        if (status.isSuccessfully()) {
            Decision decision = card.getDecision();
            decisionService.saveStatus(decision, AdmStatusAlias.SEND_TO_MIB);
            // publicApiWebhookEventPopulationService.addSendToMibEvent(move);
        } // else {
            // publicApiWebhookEventPopulationService.addValidationMibEvent(move);
        // }

    }

    @Override
    public Pair<MibCardMovement, MibResult> doSendManual(MibExecutionCard card, @Nullable User user) {
        cardService.prepareSend(card);
        cardRepository.save(card);
        Pair<MibCardMovement, MibResult> moveWithStatus = sendExecutionCard(user, card);
        MibCardMovement move = moveWithStatus.getFirst();
        MibResult sendResult = moveWithStatus.getSecond();
        MibSendStatus status = sendResult.getStatus();

        if (status.isSuccessfully()) {
            Decision decision = card.getDecision();
            decisionService.saveStatus(decision, AdmStatusAlias.SEND_TO_MIB);
            publicApiWebhookEventPopulationService.addSendToMibEvent(move);
        } else {
            throw new RuntimeException("Request was failed");
        }
        return moveWithStatus;
    }

    @Override
    public MibSendDecisionRequestApiDTO getSendJson(User user, Long mibCardId) {
        MibExecutionCard mibCard = cardService.getById(mibCardId);
        decisionAccessService.checkAccess(user, Optional.ofNullable(mibCard.getDecision()).orElseThrow(
                () -> new ImplementationException(ErrorCode.MIB_CARD_HAS_NO_DECISION)));

        return apiDTOService.buildSendDecisionRequest(mibCard);
    }

    private Pair<MibCardMovement, MibResult> sendExecutionCard(User user, MibExecutionCard card, MibResult mibResponse) {
        MibCardMovement move = new MibCardMovement();
        move.setSendTime(mibResponse.getSendTime());
        move.setMibRequestId(mibResponse.getRequestId());
        move.setSendStatus(mibResponse.getStatus());
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

    private Pair<MibCardMovement, MibResult> sendExecutionCard(User user, MibExecutionCard card) {
        MibSendDecisionRequestApiDTO request = apiDTOService.buildSendDecisionRequest(card);
        MibResult mibResponse = mibApiService.sendExecutionCard(card.getId(), request);
        MibCardMovement move = new MibCardMovement();
        move.setSendTime(LocalDateTime.now());
        move.setMibRequestId(mibResponse.getRequestId());
        move.setSendStatus(mibResponse.getStatus());
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