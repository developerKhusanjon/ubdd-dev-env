package uz.ciasev.ubdd_service.service.mib;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.exception.MibApiApplicationException;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.service.MibApiService;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.service.MibApiDTOService;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.types.MibResult;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.repository.mib.MibCardMovementRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MibCourtMovementSubscriptionService {

    private final MibCardMovementRepository repository;
    private final MibApiService apiService;
    private final MibApiDTOService dtoService;
    private final MibCardMovementService cardMovementService;

    public void subscribe() {
        List<MibCardMovement> movements = repository.findAllUnsubscribedFromCourt();

        movements.forEach(movement -> {
            sendEnvelope(movement);
            try {
                Thread.sleep(10);
            } catch (Exception ignored) {}
        });
    }

    private void sendEnvelope(MibCardMovement mibCardMovement) {

        MibResult result;

        try {
            result = apiService.sendSubscribeOnCourtEnvelope(mibCardMovement.getCardId(), dtoService.buildCourtSubscribeRequestDTO(mibCardMovement));
        } catch (MibApiApplicationException e) {
            mibCardMovement.setCourtMovementSubscriptionLastAttemptTime(LocalDateTime.now());
            mibCardMovement.setCourtMovementSubscriptionLastAttemptMessage(e.getMessage());
            cardMovementService.update(mibCardMovement);
            return;
        }

        boolean isResponseOk = result.getStatus().isSuccessfully()
                || "-57".equals(result.getStatus().getCode());

        if (isResponseOk) {
            mibCardMovement.setIsSubscribedCourtMovement(true);
            mibCardMovement.setCourtMovementSubscriptionTime(LocalDateTime.now());
        }

        mibCardMovement.setCourtMovementSubscriptionLastAttemptTime(LocalDateTime.now());
        mibCardMovement.setCourtMovementSubscriptionLastAttemptMessage(result.getMessage());

        cardMovementService.update(mibCardMovement);
    }

}
