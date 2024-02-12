package uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.karor_mail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PdfInvoiceDTO {

    private String number;                      // "123453675432",
    private String qr;                          // "base64String",
    private String bank;                        // "bank name",
    private String organ;                       // "organ name",
    private String account;                     // "23402000300100001010",
    private String treasuryAccount;             // "123456754321",
    private String bankCode;                    // "12312",
    private String inn;                         // "inn",
    private String payer;                       // "payer",
    private String payerAddress;                // "payerAddress",
    private String birthDate;                   // "30.09.2021",
    private String payerInn;                    // "payerInn",
    private String paymentType;                 // "paymentType",
    private String date;                        // "30.09.2021",
    private String amount;                      // "30'000.00",
    private String amountText;                  // "trdcat tysyach",

    //старые поля
    @JsonProperty("isDiscount")
    @Deprecated private boolean isDiscount;                 // true,
    @Deprecated private String discountDate;                // "30.02.2021",
    @Deprecated private String discountAmount;           // "70'000.00",
    @Deprecated private String discountAmountText;          // "semdesyat tysyach"

    // новые поля
    @JsonProperty("isDiscount50")
    private boolean isDiscount50;
    private String discount50Date;
    private String discount50Amount;
    private String discount50AmountText;

    @JsonProperty("isDiscount70")
    private boolean isDiscount70;
    private String discount70Date;
    private String discount70Amount;

    public void setDiscount70(boolean discount70) {
        this.isDiscount = discount70;
        isDiscount70 = discount70;
    }

    public void setDiscount70Date(String discount70Date) {
        this.discountDate = discount70Date;
        this.discount70Date = discount70Date;
    }

    public void setDiscount70Amount(String discount70Amount) {
        this.discountAmount = discount70Amount;
        this.discount70Amount = discount70Amount;
    }

    public void setDiscount70AmountText(String discount70AmountText) {
        this.discountAmountText = discount70AmountText;
        this.discount70AmountText = discount70AmountText;
    }

    private String discount70AmountText;


}
