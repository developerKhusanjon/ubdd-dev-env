package uz.ciasev.ubdd_service.mvd_core.api.billing.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EarlyDiscountResponseDTO {

    private Long fee;

    @JsonProperty("earlyPaymentDiscountDue")
    private LocalDate feeDate;
}
