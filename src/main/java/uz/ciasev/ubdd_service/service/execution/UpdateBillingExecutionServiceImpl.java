package uz.ciasev.ubdd_service.service.execution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.BillingPaymentDTO;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.invoice.Payment;
import uz.ciasev.ubdd_service.service.publicapi.eventdata.PublicApiWebhookEventPopulationService;
import uz.ciasev.ubdd_service.service.court.CourtPaymentService;
import uz.ciasev.ubdd_service.service.invoice.InvoiceService;
import uz.ciasev.ubdd_service.service.invoice.PaymentService;
import uz.ciasev.ubdd_service.service.resolution.compensation.CompensationActionService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;
import uz.ciasev.ubdd_service.service.resolution.punishment.PunishmentActionService;
import uz.ciasev.ubdd_service.service.resolution.punishment.PunishmentService;
import uz.ciasev.ubdd_service.utils.MoneyFormatter;

import java.util.Optional;

//@Service
public class UpdateBillingExecutionServiceImpl extends BillingExecutionServiceImpl {

    public UpdateBillingExecutionServiceImpl(PaymentService paymentService, PunishmentService punishmentService, PunishmentActionService punishmentActionService, CompensationActionService compensationService, ExecutionCallbackService executionCallbackService, InvoiceService invoiceService, DecisionService decisionService, CourtPaymentService courtService, PublicApiWebhookEventPopulationService publicApiWebhookEventPopulationService, BillingEntityService billingEntityService) {
        super(paymentService, punishmentService, punishmentActionService, compensationService, executionCallbackService, invoiceService, decisionService, courtService, publicApiWebhookEventPopulationService, billingEntityService);
    }

    @Autowired

    @Override
    @Transactional(timeout = 60)
    public void handlePayment(BillingPaymentDTO paymentDTO) {
        Optional<Payment> paymentOptional = paymentService.getProcessed(paymentDTO);
        if (paymentOptional.isEmpty()) {
            super.handlePayment(paymentDTO);
            return;
        }

        Payment payment = paymentOptional.get();
        if (payment.getAmount() == MoneyFormatter.currencyToCoin(paymentDTO.getAmount())) return;

        Payment updatedPayment = paymentService.update(payment, paymentDTO);
        Invoice invoice = invoiceService.findById(updatedPayment.getInvoiceId());
        BillingEntity billingEntity = billingEntityService.getInvoiceOwner(invoice);
        calculateAndSetExecution(billingEntity);

    }
}
