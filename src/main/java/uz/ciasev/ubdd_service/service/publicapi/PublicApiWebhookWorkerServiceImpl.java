package uz.ciasev.ubdd_service.service.publicapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhook;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookEvent;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookEventLock;
import uz.ciasev.ubdd_service.service.publicapi.dto.PublicApiWebhookSendDTO;

import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
//@Profile("publicapiworker")
public class PublicApiWebhookWorkerServiceImpl implements PublicApiWebhookWorkerService {

    private final PublicApiWebhookService webhookService;
    private final PublicApiWebhookEventService webhookEventService;
    private final PublicApiWebhookEventLogService webhookEventLogService;
    private final RestTemplate restTemplate;
    private final int executorPoolSize;
    private ExecutorService executor;


    @Autowired
    public PublicApiWebhookWorkerServiceImpl(PublicApiWebhookService webhookService,
                                             PublicApiWebhookEventService webhookEventService,
                                             PublicApiWebhookEventLogService webhookEventLogService,
                                             @Qualifier("publicApiWebhookWorkerRestTemplate") RestTemplate restTemplate,
                                             @Value("${mvd-ciasev.public-api.webhook.worker-pool-size}") int poolSize) {
        this.webhookService = webhookService;
        this.webhookEventService = webhookEventService;
        this.webhookEventLogService = webhookEventLogService;
        this.restTemplate = restTemplate;
        this.executorPoolSize = poolSize;
    }


    @Override
    public void startAsync() {
        ExecutorService mainPool = Executors.newFixedThreadPool(1);
        mainPool.submit(this::startSync);
    }

    @Override
    public void startSync() {
        log.info("PUBLIC-API WEBHOOKS: Start send event ");

        completeOpenedLocks();

        initExecutor();

        log.info("PUBLIC-API WEBHOOKS: Start worker");
        int eventCount = Integer.MAX_VALUE;
        try {
            while (!executor.awaitTermination(eventCount == 0 ? 5000L : 100L, TimeUnit.MICROSECONDS)) {
//            pause(eventCount == 0 ? 5000 : 100);
                eventCount = makeRound();
            }
        } catch (InterruptedException e) {
            log.warn("PUBLIC-API WEBHOOKS: Executor interrupted");
        }
        log.info("PUBLIC-API WEBHOOKS: Worker stop");


        destroyExecutor();
        log.info("PUBLIC-API WEBHOOKS: End send event ");
    }

    @Override
    public void checkExecutorWorkFine() {
        webhookEventLogService.findLast().ifPresent(lastLog -> {
            boolean hasNewSendLog = lastLog.getSentTime().isAfter(LocalDateTime.now().minusMinutes(10));

            if (!hasNewSendLog) {
                executor.shutdownNow();
            }
        });
    }

    private void completeOpenedLocks() {
        log.info("PUBLIC-API WEBHOOKS: START LOCKS REALISE");
        List<PublicApiWebhookEventLock> locks = webhookEventService.getLocks();
        for (PublicApiWebhookEventLock lock : locks) {
            boolean isReceive = webhookEventLogService.existsByEventAndSentFlag(lock.getEvent(), true);
            if (isReceive) {
                webhookEventService.successfullySendCallback(lock);
            } else {
                webhookEventService.failureSendCallback(lock);
            }
        }
        log.info("PUBLIC-API WEBHOOKS: LOCKS COMPLETED");
    }


    private int makeRound() {
        List<PublicApiWebhookEvent> events = webhookEventService.findNextForSet();
        if (events.isEmpty()) {
            return 0;
        }

        for (PublicApiWebhookEvent event : events) {
            Optional<PublicApiWebhookEventLock> lockOpt = webhookEventService.lock(event);
            if (lockOpt.isPresent()) {
                executor.submit(new Worker(webhookService, webhookEventLogService, restTemplate, lockOpt.get()));
            } else {
                log.warn("PUBLIC-API WEBHOOKS: Event {} already locked", event.getId());
            }
        }

        return events.size();
    }


