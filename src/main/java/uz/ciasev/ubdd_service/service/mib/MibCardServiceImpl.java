package uz.ciasev.ubdd_service.service.mib;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.dto.internal.request.notification.ManualNotificationRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.mib.MibCardRequestDTO;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.mib.MibOwnerTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;
import uz.ciasev.ubdd_service.repository.mib.MibExecutionCardRepository;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class MibCardServiceImpl implements MibCardService {

    private final MibExecutionCardRepository cardRepository;
    private final MibValidationService validationService;
    private final DecisionService decisionService;
    private final MibCardNotificationService cardNotificationService;

    @Override
    public List<MibExecutionCard> findPenaltyAndCompensationByDecisionId(Long decisionId) {
        decisionService.checkExistById(decisionId);
        return cardRepository.findPenaltyAndCompensationByDecisionId(decisionId);
    }

    @Override
    public MibExecutionCard getById(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(MibExecutionCard.class, id));
    }

    @Override
    public MibExecutionCard getByDecisionId(Long id) {
        return cardRepository.findByDecisionId(id)
                .orElseThrow(() -> new EntityByParamsNotFound(MibExecutionCard.class, "decisionId", id));
    }

    @Override
    public Optional<MibExecutionCard> findByDecisionId(Long id) {
        return cardRepository.findByDecisionId(id);
    }

    @Override
    @Transactional
    public MibExecutionCard openCardForDecisionByUser(User user, Long decisionId, MibCardRequestDTO requestDTO) {

        Decision decision = decisionService.getById(decisionId);
        validationService.checkCreateAvailable(user, decision);

        MibExecutionCard card = openCardForDecision(user, decision, requestDTO);

        return card;
    }

    @Override
    @Transactional
    public MibExecutionCard openCardForDecision(User user, Decision decision, MibCardRequestDTO requestDTO) {

        if (cardRepository.findByDecisionId(decision.getId()).isPresent()) {
            throw new ValidationException(ErrorCode.MIB_EXECUTION_CARD_ALREADY_EXIST);
        }

        MibExecutionCard card = requestDTO.buildMibExecutionCard();
        card.setDecision(decision);
        card.setOwnerTypeAlias(MibOwnerTypeAlias.DECISION);
        if (user.getId() != null) {
            card.setUser(user);
        }
        //card.setOutNumber("MIB-" + user.getOrgan().getCode() + "-" + decision.getNumber());
        card.setOutNumber("MIB-" + user.getOrganCode() + "-" + decision.getNumber());
        card.setOutDate(LocalDate.now());
        cardNotificationService.setAutoNotification(card);
        MibExecutionCard savedCard = cardRepository.save(card);

//        todo добавить сохранеиеи какрора

        return savedCard;
    }

    @Override
    @Transactional
    public MibExecutionCard updateCard(User user, Long cardId, MibCardRequestDTO requestDTO) {
        MibExecutionCard card = getCardForEdit(user, cardId);

        cardNotificationService.setAutoNotification(card);

        MibExecutionCard savedCard = cardRepository.save(requestDTO.applyTo(card));
        card.setOutDate(LocalDate.now());

        return card;
    }

    @Override
    public void prepareSend(MibExecutionCard card) {
        cardNotificationService.setAutoNotification(card);
    }

    @Override
    public Optional<MibExecutionCard> findByCompensationId(Long compensationId) {
        return cardRepository.findByCompensationId(compensationId);
    }

    @Override
    public MibExecutionCard getByCompensationId(Long compensationId) {
        return findByCompensationId(compensationId)
                .orElseThrow(() -> new EntityByParamsNotFound(MibExecutionCard.class, "compensationId", compensationId));
    }

    @Override
    @Transactional
    public MibExecutionCard setManualNotification(User user,
                                                  Long cardId,
                                                  ManualNotificationRequestDTO requestDTO) {
        MibExecutionCard card = getCardForEdit(user, cardId);
        return cardNotificationService.setManualNotification(user, card, requestDTO);
    }

    @Override
    public MibExecutionCard getCardForEdit(User user, Long cardId) {
        MibExecutionCard card = getById(cardId);

        validationService.checkEditAvailable(user, card);
        return card;
    }

}