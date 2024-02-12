package uz.ciasev.ubdd_service.mvd_core.api.billing.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingBankResponseDTO {

    private Long id;
    private String name;
    private String mfo;
    private String inn;
    private String type;
    private String branch;
    private String address;
}
