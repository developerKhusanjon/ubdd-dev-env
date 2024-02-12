package uz.ciasev.ubdd_service.service.execution;

import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.ViolatorPaymentsResponseDTO;
import uz.ciasev.ubdd_service.entity.invoice.InvoiceOwnerTypeAlias;

import java.util.List;

public interface ViolatorExecutionService {
    List<ViolatorPaymentsResponseDTO> findAllPaymentsDTOById(Long violatorId, InvoiceOwnerTypeAlias paymentType);
    List<ViolatorPaymentsResponseDTO> findPenaltyPaymentsDTOById(Long violatorId);
    List<ViolatorPaymentsResponseDTO> findCompensationPaymentsDTOById(Long violatorId);
}
