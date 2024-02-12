package uz.ciasev.ubdd_service.service.invoice;

import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.BillingPaymentDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.PaymentDetailResponseDTO;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.invoice.Payment;
import uz.ciasev.ubdd_service.entity.invoice.PaymentDataProjection;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PaymentService {

    List<Payment> findByInvoiceId(Long invoiceId);

    List<PaymentDetailResponseDTO> findDetailByInvoiceId(Long invoiceId);

    Payment save(Invoice invoice, BillingPaymentDTO billingPaymentDTO);

    Optional<Payment> findById(Long paymentId);

    boolean isProcessed(BillingPaymentDTO paymentDTO);

    Optional<Payment> getProcessed(BillingPaymentDTO paymentDTO);

    Optional<Payment> getLastPaymentForInvoices(Collection<Long> invoicesId);

    List<PaymentDataProjection> findPaymentByInvoices(Collection<Long> invoicesId);

    Optional<Long> getTotalAmountForInvoices(Collection<Long> invoicesId);

    Payment update(Payment payment, BillingPaymentDTO paymentDTO);
}
