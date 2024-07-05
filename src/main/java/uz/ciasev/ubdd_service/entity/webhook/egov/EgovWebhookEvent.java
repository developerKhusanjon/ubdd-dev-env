package uz.ciasev.ubdd_service.entity.webhook.egov;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import uz.ciasev.ubdd_service.event.AdmEventType;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "egov_webhook_event", schema = "sit_center")
public class EgovWebhookEvent {

    public EgovWebhookEvent(JsonNode data, AdmEventType admEventType) {
        this.data = data;
        this.dataTypeId = 11L;
        this.admEventType = admEventType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private JsonNode data;

    private Long dataTypeId;

    @Enumerated(EnumType.STRING)
    private AdmEventType admEventType;
}
