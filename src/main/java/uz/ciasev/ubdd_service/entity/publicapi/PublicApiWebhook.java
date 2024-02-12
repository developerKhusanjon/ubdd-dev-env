package uz.ciasev.ubdd_service.entity.publicapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ciasev.ubdd_service.entity.dict.Organ;

import javax.persistence.*;

@Entity
@Table(name = "public_api_webhooks")
@NoArgsConstructor
public class PublicApiWebhook {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organ_id")
    private Organ organ;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private String token;

    @Column(name = "organ_id", updatable = false, insertable = false)
    private Long organId;

    public Long getOrganId() {
        if (organ == null) return null;
        return organ.getId();
    }
}
