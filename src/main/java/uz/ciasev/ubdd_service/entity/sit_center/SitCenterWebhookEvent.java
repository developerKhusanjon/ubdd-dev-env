package uz.ciasev.ubdd_service.entity.sit_center;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import org.hibernate.annotations.Type;
import uz.ciasev.ubdd_service.service.sit_center.SitCenterWebhookEventDataType;

import javax.persistence.*;

@Entity
@Table(name = "webhook_event", schema = "sit_center")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SitCenterWebhookEvent {

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

    public SitCenterWebhookEvent(JsonNode data, SitCenterWebhookEventDataType type) {
        this.data = data;
        this.dataTypeId = type.getId();
    }
}
