package uz.ciasev.ubdd_service.repository.publicapi;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookEvent;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookEventLog;

import java.util.List;

public interface PublicApiWebhookEventLogRepository extends JpaRepository<PublicApiWebhookEventLog, Long> {

    List<PublicApiWebhookEventLog> findAllByEvent(PublicApiWebhookEvent event);

    boolean existsByEventAndIsSentAndIsIgnore(PublicApiWebhookEvent event, boolean sentFlag, boolean ignoreFlag);
}
