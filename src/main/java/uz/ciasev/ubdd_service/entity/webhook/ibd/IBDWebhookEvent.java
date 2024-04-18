package uz.ciasev.ubdd_service.entity.webhook.ibd;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import uz.ciasev.ubdd_service.service.webhook.ibd.IBDWebhookEventDataType;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ibd_webhook_event", schema = "sit_center")
public class IBDWebhookEvent {

    public IBDWebhookEvent(JsonNode data, IBDWebhookEventDataType dataType) {
        this.dataTypeId = dataType.getId();
        this.data = data;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private JsonNode data;
    private Long dataTypeId;
}
