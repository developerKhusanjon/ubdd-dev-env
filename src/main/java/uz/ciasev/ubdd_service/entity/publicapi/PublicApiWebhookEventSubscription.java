package uz.ciasev.ubdd_service.entity.publicapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.Organ;

import javax.persistence.*;

@Entity
@Table(name = "public_api_webhooks_event_subscription")
@NoArgsConstructor
public class PublicApiWebhookEventSubscription {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Enumerated(EnumType.STRING)
    private PublicApiWebhookType type;

    @Getter
    private Long organId;

    public PublicApiWebhookEventSubscription(PublicApiWebhookType type, Organ organ) {
        this.type = type;
        this.organId = organ.getId();
    }
}
