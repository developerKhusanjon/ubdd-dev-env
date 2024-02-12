package uz.ciasev.ubdd_service.entity.mib;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatus;
import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatusAlias;
import uz.ciasev.ubdd_service.entity.dict.mib.MibSendStatus;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "mib_card_movement")
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "id")
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class MibCardMovement {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mib_card_movement_id_seq")
    @SequenceGenerator(name = "mib_card_movement_id_seq", sequenceName = "mib_card_movement_id_seq", allocationSize = 1)
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
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;

    @Getter
    @ManyToOne
    @JoinColumn(name = "card_id")
    private MibExecutionCard card;

    @Getter
    @Setter
    private Boolean isFirst;

    @Getter
    @Setter
    private boolean isActive;

    @Getter
    @Setter
    private Long mibRequestId;

    @Getter
    @Setter
    private LocalDateTime sendTime;

    @Getter
    @Setter
    private LocalDateTime acceptTime;

    @Getter
    @Setter
    private LocalDateTime returnTime;

    @Getter
    @Setter
    @Column(name = "send_status_id")
    private Long sendStatusId;

    @Getter
    @Setter
    private String sendMessage;

    @Getter
    @Setter
    private String mibCaseNumber;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "mib_case_status_id")
    private MibCaseStatus mibCaseStatus;

    @Getter
    @Setter
    private Long amountOfRecovery;

    @Getter
    @Setter
    private Long totalRecoveredAmount;

    @Setter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private List<PaymentData> payments;

    @Getter
    @Setter
    private Boolean isCourt = false;

    @Getter
    @Setter
    private Boolean isSubscribedCourtMovement = false;

    @Getter
    @Setter
    private LocalDateTime courtMovementSubscriptionTime;

    @Getter
    @Setter
    private LocalDateTime courtMovementSubscriptionLastAttemptTime;

    @Getter
    @Setter
    private String courtMovementSubscriptionLastAttemptMessage;

    private Boolean isSynced;

    private Long syncEnvelopeId;

    private LocalDateTime syncTime;

    @Column(insertable = false, updatable = false)
    private Boolean isNotTakeForAutosending;



    // JPA AND CRITERIA ONLY FIELDS

    @Column(name = "card_id", insertable = false, updatable = false)
    private Long cardId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Column(name = "region_id", insertable = false, updatable = false)
    private Long regionId;

    @Column(name = "district_id", insertable = false, updatable = false)
    private Long districtId;

    @Column(name = "mib_case_status_id", insertable = false, updatable = false)
    private Long mibCaseStatusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_status_id", insertable = false, updatable = false)
    private MibSendStatus sendStatus;

    public void setSendStatus(MibSendStatus sendStatus) {
        if (sendStatus == null) return;
        this.sendStatusId = sendStatus.getId();
    }

    public Long getUserId() {
        if (user == null) return null;
        return user.getId();
    }

    public Long getRegionId() {
        if (region == null) return null;
        return region.getId();
    }

    public Long getDistrictId() {
        if (district == null) return null;
        return district.getId();
    }

    public Long getCardId() {
        if (card == null) return null;
        return card.getId();
    }

    public void setCard(MibExecutionCard card) {
        this.card = card;
        this.region = card.region;
        this.district = card.district;
    }

//    public Long getSendStatusId() {
//        if (sendStatus == null) return null;
//        return sendStatus.getId();
//    }

    public Long getMibCaseStatusId() {
        if (mibCaseStatus == null) return null;
        return mibCaseStatus.getId();
    }

    public String getMibCaseStatusCode() {
        if (mibCaseStatus == null) return null;
        return mibCaseStatus.getCode();
    }

    public List<PaymentData> getPayments() {
        return payments == null ? List.of() : payments;
    }

    public Optional<LocalDate> getLastPayDate() {
        return getPayments().stream()
                .map(PaymentData::getPaymentTime)
                .map(LocalDateTime::toLocalDate)
                .max(Comparator.naturalOrder());
    }

    public boolean isAwaitExecution() {
        if (!MibSendStatus.isSuccessfully(sendStatusId)) {
            return false;
        }

        if (mibCaseStatus == null) {
            return true;
        }

        if (mibCaseStatus.is(MibCaseStatusAlias.ACCEPTED)) {
            return true;
        }

        return false;
    }

    public boolean isSent() {
        return MibSendStatus.isSuccessfully(sendStatusId);
    }
}
