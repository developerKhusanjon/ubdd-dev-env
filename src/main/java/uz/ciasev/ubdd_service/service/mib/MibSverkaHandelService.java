package uz.ciasev.ubdd_service.service.mib;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.exception.MibApiCaseStatusEmptyException;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.exception.MibApiResultCodeException;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.service.execution.MibApiExecutionService;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.service.MibApiService;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.response.MibSverkaResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.mib.MibSendStatus;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MibSverkaHandelService {

    private final MibCardMovementService cardMovementService;
    private final MibApiExecutionService mibApiExecutionService;
    private final MibApiService mibApiSendService;


    @Transactional
    public void handel(MibExecutionCard card, String gaiSerial, String gaiNumber, Long envelopeIdFromFile) {
        MibSverkaResponseDTO mibDate = mibApiSendService.getMibCase(card.getId(), gaiSerial, gaiNumber);

        if (!mibDate.isSuccessfully()) {
//            throw new LogicalException(String.format("Mib error: %s - %s", mibDate.getResultCode(), mibDate.getResultMsg()));
            throw new MibApiResultCodeException(mibDate);
        }

        if (mibDate.getExecutionCaseStatus() == null) {
//            throw new LogicalException("Mib case status is null");
            throw new MibApiCaseStatusEmptyException();
        }

        mibDate.setDocument(null);
//        if (mibDate.getExecutionCaseStatus().notOneOf(MibCaseStatusAlias.RETURN, MibCaseStatusAlias.EXECUTED)) {
//            mibDate.setDocument(null);
//        }

        Long mibRequestId = getOrCreateMibRequestId(card, envelopeIdFromFile, mibDate.getEnvelopeId());

        mibApiExecutionService.executionResultWebhook(mibRequestId, mibDate);

        cardMovementService.setSynced(mibRequestId, mibDate.getEnvelopeId());
    }


    private Long getOrCreateMibRequestId(MibExecutionCard card, Long envelopeIdFromFile, Long envelopeIdFromMib) {
        Optional<MibCardMovement> lastMov = cardMovementService.findCurrentByCard(card);
        if (lastMov.map(MibCardMovement::getSendStatusId).map(MibSendStatus::isSuccessfully).orElse(false)) {
            return lastMov.get().getMibRequestId();
        }

        if (cardMovementService.existsByMibRequestId(envelopeIdFromMib)) {
            return envelopeIdFromMib;
        }

        Long envelopeId = envelopeIdFromFile == null ? envelopeIdFromMib : envelopeIdFromFile;

        MibCardMovement movement = new MibCardMovement();
        movement.setMibRequestId(envelopeId);
        movement.setSendTime(LocalDateTime.of(2022, 6, 22, 22, 4, 0));
        movement.setSendStatusId(1L);
        movement.setIsCourt(false);

        MibCardMovement newMov = cardMovementService.create(null, card, movement);

        return newMov.getMibRequestId();
    }
}
