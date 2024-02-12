package uz.ciasev.ubdd_service.entity.court;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.utils.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "court_execution_payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class CourtExecutionPayment {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "court_execution_payment_id_seq")
    @SequenceGenerator(name = "court_execution_payment_id_seq", sequenceName = "court_execution_payment_id_seq", allocationSize = 1)
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

    @Getter
    private Boolean isActive;

    @Getter
    @Column(updatable = false)
    private Long caseId;

    @Getter
    @Column(updatable = false)
    private Long claimId;

    @Getter
    @Column(updatable = false)
    private Long defendantId;

    @Getter
    @Column(updatable = false)
    private Long violatorId;

    @Getter
    @Column(updatable = false)
    private Long personId;


    @Getter
    @Column(updatable = false)
    private Long supplierType;

    @Getter
    @Column(updatable = false)
    private String invoiceSerial;

    @Getter
    @Column(updatable = false)
    private String invoiceUrl;

    @Getter
    @Column(updatable = false)
    private Long paymentId;


    @Setter
    @Getter
    private Boolean isSent;

    @Setter
    @Getter
    private Boolean isAccept;

    @Getter
    private String rejectionReason;

    @Setter
    @Getter
    private Boolean hasError;

    @Setter
    @Getter
    private String error;

    @Builder
    public CourtExecutionPayment(Long caseId, Long claimId, Long defendantId, Long violatorId, Long personId, Long supplierType, String invoiceSerial, String invoiceUrl, Long paymentId) {
        this.caseId = caseId;
        this.claimId = claimId;
        this.defendantId = defendantId;
        this.violatorId = violatorId;
        this.personId = personId;
        this.supplierType = supplierType;
        this.invoiceSerial = invoiceSerial;
        this.invoiceUrl = invoiceUrl;
        this.paymentId = paymentId;

        this.isActive = true;
        this.isSent = false;
        this.isAccept = false;
        this.hasError = false;

    }

    public void setRejectionReason(String value) {
        this.rejectionReason = StringUtils.first(value, 500);
    }
}
