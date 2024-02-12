package uz.ciasev.ubdd_service.event.subscribers.sms;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.event.subscribers.OrganResolutionCreateSubscriber;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedResolutionDTO;
import uz.ciasev.ubdd_service.service.notification.sms.SmsNotificationDTOService;
import uz.ciasev.ubdd_service.service.notification.sms.SmsNotificationService;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DecisionSmsAdmEventSubscriber extends OrganResolutionCreateSubscriber {

    private final SmsNotificationService smsNotificationService;
    private final SmsNotificationDTOService smsNotificationDTOService;

    @Override
    public void apply(CreatedResolutionDTO createdResolution) {
        Resolution resolution = createdResolution.getResolution();

        Organ organ = resolution.getOrgan();
        if (!Optional.ofNullable(resolution.getOrgan())
                .map(Organ::getSmsNotification)
                .orElse(false)) {
            return;
        }

        createdResolution.getCreatedDecisions()
                .stream()
                .map(createdDecision -> {

                    Decision decision = createdDecision.getDecision();

                    if (decision.getPenalty().isPresent()) {
                        return smsNotificationDTOService.makePenaltyDecisionDTO(decision, createdDecision.getCompensations(), organ);
                    }

                    return null;
                })
                .filter(Objects::nonNull)
                .forEach(smsNotificationService::sendSms);

    }
}
