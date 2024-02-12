package uz.ciasev.ubdd_service.repository.publicapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookEventSubscription;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookType;

import java.util.List;

public interface PublicApiWebhookEventSubscriptionRepository extends JpaRepository<PublicApiWebhookEventSubscription, Long> {

    boolean existsByOrganIdAndType(Long organId, PublicApiWebhookType type);

    List<PublicApiWebhookEventSubscription> findByOrganId(Long organId);

    @Modifying
    @Query("DELETE FROM  PublicApiWebhookEventSubscription s WHERE s.organId = :organId")
    void deleteByOrganId(Long organId);
}
