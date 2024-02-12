package uz.ciasev.ubdd_service.entity.invoice;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PaymentDataProjection {

    Long getId();

    Long getAmount();

    String getNumber();

    LocalDateTime getPaymentTime();

    String getInvoiceSerial();

    String getBid();

    String getBlankNumber();

    LocalDate getBlankDate();

    String getFromBankCode();

    String getFromBankAccount();

    String getFromBankName();

    String getFromInn();

    String getToBankCode();

    String getToBankAccount();

    String getToBankName();

    String getToInn();
}
