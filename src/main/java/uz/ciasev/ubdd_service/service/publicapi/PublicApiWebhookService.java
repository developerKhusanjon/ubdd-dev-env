package uz.ciasev.ubdd_service.service.publicapi;

import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhook;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookType;
import uz.ciasev.ubdd_service.dto.internal.request.publicapi.PublicApiWebhookRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.Optional;

public interface PublicApiWebhookService {

    void registerWebhook(User user, PublicApiWebhookRequestDTO requestDTO);

    void deregisterWebhook(User user);

    Optional<PublicApiWebhook> findByOrgan(Long organId);

    boolean isPublicApiWebhookOrgan(Organ organ);

    boolean isOrganSubscribeToEvent(Organ organ, PublicApiWebhookType type);
}
