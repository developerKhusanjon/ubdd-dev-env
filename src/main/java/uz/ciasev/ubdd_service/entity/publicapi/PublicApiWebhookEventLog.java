package uz.ciasev.ubdd_service.entity.publicapi;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Immutable
@Entity
@Table(name = "public_api_webhooks_events_log")
@Data
@NoArgsConstructor
public class PublicApiWebhookEventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private PublicApiWebhookEvent event;

    private LocalDateTime sentTime = LocalDateTime.now();

    private Boolean isSent = false;

    private Boolean isIgnore = false;

    private String sentResponse;
}
