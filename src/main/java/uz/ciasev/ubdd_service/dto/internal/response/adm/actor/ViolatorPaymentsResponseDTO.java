package uz.ciasev.ubdd_service.dto.internal.response.adm.actor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.resolution.execution.ManualPayment;
import uz.ciasev.ubdd_service.service.execution.InvoicePayment;
import uz.ciasev.ubdd_service.utils.ConvertUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ViolatorPaymentsResponseDTO {

    @JsonIgnore
    private final Long id;
    @JsonIgnore
    private final String idPrefix;
    private final Long amount;
    private final String number;
    private final LocalDateTime paymentTime;
    private final String invoiceSerial;
    private final Boolean isBilling;
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

    @JsonProperty("id")
    public String getUniqueId() {
        return ConvertUtils.getUniqueId(idPrefix, id);
    }

    public ViolatorPaymentsResponseDTO(InvoicePayment invoicePayment) {
        this.id = invoicePayment.getId();
        this.idPrefix = "b";
        this.amount = invoicePayment.getAmount();
        this.number = invoicePayment.getNumber();
        this.paymentTime = invoicePayment.getPaymentTime();
        this.invoiceSerial = invoicePayment.getInvoiceSerial();
        this.isBilling = true;
        this.bid = invoicePayment.getBid();
        this.blankNumber = invoicePayment.getBlankNumber();
        this.blankDate = invoicePayment.getBlankDate();
        this.fromBankCode = invoicePayment.getFromBankCode();
        this.fromBankAccount = invoicePayment.getFromBankAccount();
        this.fromBankName = invoicePayment.getFromBankName();
        this.fromInn = invoicePayment.getFromInn();
        this.toBankCode = invoicePayment.getToBankCode();
        this.toBankAccount = invoicePayment.getToBankAccount();
        this.toBankName = invoicePayment.getToBankName();
        this.toInn = invoicePayment.getToInn();
    }

    public ViolatorPaymentsResponseDTO(ManualPayment manualPayment) {
        this.id = manualPayment.getId();
        this.idPrefix = "m";
        this.amount = manualPayment.getAmount();
        this.number = null;
        this.paymentTime = manualPayment.getPaymentDate().atStartOfDay();
        this.invoiceSerial = null;
        this.isBilling = false;
        this.bid = null;
        this.blankNumber = null;
        this.blankDate = null;
        this.fromBankCode = null;
        this.fromBankAccount = null;
        this.fromBankName = null;
        this.fromInn = null;
        this.toBankCode = null;
        this.toBankAccount = null;
        this.toBankName = null;
        this.toInn = null;
    }
}
