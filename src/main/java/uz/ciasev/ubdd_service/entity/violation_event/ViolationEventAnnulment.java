package uz.ciasev.ubdd_service.entity.violation_event;

import lombok.*;
import org.hibernate.annotations.Immutable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.dict.violation_event.ViolationEventAnnulmentReason;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Optional;


@Immutable
@Entity
@Table(name = "violation_event_annulment")
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ViolationEventAnnulment implements Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "violation_event_annulment_id_seq")
    @SequenceGenerator(name = "violation_event_annulment_id_seq", sequenceName = "violation_event_annulment_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reason_id", updatable = false)
    private ViolationEventAnnulmentReason reason;

    @Column(updatable = false)
    private String comment;

    @Column(updatable = false)
    private String documentBaseUri;

    public ViolationEventAnnulment(ViolationEventAnnulmentReason reason, String comment, String documentBaseUri) {
        this.reason = reason;
        this.comment = Optional.ofNullable(comment).orElse("");
        this.documentBaseUri = documentBaseUri;
    }
}
