package uz.ciasev.ubdd_service.entity.webhook.replica;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "replica_webhook_event", schema = "sit_center")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplicaWebhookEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private JsonNode data;

    private Long dataTypeId;

    public ReplicaWebhookEvent(JsonNode data) {
        this.data = data;
        this.dataTypeId = 4L;
    }
}
