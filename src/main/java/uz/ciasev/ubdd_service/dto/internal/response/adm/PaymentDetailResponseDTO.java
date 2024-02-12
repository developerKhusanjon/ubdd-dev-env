package uz.ciasev.ubdd_service.dto.internal.response.adm;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.invoice.Payment;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PaymentDetailResponseDTO {

    private final Long id;
    private final LocalDateTime createdTime;
    private final Long invoiceId;
    private final String number;
    private final String blankNumber;
    private final LocalDate blankDate;
    private final LocalDateTime paymentTime;
    private final Long amount;
    private final String fromBankCode;
    private final String fromBankAccount;
    private final String fromBankName;
    private final String fromInn;
    private final String toBankCode;
    private final String toBankAccount;
    private final String toBankName;
    private final String toInn;

    public PaymentDetailResponseDTO(Payment payment) {
        this.id = payment.getId();
        this.createdTime = payment.getCreatedTime();
        this.invoiceId = payment.getInvoiceId();
        this.number = payment.getNumber();
        this.blankNumber = payment.getBlankNumber();
        this.blankDate = payment.getBlankDate();
        this.paymentTime = payment.getPaymentTime();
        this.amount = payment.getAmount();
        this.fromBankCode = payment.getFromBankCode();
        this.fromBankAccount = payment.getFromBankAccount();
        this.fromBankName = payment.getFromBankName();
        this.fromInn = payment.getFromInn();
        this.toBankCode = payment.getToBankCode();
        this.toBankAccount = payment.getToBankAccount();
        this.toBankName = payment.getToBankName();
        this.toInn = payment.getToInn();
    }
}
