package uz.ciasev.ubdd_service.mvd_core.api.billing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingInvoiceAmountRequestDTO {

    // All fields required
    @Builder.Default
    private String note = "";
    private Double amount;
    private String paymentTerms;
}
