package uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.service.execution;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.execution.MibPayedAmountRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatus;
import uz.ciasev.ubdd_service.entity.mib.MibOwnerTypeAlias;
import uz.ciasev.ubdd_service.entity.mib.*;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;
import uz.ciasev.ubdd_service.service.publicapi.eventdata.PublicApiWebhookEventPopulationService;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.execution.MibRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.execution.MvdExecutionResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatusAlias;
import uz.ciasev.ubdd_service.repository.mib.MibCardMovementHistoryRepository;
import uz.ciasev.ubdd_service.service.mib.MibCardMovementService;
import uz.ciasev.ubdd_service.service.mib.MibExecutionService;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.function.Consumer;
import java.util.function.Function;

import static uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatusAlias.*;

@Slf4j
@Service
public class MibApiExecutionServiceImpl implements MibApiExecutionService {

    private final MibCardMovementService cardMovementService;
    private final EnumMap<MibCaseStatusAlias, StatusHandler> statusHandlerMap;
    private final EnumMap<MibOwnerTypeAlias, Consumer<MibCardMovement>> returnedMap;
    private final EnumMap<MibOwnerTypeAlias, Consumer<MibCardMovement>> cancelExecutionMap;
    private final EnumMap<MibOwnerTypeAlias, Function<MibCardMovement, Pair<Long, Long>>> acceptedMap;
    private final EnumMap<MibOwnerTypeAlias, Function<MibCardMovement, Pair<Long, Long>>> paidDataMap;
    private final EnumMap<MibOwnerTypeAlias, Consumer<MibCardMovement>> executedMap;
    private final PublicApiWebhookEventPopulationService publicApiWebhookEventPopulationService;
    private final MibApiExecutionDocumentFileProcessing mibApiExecutionDocumentFileProcessing;

    private final MibCardMovementHistoryRepository historyRepository;

    @Autowired
    public MibApiExecutionServiceImpl(MibExecutionService mibService,
                                      MibCardMovementService cardMovementService,
                                      PublicApiWebhookEventPopulationService publicApiWebhookEventPopulationService,
                                      MibApiExecutionDocumentFileProcessing mibApiExecutionDocumentFileProcessing, MibCardMovementHistoryRepository historyRepository) {

        this.cardMovementService = cardMovementService;
        this.publicApiWebhookEventPopulationService = publicApiWebhookEventPopulationService;
        this.mibApiExecutionDocumentFileProcessing = mibApiExecutionDocumentFileProcessing;
        this.historyRepository = historyRepository;

        returnedMap = new EnumMap<>(MibOwnerTypeAlias.class);
        returnedMap.put(MibOwnerTypeAlias.DECISION, mibService::returnDecisionFromMib);
        returnedMap.put(MibOwnerTypeAlias.COMPENSATION, mibService::returnCompensationFromMib);
        returnedMap.put(MibOwnerTypeAlias.EVIDENCE_DECISION, mibService::returnEvidenceFromMib);

        acceptedMap = new EnumMap<>(MibOwnerTypeAlias.class);
        acceptedMap.put(MibOwnerTypeAlias.DECISION, mibService::registerDecisionInMib);
        acceptedMap.put(MibOwnerTypeAlias.COMPENSATION, mibService::registerCompensationInMib);
        acceptedMap.put(MibOwnerTypeAlias.EVIDENCE_DECISION, m -> Pair.of(0L, 0L));

        paidDataMap = new EnumMap<>(MibOwnerTypeAlias.class);
        paidDataMap.put(MibOwnerTypeAlias.DECISION, mibService::getPaidDataForDecision);
        paidDataMap.put(MibOwnerTypeAlias.COMPENSATION, mibService::getPaidDataCompensation);
        paidDataMap.put(MibOwnerTypeAlias.EVIDENCE_DECISION, m -> Pair.of(0L, 0L));

        executedMap = new EnumMap<>(MibOwnerTypeAlias.class);
        executedMap.put(MibOwnerTypeAlias.DECISION, mibService::executeDecisionByMib);
        executedMap.put(MibOwnerTypeAlias.COMPENSATION, mibService::executeCompensationByMib);
        executedMap.put(MibOwnerTypeAlias.EVIDENCE_DECISION, mibService::executeEvidenceByMib);

        cancelExecutionMap = new EnumMap<>(MibOwnerTypeAlias.class);
        cancelExecutionMap.put(MibOwnerTypeAlias.DECISION, mibService::cancelExecuteDecisionByMib);
        cancelExecutionMap.put(MibOwnerTypeAlias.COMPENSATION, mibService::cancelExecuteCompensationByMib);
        cancelExecutionMap.put(MibOwnerTypeAlias.EVIDENCE_DECISION, mibService::cancelExecuteEvidenceByMib);

        this.statusHandlerMap = new EnumMap<>(MibCaseStatusAlias.class);
        statusHandlerMap.put(TERMINATION, this::cancelExecution);
        statusHandlerMap.put(ACCEPTED, this::accepted);
        statusHandlerMap.put(PAID, this::paid);
        statusHandlerMap.put(RETURN, this::returned);
        statusHandlerMap.put(EXECUTED, this::executed);
    }

