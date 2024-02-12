package uz.ciasev.ubdd_service.dto.internal.response.history;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.history.ViolatorRebindRegistration;

import java.time.LocalDateTime;

@Data
public class ViolatorRebindRegistrationResponseDTO {
    private final Long id;
    private final LocalDateTime createdTime;
    private final Long userId;
    private final Long protocolId;
    private final String fromPinpp;
    private final String toPinpp;
    private final String fromDocument;
    private final String toDocument;

    public ViolatorRebindRegistrationResponseDTO(ViolatorRebindRegistration registration) {
        this.id = registration.getId();
        this.createdTime = registration.getCreatedTime();
        this.userId = registration.getUserId();
        this.protocolId = registration.getProtocolId();
        this.fromPinpp = registration.getFromPinpp();
        this.toPinpp = registration.getToPinpp();
        this.fromDocument = registration.getFromDocument();
        this.toDocument = registration.getToDocument();
    }
}
