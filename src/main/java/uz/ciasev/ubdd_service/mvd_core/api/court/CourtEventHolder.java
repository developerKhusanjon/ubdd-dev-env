package uz.ciasev.ubdd_service.mvd_core.api.court;

import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.exception.court.CourtEventNotInit;
import uz.ciasev.ubdd_service.service.publicapi.dto.eventdata.PublicApiWebhookEventDataCourtDTO;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CourtEventHolder {

    private Map<Long, PublicApiWebhookEventDataCourtDTO> webhookDataMap = new ConcurrentHashMap<>();

    public void init() {
        Long id = Thread.currentThread().getId();

        webhookDataMap.put(id, new PublicApiWebhookEventDataCourtDTO());
    }

    public PublicApiWebhookEventDataCourtDTO getCurrentInstance() {
        Long id = Thread.currentThread().getId();

        PublicApiWebhookEventDataCourtDTO webhookData = webhookDataMap.get(id);

        if (webhookData == null) {
            throw new CourtEventNotInit();
        }

        return webhookData;
    }

    public PublicApiWebhookEventDataCourtDTO close() {
        Long id = Thread.currentThread().getId();

        PublicApiWebhookEventDataCourtDTO webhookData = webhookDataMap.remove(id);

        if (webhookData == null) {
            throw new CourtEventNotInit();
        }

        return webhookData;
    }
}