    @Override
    @Transactional(timeout = 90)
    public MvdExecutionResponseDTO executionResultWebhook(Long requestId, MibRequestDTO result) {
// TODO: 10.11.2023 start mib null exception
        MibCardMovement movement = cardMovementService.getByMibRequestId(requestId);
        cardMovementService.validateActiveForHandelExecutionStatus(movement);
// TODO: 06.11.2023
//        checked
        historyRepository.save(new MibCardMovementHistory(movement, result));

//        if (!movement.isActive()) {
//            throw new ValidationException(ErrorCode.MIB_MOVEMENT_IS_NOT_ACTIVE);
////            response = new MvdExecutionResponseDTO(-1L);
//        }

        MibCaseStatus caseStatus = result.getExecutionCaseStatus();
        if (caseStatus == null) {
            throw new RuntimeException("Case status of the mib request dto is null");
        }
        movement.setMibCaseNumber(result.getExecutionCaseNumber());
        movement.setMibCaseStatus(caseStatus);
        MvdExecutionResponseDTO response;
        try {
            response = statusHandlerMap.get(caseStatus.getAlias()).handle(movement, result);
        } catch (NullPointerException e) {
            throw new RuntimeException("statusHandlerMap.get throw NullPointer exception");
        }

        try {
            cardMovementService.update(movement);
        } catch (NullPointerException e) {
            throw new RuntimeException("cardMovementService.update throw NullPointer exception");
        }

        //Порядок изменнен, чт бы не грузить файлы на диск если при обрабоке заросы произойде ошибка.
        try {
            mibApiExecutionDocumentFileProcessing.process(movement, result);
        } catch (NullPointerException e) {
            throw new RuntimeException("mibApiExecutionDocumentFileProcessing.process throw NullPointer exception");
        }

        return response;
    }

    @Override
    public MvdExecutionResponseDTO getPayedAmount(Long requestId, MibPayedAmountRequestDTO result) {
        MibCardMovement movement = cardMovementService.getByMibRequestId(requestId);

        Pair<Long, Long> payedAmount = getConsumer(movement, paidDataMap)
                .apply(movement);

        // Оплаченная сумма с учетом только Ягана биллинг
        return new MvdExecutionResponseDTO(payedAmount.getSecond());
    }

    private MvdExecutionResponseDTO accepted(MibCardMovement movement, MibRequestDTO result) {
        // TODO: 10.11.2023 mib next 
        movement.setAcceptTime(LocalDateTime.now());
        Pair<Long, Long> payedAmount;
        try {
            payedAmount = getConsumer(movement, acceptedMap)
                    .apply(movement);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()+"\nMibApiExecutionServiceImpl accepted getConsumer");
        }

       if (payedAmount==null){
           throw new RuntimeException("Payed amount is null");
       }

        // Оплаченная сумма с учетом ручного биллинга
        movement.setAmountOfRecovery(payedAmount.getFirst());

        publicApiWebhookEventPopulationService.addMibAcceptEvent(movement);

        // Оплаченная сумма с учетом только Ягана биллинг
        return new MvdExecutionResponseDTO(payedAmount.getSecond());
    }

    private MvdExecutionResponseDTO paid(MibCardMovement movement, MibRequestDTO result) {

        movement.setPayments(result.buildPaymentsData());
        movement.setTotalRecoveredAmount(result.buildPaymentsData().stream().mapToLong(PaymentData::getAmount).sum());

        publicApiWebhookEventPopulationService.addMibPaidEvent(movement);
        return new MvdExecutionResponseDTO();
    }

    private MvdExecutionResponseDTO returned(MibCardMovement movement, MibRequestDTO result) {

        movement.setReturnTime(LocalDateTime.now());

        getConsumer(movement, returnedMap)
                .accept(movement);

        publicApiWebhookEventPopulationService.addMibReturnEvent(movement);
        return new MvdExecutionResponseDTO();
    }

    private MvdExecutionResponseDTO executed(MibCardMovement movement, MibRequestDTO result) {

        movement.setReturnTime(LocalDateTime.now());
        movement.setPayments(result.buildPaymentsData());
        movement.setTotalRecoveredAmount(result.buildPaymentsData().stream().mapToLong(PaymentData::getAmount).sum());

        getConsumer(movement, executedMap)
                .accept(movement);

        publicApiWebhookEventPopulationService.addMibExecuteEvent(movement);

        return new MvdExecutionResponseDTO();
    }

    private MvdExecutionResponseDTO cancelExecution(MibCardMovement movement, MibRequestDTO result) {

        movement.setPayments(null);
        movement.setReturnTime(null);
        movement.setAcceptTime(null);
        movement.setAmountOfRecovery(null);
        movement.setTotalRecoveredAmount(null);

        getConsumer(movement, cancelExecutionMap)
                .accept(movement);

        publicApiWebhookEventPopulationService.addMibCancelExecutionEvent(movement);

        // Повторить регистрацию дела в МИБ, так как миб будет повторно работать с делом.
        return accepted(movement, result);
    }

    private MvdExecutionResponseDTO ignore(MibCardMovement movement, MibRequestDTO result) {
        return new MvdExecutionResponseDTO();
    }

    private <T> T getConsumer(MibCardMovement movement, EnumMap<MibOwnerTypeAlias, T> map) {

        MibExecutionCard card = movement.getCard();

        T consumer = map.get(card.getOwnerTypeAlias());
        if (consumer == null) {
            throw new NotImplementedException(String.format("Mib consumer for card of %s", card.getOwnerTypeAlias()));
        }

        return consumer;
    }


    @FunctionalInterface
    public interface StatusHandler {
        MvdExecutionResponseDTO handle(MibCardMovement movement, MibRequestDTO result);
    }
}
