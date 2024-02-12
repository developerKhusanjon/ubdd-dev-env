package uz.ciasev.ubdd_service.service.publicapi;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookEvent;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookEventLock;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookType;
import uz.ciasev.ubdd_service.repository.publicapi.PublicApiWebhookEventLockRepository;
import uz.ciasev.ubdd_service.repository.publicapi.PublicApiWebhookEventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicApiWebhookEventServiceImpl implements PublicApiWebhookEventService {

    private final PublicApiWebhookEventRepository repository;
    private final PublicApiWebhookEventLockRepository lockRepository;

    @Override
    @Transactional
    public void add(PublicApiWebhookType type, Organ organ, AdmCase admCase, JsonNode data) {
        repository.save(
                makeEntity(type, organ, admCase, data)
        );
    }

    private PublicApiWebhookEvent makeEntity(PublicApiWebhookType type, Organ organ, AdmCase admCase, JsonNode data) {
        PublicApiWebhookEvent rsl = new PublicApiWebhookEvent();
        rsl.setType(type);
        rsl.setOrgan(organ);
        rsl.setAdmCase(admCase);
        rsl.setData(data);
        rsl.setOrderId(0);
        rsl.setIsReceived(false);
        return rsl;
    }

//    @Override
//    public Optional<PublicApiWebhookEvent> findNextNotSent(PublicApiWebhookEvent event, LocalDateTime now) {
//
//        Optional<PublicApiWebhookEvent> rsl = Optional.empty();
//
//        if (event != null) {
//            try {
//                rsl = repository.findNextNotSent(event.getId(), now).stream().findFirst();
//            } catch (Exception e) {
//                log.error("PUBLIC-API WEBHOOKS: EVENT SEARCH ERROR", e);
//            }
//        }
//        if (rsl.isEmpty()) {
//            try {
//                rsl = repository.findAnyNotSent(now).stream().findFirst();
//            } catch (Exception e) {
//                log.error("PUBLIC-API WEBHOOKS: EVENT SEARCH ERROR", e);
//            }
//        }
//
//        return rsl;
//    }

    @Override
    public List<PublicApiWebhookEvent> findNextForSet() {
//        return repository.findNextForSending(5, 20);
        return repository.findNextForSending(1000);
    }

//    @Override
//    @Transactional
//    public void clearLock() {
//        lockRepository.deleteAll();
//    }

    @Override
    public List<PublicApiWebhookEventLock> getLocks() {
        return lockRepository.findAll();
    }

        @Override
    public boolean lockAlive(PublicApiWebhookEventLock lock) {
        return lockRepository.existsById(lock.getId());
    }

    @Override
    @Transactional
    public Optional<PublicApiWebhookEventLock> lock(PublicApiWebhookEvent event) {
        try {
            return Optional.of(lockRepository.save(new PublicApiWebhookEventLock(event)));
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException constraintViolationException = (ConstraintViolationException) e.getCause();
                if (constraintViolationException.getConstraintName().equals("public_api_webhooks_events_lock_uniq_event_id")) {
                    Optional.empty();
                }
            }
            throw e;
        }
    }

    @Override
    @Transactional
    public void successfullySendCallback(PublicApiWebhookEventLock lock) {
        repository.setReceivedFields(lock.getEvent());
        lockRepository.delete(lock);
    }

    @Override
    @Transactional
    public void failureSendCallback(PublicApiWebhookEventLock lock) {
        lockRepository.delete(lock);
        repository.prorogueOn(lock.getEvent(), LocalDateTime.now().plusHours(1), 1);
    }
}
