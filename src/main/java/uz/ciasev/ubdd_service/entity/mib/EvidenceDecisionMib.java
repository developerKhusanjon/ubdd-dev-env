package uz.ciasev.ubdd_service.entity.mib;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.resolution.evidence_decision.EvidenceDecision;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Builder
@Table(name = "mib_execution_card_evidence_decision")
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class EvidenceDecisionMib implements Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @Getter
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime editedTime;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mib_execution_card_id")
    private MibExecutionCard card;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evidence_decision_id")
    private EvidenceDecision evidenceDecision;

    @Getter
    @Setter
    private Boolean isActive;


    // JPA AND CRITERIA ONLY FIELDS

    @Column(name = "mib_execution_card_id", updatable = false, insertable = false)
    private Long cardId;

    @Column(name = "evidence_decision_id", updatable = false, insertable = false)
    private Long evidenceDecisionId;

    public Long getCardId() {
        if (card == null) return null;
        return card.getId();
    }

    public Long getEvidenceDecisionId() {
        if (evidenceDecision == null) return null;
        return evidenceDecision.getId();
    }
}