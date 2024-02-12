package uz.ciasev.ubdd_service.entity.publicapi;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.Organ;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "public_api_webhooks_events")
@NoArgsConstructor
public class PublicApiWebhookEvent {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private LocalDateTime createdTime = LocalDateTime.now();

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private PublicApiWebhookType type;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organ_id")
    private Organ organ;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adm_case_id")
    private AdmCase admCase;

    @Getter
    @Setter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private JsonNode data;

    @Getter
    @Setter
    private LocalDateTime dontSendBefore;

    @Getter
    @Setter
    private Integer orderId;

    @Getter
    @Setter
    private Boolean isReceived;

    @Getter
    @Setter
    private LocalDateTime receivedTime;

    // JPA AND CRITERIA ONLY FIELD

    @Column(name = "adm_case_id", insertable = false, updatable = false)
    private Long admCaseId;

    public Long getOrganId() {
        return Optional.ofNullable(organ).map(Organ::getId).orElse(null);
    }

    public Long getAdmCaseId() {
        return Optional.ofNullable(admCase).map(AdmCase::getId).orElse(null);
    }
}
