package uz.ciasev.ubdd_service.service.execution;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.invoice.PaymentDataProjection;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class InvoicePayment {

    private final Long id;
    private final Long amount;
    private final String number;
    private final LocalDateTime paymentTime;
    private final String invoiceSerial;
    private final String bid;
    private final String blankNumber;
    private final LocalDate blankDate;
    private final String fromBankCode;
    private final String fromBankAccount;
    private final String fromBankName;
    private final String fromInn;
    private final String toBankCode;
    private final String toBankAccount;
    private final String toBankName;
    private final String toInn;

    public InvoicePayment(PaymentDataProjection paymentDataProjection) {
        this.id = paymentDataProjection.getId();
        this.amount = paymentDataProjection.getAmount();
        this.number = paymentDataProjection.getNumber();
        this.paymentTime = paymentDataProjection.getPaymentTime();
        this.invoiceSerial = paymentDataProjection.getInvoiceSerial();
        this.bid = paymentDataProjection.getBid();
        this.blankNumber = paymentDataProjection.getBlankNumber();
        this.blankDate = paymentDataProjection.getBlankDate();
        this.fromBankCode = paymentDataProjection.getFromBankCode();
        this.fromBankAccount = paymentDataProjection.getFromBankAccount();
        this.fromBankName = paymentDataProjection.getFromBankName();
        this.fromInn = paymentDataProjection.getFromInn();
        this.toBankCode = paymentDataProjection.getToBankCode();
        this.toBankAccount = paymentDataProjection.getToBankAccount();
        this.toBankName = paymentDataProjection.getToBankName();
        this.toInn = paymentDataProjection.getToInn();
    }
}
