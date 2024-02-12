package uz.ciasev.ubdd_service.service.publicapi;

import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookEvent;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookEventLog;

import java.util.List;
import java.util.Optional;

public interface PublicApiWebhookEventLogService {

    void save(PublicApiWebhookEventLog eventLog);

    void save(PublicApiWebhookEvent event, boolean success, String response);

    List<PublicApiWebhookEventLog> findAllByEvent(PublicApiWebhookEvent event);

    Optional<PublicApiWebhookEventLog> findLast();

    boolean existsByEventAndSentFlag(PublicApiWebhookEvent event, boolean sentFlag);
}
