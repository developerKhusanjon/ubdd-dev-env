package uz.ciasev.ubdd_service.service.publicapi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookEvent;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookEventLog;
import uz.ciasev.ubdd_service.repository.publicapi.PublicApiWebhookEventLogRepository;
import uz.ciasev.ubdd_service.utils.PageUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PublicApiWebhookEventLogServiceImpl implements PublicApiWebhookEventLogService {

    private final PublicApiWebhookEventLogRepository repository;

    @Override
    @Transactional
    public void save(PublicApiWebhookEventLog eventLog) {
        repository.save(eventLog);
    }

    @Override
    @Transactional
    public void save(PublicApiWebhookEvent event, boolean success, String response) {
        repository.save(
                makeEntity(event, success, response)
        );
    }

    private PublicApiWebhookEventLog makeEntity(PublicApiWebhookEvent event, boolean success, String response) {
        PublicApiWebhookEventLog rsl = new PublicApiWebhookEventLog();
        rsl.setEvent(event);
        rsl.setIsSent(success);
        rsl.setSentResponse(response);
        return rsl;
    }

    @Override
    public List<PublicApiWebhookEventLog> findAllByEvent(PublicApiWebhookEvent event) {
        return repository.findAllByEvent(event);
    }

    @Override
    public Optional<PublicApiWebhookEventLog> findLast() {
        return repository.findAll(PageUtils.oneWithMaxId()).stream().findFirst();
    }

    @Override
    public boolean existsByEventAndSentFlag(PublicApiWebhookEvent event, boolean sentFlag) {
        return repository.existsByEventAndIsSentAndIsIgnore(event, sentFlag, false);
    }
}
