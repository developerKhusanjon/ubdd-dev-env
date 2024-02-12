package uz.ciasev.ubdd_service.service.execution;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.ViolatorPaymentsResponseDTO;
import uz.ciasev.ubdd_service.entity.invoice.InvoiceOwnerTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.execution.ManualPayment;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.service.violator.ViolatorService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ViolatorExecutionServiceImpl implements ViolatorExecutionService {
    private final BillingEntityService billingEntityService;
    private final ViolatorService violatorService;

    @Override
    public List<ViolatorPaymentsResponseDTO> findPenaltyPaymentsDTOById(Long violatorId) {
        return findAllPaymentsDTOById(violatorId, InvoiceOwnerTypeAlias.PENALTY);
    }

    @Override
    public List<ViolatorPaymentsResponseDTO> findCompensationPaymentsDTOById(Long violatorId) {
        return findAllPaymentsDTOById(violatorId, InvoiceOwnerTypeAlias.COMPENSATION);
    }

    @Override
    public List<ViolatorPaymentsResponseDTO> findAllPaymentsDTOById(Long violatorId, InvoiceOwnerTypeAlias paymentType) {
        Violator violator = violatorService.getById(violatorId);

        return Stream.concat(
                        getInvoicePayments(violator, paymentType).stream(),
                        getManualPayments(violator, paymentType).stream())
                        .sorted(Comparator.comparing(ViolatorPaymentsResponseDTO::getPaymentTime).reversed())
                        .collect(Collectors.toList());
    }

    public List<ViolatorPaymentsResponseDTO> getInvoicePayments(Violator violator, InvoiceOwnerTypeAlias paymentType) {
        return billingEntityService
                .getInvoicePayments(violator, paymentType)
                .stream()
                .map(this::convertToInvoiceDTO)
                .collect(Collectors.toList());
    }

    public List<ViolatorPaymentsResponseDTO> getManualPayments(Violator violator, InvoiceOwnerTypeAlias paymentType) {
        return billingEntityService
                .getManualPayments(violator, paymentType)
                .stream()
                .map(this::convertToManualDTO)
                .collect(Collectors.toList());
    }

    public ViolatorPaymentsResponseDTO convertToInvoiceDTO(InvoicePayment invoicePayment) {
        if (invoicePayment == null) {
            return null;
        }

        return new ViolatorPaymentsResponseDTO(invoicePayment);
    }

    public ViolatorPaymentsResponseDTO convertToManualDTO(ManualPayment manualPayment) {
        if (manualPayment == null) {
            return null;
        }

        return new ViolatorPaymentsResponseDTO(manualPayment);
    }
}
