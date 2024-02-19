package uz.ciasev.ubdd_service.service.invoice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.adm.InvoiceResponseDTO;
import uz.ciasev.ubdd_service.dto.ubdd.UbddInvoiceRequest;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;
import uz.ciasev.ubdd_service.exception.implementation.LogicalException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;
import uz.ciasev.ubdd_service.repository.invoice.InvoiceRepository;
import uz.ciasev.ubdd_service.utils.PageUtils;

import java.util.Optional;

import static uz.ciasev.ubdd_service.entity.invoice.InvoiceOwnerTypeAlias.COMPENSATION;
import static uz.ciasev.ubdd_service.entity.invoice.InvoiceOwnerTypeAlias.PENALTY;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Override
    public void create(UbddInvoiceRequest request) {
        invoiceRepository.save(request.toEntity());
    }

    @Override
    public Invoice findById(Long id) {
        return invoiceRepository.findById(id).orElseThrow(() -> new EntityByIdNotFound(Invoice.class, id));
    }

    @Override
    public InvoiceResponseDTO findDTOById(Long id) {
        return new InvoiceResponseDTO(findById(id));
    }

    @Override
    public Invoice findByBillingId(Long id) {
        return invoiceRepository
                .findByInvoiceId(id)
                .orElseThrow(() -> new EntityByParamsNotFound(Invoice.class, "billingId", id));
    }

    @Override
    public Invoice update(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public Decision getInvoiceDecision(Invoice invoice) {
        if (invoice.getOwnerTypeAlias().equals(PENALTY)) {
            return invoiceRepository.findDecisionByInvoiceInPenaltyPunishment(invoice).orElseThrow(() -> new LogicalException("Invoice penalty decision not found"));
        } else if (invoice.getOwnerTypeAlias().equals(COMPENSATION)) {
            return invoiceRepository.findDecisionByInvoiceInCompensation(invoice).orElseThrow(() -> new LogicalException("Invoice compensation decision not found"));
        } else {
            throw new LogicalException("Invoice type has no decision");
        }
    }

    @Override
    public Optional<Invoice> findByPenalty(PenaltyPunishment penalty) {
        return invoiceRepository
                .findByPenaltyPunishmentId(penalty.getId(), PageUtils.topWithMaxId(1))
                .stream().findFirst();
    }

    @Override
    public Optional<Invoice> findPenaltyInvoiceByDecision(Decision decision) {
        return decision.getPenalty()
                .flatMap(this::findByPenalty);
    }

    @Override
    public Invoice getPenaltyInvoiceByDecision(Decision decision) {
        return findPenaltyInvoiceByDecision(decision)
                .orElseThrow(() -> new EntityByParamsNotFound(Invoice.class, "decisionId", decision.getId()));
    }
}
