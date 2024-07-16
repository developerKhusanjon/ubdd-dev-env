package uz.ciasev.ubdd_service.service.invoice;

import uz.ciasev.ubdd_service.dto.internal.response.adm.InvoiceResponseDTO;
import uz.ciasev.ubdd_service.dto.ubdd.UbddInvoiceRequest;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;

import java.util.Optional;

public interface InvoiceService {

    Invoice create(UbddInvoiceRequest request);

    Invoice findById(Long id);

    InvoiceResponseDTO findDTOById(Long id);

    Invoice findByBillingId(Long id);

    Invoice update(Invoice invoice);

    Decision getInvoiceDecision(Invoice invoice);

    Optional<Invoice> findByPenalty(PenaltyPunishment penalty);

    Optional<Invoice> findPenaltyInvoiceByDecision(Decision decision);

    Invoice getPenaltyInvoiceByDecision(Decision decision);

    Invoice findByAdmCaseId(Long id);
}
