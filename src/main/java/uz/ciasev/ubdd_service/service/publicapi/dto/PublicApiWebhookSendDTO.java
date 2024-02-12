package uz.ciasev.ubdd_service.service.publicapi.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookEvent;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookType;

import java.time.LocalDateTime;

@Data
public class PublicApiWebhookSendDTO {

    private final PublicApiWebhookType type;

    private final Long uid;

    private final LocalDateTime createdTime;

    private final Long admCaseId;

    private final JsonNode data;

    public PublicApiWebhookSendDTO(PublicApiWebhookEvent event) {
        this.type = event.getType();
        this.uid = event.getId();
        this.createdTime = event.getCreatedTime();
        this.admCaseId = event.getAdmCaseId();
        this.data = event.getData();
    }
}
