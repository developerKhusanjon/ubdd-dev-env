package uz.ciasev.ubdd_service.repository.publicapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhook;
import uz.ciasev.ubdd_service.entity.dict.Organ;

import java.util.Optional;

public interface PublicApiWebhookRepository extends JpaRepository<PublicApiWebhook, Long> {

    boolean existsByOrganId(Long organId);

    Optional<PublicApiWebhook> findByOrganId(Long organId);

    @Modifying
    @Query("DELETE FROM  PublicApiWebhook w WHERE w.organ = :organ")
    void deleteByOrgan(Organ organ);
}
