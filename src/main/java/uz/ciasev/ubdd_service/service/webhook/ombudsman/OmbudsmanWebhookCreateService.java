package uz.ciasev.ubdd_service.service.webhook.ombudsman;

import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.event.AdmEventType;

public interface OmbudsmanWebhookCreateService {

    void createWebhooks(AdmCase admCase, AdmEventType type);

    void createWebhooksByViolatorId(Long violatorId, AdmEventType type);

    void createWebhooks(Decision decision, AdmEventType type);

    void createWebhook(Protocol protocol, AdmEventType type);
}
