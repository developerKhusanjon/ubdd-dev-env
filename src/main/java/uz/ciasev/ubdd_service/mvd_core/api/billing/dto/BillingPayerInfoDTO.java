package uz.ciasev.ubdd_service.mvd_core.api.billing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingPayerInfoDTO {

    @JsonProperty("branch")
    @NotNull(message = "branch field required")
    private String fromBankCode;

    @JsonProperty("account")
    @NotNull(message = "account field required")
    private String fromBankAccount;

    @JsonProperty("name")
    @NotNull(message = "name field required")
    private String fromBankName;

    @JsonProperty("inn")
    @NotNull(message = "inn field required")
    private String fromInn;
}
