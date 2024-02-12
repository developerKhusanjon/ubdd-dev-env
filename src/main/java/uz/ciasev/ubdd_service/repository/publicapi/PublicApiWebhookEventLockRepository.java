package uz.ciasev.ubdd_service.repository.publicapi;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookEventLock;

public interface PublicApiWebhookEventLockRepository extends JpaRepository<PublicApiWebhookEventLock, Long> {
}
