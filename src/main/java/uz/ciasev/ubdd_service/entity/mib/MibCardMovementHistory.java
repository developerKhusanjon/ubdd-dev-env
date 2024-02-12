package uz.ciasev.ubdd_service.entity.mib;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.execution.MibRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "mib_card_movement_history")
@ToString(of = "id")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class MibCardMovementHistory {

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
    @Setter
    @ManyToOne
    @JoinColumn(name = "mib_card_movement_id")
    private MibCardMovement mibCardMovement;

    @Getter
    @Setter
    private String mibCaseNumber;

    @Setter
    @ManyToOne
    @JoinColumn(name = "mib_case_status_id")
    private MibCaseStatus mibCaseStatus;

    //  move  movement
    @Getter
    @Setter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private List<PaymentData> payments;

    public MibCardMovementHistory(MibCardMovement movement, MibRequestDTO result) {
        this.mibCardMovement = movement;
        this.mibCaseNumber = result.getExecutionCaseNumber();
        this.mibCaseStatus = result.getExecutionCaseStatus();
        this.payments = result.buildPaymentsData();
    }
}
