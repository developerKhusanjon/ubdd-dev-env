package uz.ciasev.ubdd_service.service.court;

import uz.ciasev.ubdd_service.entity.court.CourtInvoiceSending;
import uz.ciasev.ubdd_service.entity.court.CourtInvoiceType;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

import java.util.List;
import java.util.Optional;

public interface CourtInvoiceSendingOrderService {

    CourtInvoiceSending build(Long fromResolutionId, Decision toDecision);

    CourtInvoiceSending buildOnlyMove(Long fromResolutionId, Decision toDecision);

    void create(Long fromResolutionId, Decision toDecision);

    CourtInvoiceSending build(Long fromResolutionId, Compensation toCompensation);

    void create(Long fromResolutionId, Compensation toCompensation);

    void create(CourtInvoiceSending invoice);

    void create(List<CourtInvoiceSending> invoices);

    Optional<CourtInvoiceSending> findNextUnsentInvoices(CourtInvoiceType invoiceType);

    Optional<CourtInvoiceSending> findById(Long id);

    boolean handle(CourtInvoiceSending invoiceSending);
}
