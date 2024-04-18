package uz.ciasev.ubdd_service.service.webhook.ibd;

import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.event.AdmEventType;

public interface IBDWebhookCreateService {

    void createWebhooks(AdmCase admCase);

    void createWebhooks(Long admCaseId, AdmEventType eventType);

    void createWebhooksByViolatorId(Long violatorId);

    void createWebhooks(Decision decision);

    void createWebhook(Protocol protocol);

    void createWebhook(Protocol protocol, AdmEventType eventType);
}
