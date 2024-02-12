package uz.ciasev.ubdd_service.service.execution;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.BillingPaymentDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface BillingExecutionService {

    void handlePayment(@NotNull @Valid BillingPaymentDTO dataResponseDTO);

    @Transactional
    BillingData calculateAndSetExecution(BillingEntity billingEntity);

//    @Transactional
//    void setExecutionToOwner(Invoice invoice);
}
