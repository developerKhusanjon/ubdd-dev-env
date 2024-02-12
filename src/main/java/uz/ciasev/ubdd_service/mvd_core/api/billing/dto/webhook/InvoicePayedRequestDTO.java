package uz.ciasev.ubdd_service.mvd_core.api.billing.dto.webhook;

import lombok.Data;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.BillingPayeeInfoDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.BillingPayerInfoDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.BillingPaymentDTO;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Data
public class InvoicePayedRequestDTO {

    private Long id;

    private String serial;

    @Valid
    private BillingPayerInfoDTO payerInfo;

    @Valid
    private BillingPayeeInfoDTO payeeInfo;

    @Valid
    private List<BillingPaymentDTO> payments = new ArrayList<>();
}
