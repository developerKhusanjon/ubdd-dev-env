package uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.execution;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.mib.PaymentData;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.stream.Stream;

@Data
public class MibPaymentDTO {

    @JsonProperty("supplier_date")
    private String paymentTimeString;

    @JsonProperty("ammount")
    private Long amount;

    @JsonProperty("blank_number")
    private String blankNumber;

    @JsonProperty("blank_date")
    private String blankDateString;

    @JsonProperty("payer_bank_mfo")
    private String fromBankCode;

    @JsonProperty("payer_bank_account")
    private String fromBankAccount;

    @JsonProperty("payer_bank")
    private String fromBankName;

    @JsonProperty("payer_bank_inn")
    private String fromInn;

    @JsonProperty("payee_bank_mfo")
    private String toBankCode;

    @JsonProperty("payee_bank_account")
    private String toBankAccount;

    @JsonProperty("payee_bank")
    private String toBankName;

    @JsonProperty("payee_bank_inn")
    private String toInn;

    public PaymentData buildPaymentData() {
        PaymentData paymentData = new PaymentData();

        paymentData.setPaymentTime(this.getPaymentTime());
        paymentData.setAmount(this.amount);
        paymentData.setBlankNumber(this.blankNumber);
        paymentData.setBlankDate(this.getBlankDate());
        paymentData.setFromBankCode(this.fromBankCode);
        paymentData.setFromBankAccount(this.fromBankAccount);
        paymentData.setFromBankName(this.fromBankName);
        paymentData.setFromInn(this.fromInn);
        paymentData.setToBankCode(this.toBankCode);
        paymentData.setToBankAccount(this.toBankAccount);
        paymentData.setToBankName(this.toBankName);
        paymentData.setToInn(this.toInn);

        return paymentData;
    }

    public LocalDateTime getPaymentTime() {
        if (paymentTimeString == null) return null;
        return parse(paymentTimeString);
    }

    public LocalDate getBlankDate() {
        if (blankDateString == null) return null;
        return parse(blankDateString).toLocalDate();
    }

    private LocalDateTime parse(String timeString) {
        return Stream.of(
                        DateTimeFormatter.ISO_LOCAL_DATE_TIME,
                        DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
                ).map(p -> {
                    try {
                        return LocalDateTime.parse(timeString, p);
                    } catch (DateTimeParseException e) {
                        return null;
                    }
                }).filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new ValidationException(ErrorCode.DATE_PARSE_ERROR, "For string " + timeString));
    }
}
