package uz.ciasev.ubdd_service.entity.violation_event;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Immutable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.dict.violation_event.ViolationEventResultType;
import uz.ciasev.ubdd_service.entity.dict.violation_event.ViolationEventResultTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.utils.converter.ViolationEventResultTypeIdToAliasConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Immutable
@Entity
@Table(name = "violation_event_result")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
@EntityListeners(AuditingEntityListener.class)
public class ViolationEventResult implements Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "violation_event_result_id_seq")
    @SequenceGenerator(name = "violation_event_result_id_seq", sequenceName = "violation_event_result_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @Getter
    private boolean isActive;

    @Getter
    @Column(nullable = false, updatable = false)
    Long violationEventId;

    @Getter
    @Convert(converter = ViolationEventResultTypeIdToAliasConverter.class)
    @Column(name = "type_id")
    private ViolationEventResultTypeAlias typeAlias;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decision_id", updatable = false)
    private Decision decision;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annulment_id", updatable = false)
    private ViolationEventAnnulment annulment;


    // JPA AND CRITERIA FIELD ONLY

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", insertable = false, updatable = false)
    private ViolationEventResultType type;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Column(name = "decision_id", insertable = false, updatable = false)
    private Long decisionId;

    @Column(name = "annulment_id", insertable = false, updatable = false)
    private Long annulmentId;


    private ViolationEventResult(User user, Long violationEventId, ViolationEventResultTypeAlias typeAlias, Decision decision, ViolationEventAnnulment annulment) {
        this.user = user;
        this.violationEventId = violationEventId;
        this.typeAlias = typeAlias;
        this.decision = decision;
        this.annulment = annulment;
        this.isActive = true;
    }


    public ViolationEventResult(User user, Long violationEventId, Decision decision) {
        this(user, violationEventId, ViolationEventResultTypeAlias.DECISION, decision, null);
    }


    public ViolationEventResult(User user, Long violationEventId, ViolationEventAnnulment annulment) {
        this(user, violationEventId, ViolationEventResultTypeAlias.ANNULMENT, null, annulment);
    }
}
