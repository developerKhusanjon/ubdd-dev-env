package uz.ciasev.ubdd_service.entity.webhook.sit_center;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import uz.ciasev.ubdd_service.service.webhook.sit_center.SitCenterWebhookEventDataType;

import javax.persistence.*;

@Entity
@Table(name = "webhook_event_generate", schema = "sit_center")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SitCenterGenerateWebhookEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private JsonNode data;

    @Getter
    private Long dataTypeId;

    public SitCenterGenerateWebhookEvent(JsonNode data, SitCenterWebhookEventDataType type) {
        this.data = data;
        this.dataTypeId = type.getId();
    }
}
