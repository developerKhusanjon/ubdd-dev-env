package uz.ciasev.ubdd_service.entity.prosecutor.protest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.dict.prosecutor.ProsecutorProtestReason;
import uz.ciasev.ubdd_service.entity.prosecutor.AbstractProsecutor;
import uz.ciasev.ubdd_service.entity.resolution.cancellation.CancellationResolution;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prosecutor_protest")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProsecutorProtest extends AbstractProsecutor {

    @Transient
    @Getter
    private EntityNameAlias entityNameAlias = EntityNameAlias.PROSECUTOR_PROTEST;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prosecutor_protest_id_seq")
    @SequenceGenerator(name = "prosecutor_protest_id_seq", sequenceName = "prosecutor_protest_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resolution_id")
    @Setter
    private Resolution resolution;

    @Getter
    @Setter
    private String number;

    @Getter
    @Setter
    private LocalDate protestDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reason_id")
    @Setter
    private ProsecutorProtestReason reason;

    @Setter
    @Getter
    private boolean isAccepted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancellation_resolution_id")
    @Setter
    private CancellationResolution cancellationResolution;

    // JPA AND CRITERIA FIELDS ONLY

    @Column(name = "resolution_id", insertable = false, updatable = false)
    private Long resolutionId;

    @Column(name = "reason_id", insertable = false, updatable = false)
    private Long reasonId;

    @Column(name = "cancellation_resolution_id", insertable = false, updatable = false)
    private Long cancellationResolutionId;

    public Long getResolutionId() {
        if (this.resolution == null) {
            return null;
        }
        return this.resolution.getId();
    }

    public Long getReasonId() {
        if (this.reason == null) {
            return null;
        }
        return this.reason.getId();
    }

    public Long getCancellationResolutionId() {
        if (this.cancellationResolution == null) {
            return null;
        }
        return this.cancellationResolution.getId();
    }
}
