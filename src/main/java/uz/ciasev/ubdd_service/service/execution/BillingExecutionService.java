package uz.ciasev.ubdd_service.service.execution;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.BillingPaymentDTO;

@Validated
public interface BillingExecutionService {

    void handlePayment(BillingPaymentDTO dataResponseDTO);

    @Transactional
    void calculateAndSetExecution(BillingEntity billingEntity);

}
