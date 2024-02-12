package uz.ciasev.ubdd_service.entity.publicapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Immutable
@Entity
@Table(name = "public_api_webhooks_events_lock")
@NoArgsConstructor
public class PublicApiWebhookEventLock {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private LocalDateTime createdTime;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "event_id")
    private PublicApiWebhookEvent event;

    public PublicApiWebhookEventLock(PublicApiWebhookEvent event) {
        this.event = event;
        this.createdTime = LocalDateTime.now();
    }

    public Object getEventId() {
        if (event == null) return null;
        return event.getId();
    }
}
