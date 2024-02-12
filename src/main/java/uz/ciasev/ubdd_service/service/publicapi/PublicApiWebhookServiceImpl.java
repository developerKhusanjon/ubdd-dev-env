package uz.ciasev.ubdd_service.service.publicapi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhook;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookEventSubscription;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookType;
import uz.ciasev.ubdd_service.dto.internal.request.publicapi.PublicApiWebhookRequestDTO;
import uz.ciasev.ubdd_service.repository.publicapi.PublicApiWebhookEventSubscriptionRepository;
import uz.ciasev.ubdd_service.repository.publicapi.PublicApiWebhookRepository;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@Profile("publicapi")
@RequiredArgsConstructor
public class PublicApiWebhookServiceImpl implements PublicApiWebhookService {

    private final PublicApiWebhookRepository repository;
    private final PublicApiWebhookEventSubscriptionRepository subscriptionRepository;
    private final RestTemplate restTemplate;


    private PublicApiWebhook makeEntity(User user, PublicApiWebhookRequestDTO requestDTO) {

        PublicApiWebhook rsl = new PublicApiWebhook();
        rsl.setOrgan(user.getOrgan());
        rsl.setUrl(requestDTO.getUrl());
        rsl.setToken(requestDTO.getToken());
        return rsl;
    }

    @Override
    @Transactional
    public void registerWebhook(User user, PublicApiWebhookRequestDTO requestDTO) {
        checkUrlAvailable(requestDTO);

        repository.deleteByOrgan(user.getOrgan());
        repository.save(makeEntity(user, requestDTO));
        subscribeToTypes(user.getOrgan(), requestDTO.getSubscribeToTypes());
    }

    @Override
    @Transactional
    public void deregisterWebhook(User user) {
        subscriptionRepository.deleteByOrganId(user.getOrgan().getId());
        repository.deleteByOrgan(user.getOrgan());
    }

    @Override
    public Optional<PublicApiWebhook> findByOrgan(Long organId) {
        return repository.findByOrganId(organId);
    }

    @Override
    public boolean isPublicApiWebhookOrgan(Organ organ) {
        return repository.existsByOrganId(organ.getId());
    }

    @Override
    public boolean isOrganSubscribeToEvent(Organ organ, PublicApiWebhookType type) {
        return isPublicApiWebhookOrgan(organ) && subscriptionRepository.existsByOrganIdAndType(organ.getId(), type);
    }

    private void subscribeToTypes(Organ organ, List<PublicApiWebhookType> types) {
        if (types.isEmpty()) {
            types = List.of(PublicApiWebhookType.values());
        }

        List<PublicApiWebhookEventSubscription> subscriptions = types.stream()
                .map(t -> new PublicApiWebhookEventSubscription(t, organ))
                .collect(Collectors.toList());

        subscriptionRepository.deleteByOrganId(organ.getId());
        subscriptionRepository.saveAll(subscriptions);
    }

    private void checkUrlAvailable(PublicApiWebhookRequestDTO requestDTO) {
        try {
            restTemplate.postForEntity(requestDTO.getUrl(), null, String.class);
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            return;
        } catch (Exception e) {
            throw new ValidationException(
                    ErrorCode.PUBLIC_API_WEBHOOK_URL_UNREACHABLE,
                    String.format("Post request with empty body to '%s' raised error: %s", requestDTO.getUrl(), e.getMessage())
            );
        }
    }
}
