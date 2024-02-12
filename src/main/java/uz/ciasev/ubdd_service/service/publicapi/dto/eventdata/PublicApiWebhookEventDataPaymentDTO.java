package uz.ciasev.ubdd_service.service.publicapi.dto.eventdata;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PublicApiWebhookEventDataPaymentDTO {

    private Long invoiceId;
    private Long violatorId;
    private Short supplierType;
    private LocalDateTime supplierDate;
    private Long amount;
    private String blankNumber;
    private String blankDate;
    private String payerBankMfo;
    private String payerBankAccount;
    private String payerBank;
    private String payerBankInn;
    private String payeeBankMfo;
    private String payeeBankAccount;
    private String payeeBank;
    private String payeeBankInn;
}
