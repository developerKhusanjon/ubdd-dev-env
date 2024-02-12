package uz.ciasev.ubdd_service.entity.court;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.invoice.InvoiceDeactivateReasonAlias;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "billing_sending")
@EntityListeners(AuditingEntityListener.class)
public class BillingSending {


    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(updatable = false)
    private Long invoiceId;

    @Getter
    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private BillingAction action;

    @Getter
    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private InvoiceDeactivateReasonAlias reasonType;

    @Getter
    @Column(updatable = false)
    private String reason;

    @Setter
    @Getter
    private Boolean isSent;

    @Setter
    @Getter
    private String error;

    @Setter
    @Getter
    private Boolean hasError;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Setter
    private User user;

    private BillingSending(Invoice invoice, BillingAction action) {
        this.invoiceId = invoice.getInvoiceId();
        this.action = action;

        this.hasError = false;
        this.isSent = false;
    }

    public static BillingSending open(Invoice billingInvoice) {
        BillingSending e = new BillingSending(billingInvoice, BillingAction.OPEN_INVOICE);
        return e;
    }

    public static BillingSending close(Invoice billingInvoice, InvoiceDeactivateReasonAlias reasonType, String reason) {
        BillingSending e = new BillingSending(billingInvoice, BillingAction.CANCEL_INVOICE);
        e.reason = reason;
        e.reasonType = reasonType;
        return e;
    }
}
