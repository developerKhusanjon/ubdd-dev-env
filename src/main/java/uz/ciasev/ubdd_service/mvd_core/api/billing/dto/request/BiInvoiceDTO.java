package uz.ciasev.ubdd_service.mvd_core.api.billing.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BiInvoiceDTO {

    private String requestId;
    private Long serviceId;
    private Long departmentId;
    private Long payerId;
    private Long contractId;
    private String note;
    private String paymentTerms;

    private Long quantity;  //  not used
    private List<String> discounts; //  not used
    private String serial;
}
