package uz.ciasev.ubdd_service.service.sit_center;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SitCenterWebhookEventDataType {
    WANTED_PERSON(1L),
    PROTOCOL(2L);

    private final Long id;
}
