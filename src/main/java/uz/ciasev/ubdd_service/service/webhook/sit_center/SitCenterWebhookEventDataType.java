package uz.ciasev.ubdd_service.service.webhook.sit_center;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SitCenterWebhookEventDataType {
    WANTED_PERSON(1L),
    PROTOCOL(2L),
    GENERATE_PROTOCOL(10L);

    private final Long id;
}
