package uz.ciasev.ubdd_service.service.sit_center;

import uz.ciasev.ubdd_service.entity.wanted.WantedProtocol;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

import java.util.List;

public interface SitCenterWebhookCreateService {

    void createWebhooks(AdmCase admCase);

    void createWebhooksByViolatorId(Long violatorId);

    void createWebhooks(Decision decision);

    void createWebhook(Protocol protocol);

    // не ходи в базу, используй эти данные если их хватит
    void createWebhooks(List<WantedProtocol> wantedProtocol);
}
