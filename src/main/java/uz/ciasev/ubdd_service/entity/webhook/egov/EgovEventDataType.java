package uz.ciasev.ubdd_service.entity.webhook.egov;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EgovEventDataType {
    PROTOCOL(11L);
    private final Long id;
}
