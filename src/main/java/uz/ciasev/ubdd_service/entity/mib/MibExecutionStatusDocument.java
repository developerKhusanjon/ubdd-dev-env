package uz.ciasev.ubdd_service.entity.mib;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mib_execution_status_document")
@EntityListeners(AuditingEntityListener.class)
public class MibExecutionStatusDocument {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Getter
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_movement_id")
    private MibCardMovement cardMovement;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "execution_status_id")
    private MibCaseStatus executionStatus;

    @Getter
    @Setter
    private String uri;

    @Column(name = "card_movement_id", updatable = false, insertable = false)
    private Long cardMovementId;

    @Column(name = "execution_status_id", updatable = false, insertable = false)
    private Long executionStatusId;

    public Long getCardMovementId() {
        if (cardMovement == null) return null;
        return cardMovement.getId();
    }

    public Long getExecutionStatusId() {
        if (executionStatus == null) return null;
        return executionStatus.getId();
    }

}
