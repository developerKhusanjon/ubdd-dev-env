package uz.ciasev.ubdd_service.service.invoice;

import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.invoice.InvoiceDeactivateReasonAlias;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;

public interface InvoiceActionService {

    Invoice createForPenalty(Decision decision);

    Invoice createForCompensation(Compensation compensation);

    Invoice editOwner(Invoice invoice, Compensation compensation);

    Invoice editOwner(Invoice invoice, PenaltyPunishment penaltyPunishment);

    void updateInvoiceInfoFromBilling(Long invoiceId);

    @Deprecated
    void closePermanently(User user, Invoice invoice, InvoiceDeactivateReasonAlias reason, List<Object> params);

    void closeForPenalty(PenaltyPunishment penaltyPunishment, InvoiceDeactivateReasonAlias reason, List<Object> params);

    void close(Invoice invoice, InvoiceDeactivateReasonAlias reason, List<Object> params);

    void closeBatch(List<Invoice> invoices, InvoiceDeactivateReasonAlias reason, List<Object> params);

    void openForPenalty(PenaltyPunishment penaltyPunishment);

    void open(Invoice invoice);

    @Deprecated
    void openPermanently(User user, Invoice invoice);

    void updateInvoiceAmount(Invoice invoice, PenaltyPunishment penaltyPunishment);

    void updateInvoiceAmount(Invoice invoice, Compensation compensation);

}
