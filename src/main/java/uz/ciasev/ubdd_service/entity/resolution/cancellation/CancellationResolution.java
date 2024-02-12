package uz.ciasev.ubdd_service.entity.resolution.cancellation;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.dict.resolution.OrganCancellation;
import uz.ciasev.ubdd_service.entity.dict.resolution.ReasonCancellation;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Builder
@Table(name = "cancellation_resolution")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CancellationResolution implements Serializable, AdmEntity {

    @Getter
    @Transient
    private final EntityNameAlias entityNameAlias = EntityNameAlias.CANCELLATION_RESOLUTION;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cancellation_resolution_id_seq")
    @SequenceGenerator(name = "cancellation_resolution_id_seq", sequenceName = "cancellation_resolution_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resolution_id")
    private Resolution resolution;

    @Getter
    @Setter
    private Long claimId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reason_cancellation_id")
    private ReasonCancellation reasonCancellation;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organ_cancellation_id")
    private OrganCancellation organCancellation;

    @Getter
    @Setter
    private LocalDateTime cancellationDate;

    @Getter
    @Setter
    private String signature;

    @Getter
    @Setter
    private String fileUri;

    // JPA AND CRITERIA FIELDS ONLY

    public Long getReasonCancellationId() {
        if (reasonCancellation == null) return null;
        return reasonCancellation.getId();
    }

    public Long getOrganCancellationId() {
        if (organCancellation == null) return null;
        return organCancellation.getId();
    }

    public Long getResolutionId() {
        if (resolution == null) return null;
        return resolution.getId();
    }

    public Long getUserId() {
        if (user == null) return null;
        return user.getId();
    }

    @Column(name = "reason_cancellation_id", insertable = false, updatable = false)
    private Long reasonCancellationId;

    @Column(name = "organ_cancellation_id", insertable = false, updatable = false)
    private Long organCancellationId;

    @Column(name = "resolution_id", insertable = false, updatable = false)
    private Long resolutionId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CancellationResolution that = (CancellationResolution) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
