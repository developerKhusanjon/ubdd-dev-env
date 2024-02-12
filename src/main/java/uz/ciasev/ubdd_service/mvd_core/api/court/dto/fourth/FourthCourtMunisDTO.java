package uz.ciasev.ubdd_service.mvd_core.api.court.dto.fourth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class FourthCourtMunisDTO {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonProperty("supplierDate")
    private String paymentTime;
    private Long amount;
    private String paymentId;
    private String blankNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate blankDate;

    @JsonProperty("payerBankMfo")
    private String fromBankCode;
    @JsonProperty("payerBankAccount")
    private String fromBankAccount;
    @JsonProperty("payerBank")
    private String fromBankName;
    @JsonProperty("payerBankInn")
    private String fromInn;

    @JsonProperty("payeeBankMfo")
    private String toBankCode;
    @JsonProperty("payeeBankAccount")
    private String toBankAccount;
    @JsonProperty("payeeBank")
    private String toBankName;
    @JsonProperty("payeeBankInn")
    private String toInn;
}
