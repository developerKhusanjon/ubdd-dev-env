package uz.ciasev.ubdd_service.service.invoice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.billing.service.BillingInvoiceApiService;
import uz.ciasev.ubdd_service.entity.court.BillingAction;
import uz.ciasev.ubdd_service.entity.court.BillingSending;
import uz.ciasev.ubdd_service.exception.implementation.ImplementationException;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillingSendingHandleService {

    private final BillingInvoiceApiService billingService;

    public void handle(BillingSending invoice) {
        if (invoice.getAction() == BillingAction.OPEN_INVOICE) {
            billingService.openInvoice(invoice.getInvoiceId());
        } else if (invoice.getAction() == BillingAction.CANCEL_INVOICE) {
            billingService.cancelInvoice(invoice.getInvoiceId(), invoice.getReason());
        } else {
            throw new ImplementationException(String.format("Unknown BillingAction %s", invoice.getAction()));
        }

    }
}
