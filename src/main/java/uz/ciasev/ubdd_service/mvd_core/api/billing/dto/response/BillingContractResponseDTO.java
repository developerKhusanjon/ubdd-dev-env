package uz.ciasev.ubdd_service.mvd_core.api.billing.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingContractResponseDTO {

    private Long id;
    private String number;
    private Long amount;
    private String currency;
}
