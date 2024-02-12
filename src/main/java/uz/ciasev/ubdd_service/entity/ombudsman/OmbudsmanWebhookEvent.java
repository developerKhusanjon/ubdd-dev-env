package uz.ciasev.ubdd_service.entity.ombudsman;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import uz.ciasev.ubdd_service.event.AdmEventType;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "ombudsman_webhook_event", schema = "sit_center")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OmbudsmanWebhookEvent {

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

    public OmbudsmanWebhookEvent(JsonNode data, AdmEventType admEventType) {
        this.data = data;
        this.dataTypeId = 5L;
        this.admEventType = admEventType;
    }
}
