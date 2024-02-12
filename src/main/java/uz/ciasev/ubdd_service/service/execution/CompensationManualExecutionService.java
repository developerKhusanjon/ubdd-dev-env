package uz.ciasev.ubdd_service.service.execution;


import uz.ciasev.ubdd_service.dto.internal.request.resolution.ManualPaymentRequestDTO;
import uz.ciasev.ubdd_service.entity.user.User;

public interface CompensationManualExecutionService {

    void deleteManualBilling(User user, Long compensationId);

    void executeWithoutBilling(User user, Long compensationId, ManualPaymentRequestDTO dto);
}
