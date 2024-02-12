package uz.ciasev.ubdd_service.event.subscribers.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.notification.sms.SystemUserNotificationBroadcastRequestDTO;
import uz.ciasev.ubdd_service.entity.court.CourtMaterial;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialDecision;
import uz.ciasev.ubdd_service.event.subscribers.RegisterMaterialInCourtSubscriber;
import uz.ciasev.ubdd_service.repository.court.CourtMaterialDecisionRepository;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;
import uz.ciasev.ubdd_service.service.notification.system.SystemUserNotificationMessageService;
import uz.ciasev.ubdd_service.service.notification.system.SystemUserNotificationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourtMaterial315SystemUserNotificationSubscriber extends RegisterMaterialInCourtSubscriber {

    private final SystemUserNotificationService systemUserNotificationService;
    private final SystemUserNotificationMessageService systemUserNotificationMessageService;
    private final NotificationTypeAlias notificationTypeAlias = NotificationTypeAlias.REGISTERED_MATERIAL_315_IN_COURT;
    private final CourtMaterialDecisionRepository courtMaterialDecisionRepository;

    @Override
    public void apply(CourtMaterial material) {
        List<CourtMaterialDecision> decisionsInMaterial = courtMaterialDecisionRepository.findAllByCourtMaterialId(material.getId());

        decisionsInMaterial
                .stream()
                .filter(dInM -> !dInM.getDecision().getIsCourt())
                .map(CourtMaterialDecision::getDecision)
                .forEach(decision -> {
                    String messageText = systemUserNotificationMessageService.buildText(notificationTypeAlias, decision);
                    SystemUserNotificationBroadcastRequestDTO notificationRequest = new SystemUserNotificationBroadcastRequestDTO(decision.getResolution().getAdmCase(), decision, notificationTypeAlias, messageText);

                    systemUserNotificationService.sendBroadcast(notificationRequest);
                });
    }
}
