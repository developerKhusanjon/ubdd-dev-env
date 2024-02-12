package uz.ciasev.ubdd_service.service.court;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.court.CourtInvoiceSending;
import uz.ciasev.ubdd_service.entity.court.CourtInvoiceType;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.implementation.LogicalException;
import uz.ciasev.ubdd_service.repository.court.CourtInvoiceSendingRepository;
import uz.ciasev.ubdd_service.utils.PageUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourtInvoiceSendingOrderServiceImpl implements CourtInvoiceSendingOrderService {
    private final CourtInvoiceSendingRepository courtInvoiceSendingRepository;
    private final CourtInvoiceSendingHandleService handelService;

    @Override
    public CourtInvoiceSending build(Long fromResolutionId, Decision toDecision) {
        return new CourtInvoiceSending(fromResolutionId, CourtInvoiceType.PENALTY, toDecision.getId(), false);
    }

    @Override
    public CourtInvoiceSending buildOnlyMove(Long fromResolutionId, Decision toDecision) {
        if (fromResolutionId == -1L) {
            throw new LogicalException("CourtInvoiceSending for only move old invoice mast present old resolution id");
        }
        return new CourtInvoiceSending(fromResolutionId, CourtInvoiceType.PENALTY, toDecision.getId(), true);
    }

    @Override
    public void create(Long fromResolutionId, Decision toDecision) {
        create(build(fromResolutionId, toDecision));
    }

    @Override
    public CourtInvoiceSending build(Long fromResolutionId, Compensation toCompensation) {
        return new CourtInvoiceSending(fromResolutionId, CourtInvoiceType.COMPENSATION, toCompensation.getId(), false);
    }

    @Override
    public void create(Long fromResolutionId, Compensation toCompensation) {
        create(build(fromResolutionId, toCompensation));
    }

    @Override
    public void create(CourtInvoiceSending invoice) {
        courtInvoiceSendingRepository.saveAndFlush(invoice);
    }

    @Override
    @Transactional
    public void create(List<CourtInvoiceSending> invoices) {
        courtInvoiceSendingRepository.saveAll(invoices);
        courtInvoiceSendingRepository.flush();
    }

    @Override
    public Optional<CourtInvoiceSending> findNextUnsentInvoices(CourtInvoiceType invoiceType) {
        List<CourtInvoiceSending> res = courtInvoiceSendingRepository.findUnsentInvoice(invoiceType, PageUtils.oneWithMinId());
        return res.stream().findFirst();
    }

    @Override
    public Optional<CourtInvoiceSending> findById(Long id) {
        return courtInvoiceSendingRepository.findById(id);
    }

    @Override
    public boolean handle(CourtInvoiceSending invoiceSending) {
        try {

            handelService.handle(invoiceSending);
            invoiceSending.sentSuccess();
//            invoiceSending.setHasError(false);

        } catch (ApplicationException e) {
            invoiceSending.sentWithError(String.format("%s: %s", e.getCode(), e.getDetail()));
//            invoiceSending.setError(String.format("%s: %s", e.getCode(), e.getDetail()));
//            invoiceSending.setHasError(true);
        } catch (Exception e) {
            invoiceSending.sentWithError("Exception: " + e.getMessage());
//            invoiceSending.setError("Exception: " + e.getMessage());
//            invoiceSending.setHasError(true);
        }

//        invoiceSending.setIsSent(true);
        courtInvoiceSendingRepository.save(invoiceSending);

        return !invoiceSending.getHasError();
    }
}
