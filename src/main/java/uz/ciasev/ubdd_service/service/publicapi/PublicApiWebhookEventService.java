package uz.ciasev.ubdd_service.service.publicapi;

import com.fasterxml.jackson.databind.JsonNode;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookEvent;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookEventLock;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookType;
import uz.ciasev.ubdd_service.entity.dict.Organ;

import java.util.List;
import java.util.Optional;

public interface PublicApiWebhookEventService {

    void add(PublicApiWebhookType type, Organ organ, AdmCase admCase, JsonNode data);

//    Optional<PublicApiWebhookEvent> findNextNotSent(PublicApiWebhookEvent event, LocalDateTime now);

    List<PublicApiWebhookEvent> findNextForSet();

//    void clearLock();

    List<PublicApiWebhookEventLock> getLocks();

    boolean lockAlive(PublicApiWebhookEventLock lock);

    Optional<PublicApiWebhookEventLock> lock(PublicApiWebhookEvent event);

    void successfullySendCallback(PublicApiWebhookEventLock lock);

    void failureSendCallback(PublicApiWebhookEventLock lock);
}
