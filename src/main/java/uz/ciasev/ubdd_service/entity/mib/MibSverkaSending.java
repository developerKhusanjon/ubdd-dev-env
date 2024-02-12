package uz.ciasev.ubdd_service.entity.mib;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "mib_sverka_sending")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(of = "id")
public class MibSverkaSending {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Column(nullable = true, updatable = false)
    private LocalDateTime createdTime;

    @Getter
    private Long envelopedId;

    @Getter
    private Long decisionId;

    @Getter
    private String sourceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private MibExecutionCard card;

    @Getter
    private LocalDate resolutionDate;

    @Getter
    private String controlSerial;

    @Getter
    private String controlNumber;

    @Getter
    @Setter
    private LocalDateTime passTime;

    @Getter
    @Setter
    private Boolean isExclude;

    @Getter
    @Setter
    private Boolean pass;

    @Getter
    @Setter
    private Boolean hasError;

    @Getter
    @Setter
    private Boolean isApiError;

    @Getter
    @Setter
    private String errorText;

    @Getter
    private int passCount;

    public MibSverkaSending(MibExecutionCard card, String controlSerial, String controlNumber, Long envelopedId) {
        this.envelopedId = envelopedId;
        this.decisionId = card.getDecisionId();
        this.card = card;
        this.resolutionDate = card.getOutDate();
        this.controlSerial = controlSerial;
        this.controlNumber = controlNumber;
        this.createdTime = LocalDateTime.now();

        this.sourceType = "user-request";
        this.passTime = null;
        this.pass = false;
        this.hasError = false;
        this.isApiError = false;
        this.errorText = null;
        this.passCount = 0;
    }

    public void incrementCount() {
        this.passCount++;
    }

    public Long getCardId() {
        if (this.card == null) return null;
        return this.card.getId();
    }
}
