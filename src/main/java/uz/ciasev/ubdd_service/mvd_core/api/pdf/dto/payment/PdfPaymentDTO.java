package uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PdfPaymentDTO {

    private String number;
    private String date;
    private String time;
    private String amount;
    private String amountText;
    private Boolean isBilling;

    private String fromBankCode;
    private String fromBankAccount;
    private String fromBankName;
    private String fromInn;

    private String toBankCode;
    private String toBankAccount;
    private String toBankName;
    private String toInn;
}