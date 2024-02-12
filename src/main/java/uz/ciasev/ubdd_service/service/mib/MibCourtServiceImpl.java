package uz.ciasev.ubdd_service.service.mib;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.dto.internal.request.mib.CourtMibCardRequestDTO;
import uz.ciasev.ubdd_service.entity.mib.MibOwnerTypeAlias;
import uz.ciasev.ubdd_service.entity.mib.*;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.evidence_decision.EvidenceDecision;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.repository.mib.EvidenceDecisionMibRepository;
import uz.ciasev.ubdd_service.repository.mib.MibExecutionCardRepository;
import uz.ciasev.ubdd_service.repository.resolution.evidence_decision.EvidenceDecisionRepository;
import uz.ciasev.ubdd_service.service.dict.mib.MibSendStatusService;
import uz.ciasev.ubdd_service.service.resolution.compensation.CompensationActionService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionActionService;
import uz.ciasev.ubdd_service.service.status.StatusService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MibCourtServiceImpl implements MibCourtService {
    private final MibCardMovementService cardMovementService;
    private final DecisionActionService decisionService;
    private final StatusService admStatusService;
    private final MibExecutionCardRepository cardRepository;
    private final EvidenceDecisionRepository evidenceDecisionRepository;
    private final CompensationActionService compensationService;
    private final MibSendStatusService mibSendStatusService;
    private final EvidenceDecisionMibRepository evidenceDecisionMibRepository;

    @Override
    public boolean isMibRequestIdProcessed(Long mibRequestId) {
        return cardMovementService.existsByMibRequestId(mibRequestId);
    }

    @Override
    @Transactional
    public MibCardMovement openCourtMibCard(List<EvidenceDecision> evidenceDecisions, @Validated CourtMibCardRequestDTO requestDTO) {

        checkCourtMibCard(requestDTO);

        MibExecutionCard card = requestDTO.buildMibExecutionCard();
        card.setOwnerTypeAlias(MibOwnerTypeAlias.EVIDENCE_DECISION);
        card = cardRepository.save(card);

        for (EvidenceDecision evidenceDecision : evidenceDecisions) {
            EvidenceDecisionMib edm = new EvidenceDecisionMib();
            edm.setCard(card);
            edm.setEvidenceDecision(evidenceDecision);
            edm.setIsActive(true);
            evidenceDecisionMibRepository.save(edm);
        }

        MibCardMovement movement = createAndSaveMibCardMovementFromCourt(card, requestDTO);

        setEvidenceStatuses(movement, AdmStatusAlias.SEND_TO_MIB);

        return movement;
    }

    @Override
    @Transactional
    public void openCourtMibCard(Decision decision, @Validated CourtMibCardRequestDTO requestDTO) {

        checkCourtMibCard(requestDTO);

        MibExecutionCard card = cardRepository.findByDecisionId(decision.getId()).map(requestDTO::applyTo).orElseGet(() -> {
            MibExecutionCard newCard = requestDTO.buildMibExecutionCard();
            newCard.setOwnerTypeAlias(MibOwnerTypeAlias.DECISION);
            newCard.setDecision(decision);
            return newCard;
        });

        cardRepository.save(card);

        decisionService.saveStatus(decision, AdmStatusAlias.SEND_TO_MIB);

        createAndSaveMibCardMovementFromCourt(card, requestDTO);
    }

    @Override
    @Transactional
    public void openCourtMibCard(Compensation compensation, @Validated CourtMibCardRequestDTO requestDTO) {

        checkCourtMibCard(requestDTO);

        MibExecutionCard card = cardRepository.findByCompensationId(compensation.getId()).map(requestDTO::applyTo).orElseGet(() -> {
            MibExecutionCard newCard = requestDTO.buildMibExecutionCard();
            newCard.setOwnerTypeAlias(MibOwnerTypeAlias.COMPENSATION);
            newCard.setCompensation(compensation);
            return newCard;
        });

        cardRepository.save(card);

        admStatusService.addStatus(compensation, AdmStatusAlias.SEND_TO_MIB);
        compensationService.refreshStatusAndSave(compensation);

        createAndSaveMibCardMovementFromCourt(card, requestDTO);
    }

    private MibCardMovement createAndSaveMibCardMovementFromCourt(MibExecutionCard card, CourtMibCardRequestDTO requestDTO) {

        MibCardMovement movement = new MibCardMovement();
        movement.setMibRequestId(requestDTO.getMibRequestId());
        movement.setSendTime(requestDTO.getSendTime());
        movement.setSendStatus(mibSendStatusService.getSuccessfully());
        movement.setIsCourt(true);
        return cardMovementService.create(null, card, movement);
    }

    private void setEvidenceStatuses(MibCardMovement movement, AdmStatusAlias statusAlias) {
        List<Long> decisionsIds = evidenceDecisionMibRepository.findActivesByCard(movement.getCardId());

        for (Long id : decisionsIds) {
            EvidenceDecision ed = evidenceDecisionRepository.findById(id).get();
            admStatusService.setStatus(ed, statusAlias);
            evidenceDecisionRepository.save(ed);
        }
    }

    private void checkCourtMibCard(CourtMibCardRequestDTO requestDTO) {
        if (cardMovementService.existsByMibRequestId(requestDTO.getMibRequestId())) {
            throw new ValidationException(ErrorCode.MIB_REQUEST_ID_EXIST);
        }
    }
}