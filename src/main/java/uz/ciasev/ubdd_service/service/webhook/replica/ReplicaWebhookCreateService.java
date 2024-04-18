package uz.ciasev.ubdd_service.service.webhook.replica;

import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

public interface ReplicaWebhookCreateService {

    void createWebhooks(AdmCase admCase);

    void createWebhooksByViolatorId(Long violatorId);

    void createWebhooks(Decision decision);

    void createWebhook(Protocol protocol);
}