    private void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            executor.shutdown();
        }
    }

    private void initExecutor() {
        log.info("PUBLIC-API WEBHOOKS: Initialize executor");

        if (this.executor != null) {
            log.info("PUBLIC-API WEBHOOKS: Destroy old executor firstly");
            destroyExecutor();
        }

        executor = Executors.newFixedThreadPool(executorPoolSize);
    }

    @PreDestroy
    private void destroyExecutor() {
        log.info("PUBLIC-API WEBHOOKS: Shutdown executor");

        if (executor != null) {
            executor.shutdownNow();
            executor = null;
        }
    }

    private class Worker implements Runnable {

        private final PublicApiWebhookService webhookService;
        private final PublicApiWebhookEventLogService webhookEventLogService;
        private final RestTemplate restTemplate;
        private final PublicApiWebhookEventLock lock;

        public Worker(PublicApiWebhookService webhookService,
                      PublicApiWebhookEventLogService webhookEventLogService,
                      RestTemplate restTemplate,
                      PublicApiWebhookEventLock eventLock) {
            this.webhookService = webhookService;
            this.webhookEventLogService = webhookEventLogService;
            this.restTemplate = restTemplate;
            this.lock = eventLock;
        }

        @Override
        public void run() {
            log.debug("PUBLIC-API WEBHOOKS: Attempting to send event start, id:{} lock:{}", lock.getEventId(), lock.getId());

            if (!webhookEventService.lockAlive(lock)) {
                log.debug("PUBLIC-API WEBHOOKS: Event lock is death, id:{} lock:{}", lock.getEventId(), lock.getId());
                return;
            }


            boolean isReceive = sendEvent(lock.getEvent());

            if (isReceive) {
                webhookEventService.successfullySendCallback(lock);
            } else {
                webhookEventService.failureSendCallback(lock);
            }
        }

        private boolean sendEvent(PublicApiWebhookEvent event) {

            PublicApiWebhook webhook = webhookService.findByOrgan(event.getOrganId()).orElse(null);
            if (webhook == null) {
                log.debug("PUBLIC-API WEBHOOKS: Webhook not defined for organ {} ", event.getOrganId());
                return false;
            }

            if (webhook.getToken() == null) {
                log.debug("PUBLIC-API WEBHOOKS: Auth token not defined for webhook, id:{} ", webhook.getId());
                return false;
            }

            log.debug("PUBLIC-API WEBHOOKS: Sending to url:{} ", webhook.getUrl());

            PublicApiWebhookSendDTO dto = new PublicApiWebhookSendDTO(event);

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(webhook.getToken());
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<PublicApiWebhookSendDTO> request = new HttpEntity<>(dto, headers);

            // CHECK IF ANOTHER THREAD ALREADY SEND THIS EVENT
//            boolean isLogExists = webhookEventLogService.existsByEventAndSentFlag(event, true);
//            if (isLogExists) {
//                return;
//            }

            ResponseEntity<String> response;

            try {
                response = restTemplate.postForEntity(webhook.getUrl(), request, String.class);
            } catch (HttpServerErrorException | HttpClientErrorException e) {
                log.debug("PUBLIC-API WEBHOOKS: Request error, id:{}", event.getId(), e);
                webhookEventLogService.save(event, false, e.getStatusText() + " " + e.getResponseBodyAsString());
                return false;
            } catch (Exception e) {
                log.debug("PUBLIC-API WEBHOOKS: Request exception, id:{}", event.getId(), e);
                webhookEventLogService.save(event, false, e.getLocalizedMessage());
                return false;
            }

            if (response.getStatusCode() == HttpStatus.OK) {
                log.debug("PUBLIC-API WEBHOOKS: Request success, id:{} ", event.getId());
                webhookEventLogService.save(event, true,  buildToString(response));
                return true;
            } else {
                log.debug("PUBLIC-API WEBHOOKS: Sending failed, id:{} ", event.getId());
                webhookEventLogService.save(event, false, buildToString(response));
                return false;
            }
        }

        private String buildToString(ResponseEntity<String> response) {
            StringBuilder builder = new StringBuilder("<");
            builder.append(response.getStatusCodeValue());
            builder.append(',');
            String body = response.getBody();
            if (body != null) {
                builder.append(body);
                builder.append(',');
            } else {
                builder.append("EMPTY BODY");
            }
            builder.append('>');
            return builder.toString();
        }
    }
}
