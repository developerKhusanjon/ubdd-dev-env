package uz.ciasev.ubdd_service.service.webhook.ibd;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IBDWebhookEventDataType {
    PHOTO_URI(8L),
    PDF_URI(7L),
    PROTOCOL(6L);

    private final Long id;
}
