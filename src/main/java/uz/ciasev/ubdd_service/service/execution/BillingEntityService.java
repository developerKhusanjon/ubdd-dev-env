package uz.ciasev.ubdd_service.service.execution;

import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.invoice.InvoiceOwnerTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.execution.ManualPayment;
import uz.ciasev.ubdd_service.entity.violator.Violator;

import java.util.List;
import java.util.Optional;

public interface BillingEntityService {

    BillingData getBillingData(BillingEntity billingEntity);

    // Ввзврашает данные оплат полученные от биллинга (данные ручных оплат не учитываются)
    Optional<PaymentsData> getInvoicePaymentsData(BillingEntity billingEntity);

    Optional<PaymentsData> getManualPaymentsData(BillingEntity billingEntity);

    Optional<PaymentsData> getInvoicePaymentsData(Violator violator, BillingEntity billingEntity);

    List<InvoicePayment> getInvoicePayments(Violator violator, InvoiceOwnerTypeAlias invoiceTypeAlias);

    List<ManualPayment> getManualPayments(Violator violator, InvoiceOwnerTypeAlias invoiceTypeAlias);

    Optional<PaymentsData> getManualPaymentsData(Violator violator, BillingEntity billingEntity);

    BillingEntity getInvoiceOwner(Invoice invoice);

    boolean isResolutionActive(BillingEntity billingEntity);

    Optional<BillingEntity> findActiveBillingEntity(Violator violator, InvoiceOwnerTypeAlias invoiceTypeAlias);

    Violator getOwnerViolator(Invoice invoice);

    Violator getOwnerViolator(BillingEntity billingEntity);
}
