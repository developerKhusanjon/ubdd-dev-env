package uz.ciasev.ubdd_service.service.execution;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.BillingPaymentDTO;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.invoice.InvoiceOwnerTypeAlias;
import uz.ciasev.ubdd_service.entity.invoice.Payment;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.execution.ExecutorType;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecutionDTO;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecutionType;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;
import uz.ciasev.ubdd_service.service.publicapi.eventdata.PublicApiWebhookEventPopulationService;
import uz.ciasev.ubdd_service.service.court.CourtPaymentService;
import uz.ciasev.ubdd_service.service.invoice.InvoiceService;
import uz.ciasev.ubdd_service.service.invoice.PaymentService;
import uz.ciasev.ubdd_service.service.resolution.compensation.CompensationActionService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;
import uz.ciasev.ubdd_service.service.resolution.punishment.PunishmentActionService;
import uz.ciasev.ubdd_service.service.resolution.punishment.PunishmentService;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillingExecutionServiceImpl implements BillingExecutionService {

    private final EnumMap<InvoiceOwnerTypeAlias, PaymentConsumer> executionMap = new EnumMap<>(InvoiceOwnerTypeAlias.class);

    protected final PaymentService paymentService;
    protected final PunishmentService punishmentService;
    protected final PunishmentActionService punishmentActionService;
    protected final CompensationActionService compensationService;
    protected final ExecutionCallbackService executionCallbackService;
    protected final InvoiceService invoiceService;
    protected final DecisionService decisionService;
    protected final CourtPaymentService courtService;
    protected final PublicApiWebhookEventPopulationService publicApiWebhookEventPopulationService;
    protected final BillingEntityService billingEntityService;

    {
        executionMap.put(InvoiceOwnerTypeAlias.PENALTY, this::executionPenalty);
        executionMap.put(InvoiceOwnerTypeAlias.COMPENSATION, this::executionCompensation);
        executionMap.put(InvoiceOwnerTypeAlias.DAMAGE, this::executionDamage);
    }

    @Override
    @Transactional(timeout = 60)
    public void handlePayment(BillingPaymentDTO paymentDTO) {

        if (paymentService.isProcessed(paymentDTO)) {
            return;
        }

        Invoice invoice = invoiceService.findByBillingId(paymentDTO.getInvoiceId());
        paymentService.save(invoice, paymentDTO);

        Payment savedPayment = paymentService.save(invoice, paymentDTO);

        BillingEntity billingEntity = billingEntityService.getInvoiceOwner(invoice);
        Violator violatorOwner = billingEntityService.getOwnerViolator(billingEntity);

        // publicApiWebhookEventPopulationService.addPaymentEvent(savedPayment, invoice, violatorOwner);
        courtService.acceptIfCourt(invoice, savedPayment);

        calculateAndSetExecution(billingEntity);
        applyToOtherDecisionIfNeed(violatorOwner, invoice, billingEntity);

    }

    @Override
    @Transactional
    public void calculateAndSetExecution(BillingEntity billingEntity) {
        BillingData billingData = billingEntityService.getBillingData(billingEntity);

        Optional<LocalDateTime> lastPaymentTimeOpt = billingData.getLastPayTime();
        Long paidAmount = billingData.getTotalAmount().orElse(0L);

        billingEntity.setLastPayTime(lastPaymentTimeOpt.orElse(null));
        billingEntity.setPaidAmount(paidAmount);

        executionMap.get(billingEntity.getInvoiceOwnerTypeAlias()).accept(billingEntity, billingData.getExecutorNames());
    }


    /**
     * @param violator            - нарушитель-владелиц квитанции. Сейчас оплаченная сумма считаеться именно по нарушителю.
     * @param invoice             - квитанция, по каторой пришла оплата
     * @param currentInvoiceOwner - текуший штраф/компенсация, к каторой привязаны квитанция
     *                            <p>
     *                            Иногда происходят задержки/сбои в работе с биллингом, и появляються оплаты, по уже отмененным квитанциям.
     *                            Раньше такие оплаты вызывали ошибку INVOICE_DEACTIVATED, копились в прокси-сервисе и позже обрабатывались в ручном режиме.
     *                            <p>
     *                            Если квитанция заблокированна временно (в связи с обжалованием в суде, передачей в миб), то надо просто принять поалту.
     *                            Если квитанция заблокированна в связи с отменой решения, то надо проробывать применит оплату к новому решению.
     */
    private void applyToOtherDecisionIfNeed(Violator violator, Invoice invoice, BillingEntity currentInvoiceOwner) {
        // для автивной квитанции оплату сажаем на ее текущего владельца и не паримся (всегда так работало)
        if (invoice.isActive()) return;

        // если резолюция текущего владельца квитанции активна, значит оплата села правельно
        if (billingEntityService.isResolutionActive(currentInvoiceOwner)) return;

        // для отмененных решений, надо попробывать применить оплату к новому решению, если оно есть.
        billingEntityService.findActiveBillingEntity(violator, currentInvoiceOwner.getInvoiceOwnerTypeAlias())
                .ifPresent(this::calculateAndSetExecution);
    }


    private void executionPenalty(BillingEntity billingEntity, List<String> executorNames) {
        Punishment punishment = (Punishment) billingEntity;

        PenaltyPunishment penalty = punishment.getPenalty();
        ForceExecutionDTO forceExecutionDTO;
        ExecutorType changeReason;

        if (penalty.getPaidAmount() == null || penalty.getPaidAmount() == 0) {
            forceExecutionDTO = null;
            changeReason = ExecutorType.BILLING;
        } else if (!penalty.isAnyDiscountAlive(penalty.getLastPayTime().toLocalDate())) {
            // Скидка уже не действует
            forceExecutionDTO = null;
            changeReason = ExecutorType.BILLING;
        } else if (isFullyPaid(penalty.getAmount(), penalty.getPaidAmount())) {
            // Скидка действует, но человек оплатил полную сумму, аначит факт использования скидки не формируем
            forceExecutionDTO = null;
            changeReason = ExecutorType.BILLING;
        } else if (penalty.isDiscount50Alive(penalty.getLastPayTime().toLocalDate()) && isFullyPaid(penalty.getDiscount50Amount(), penalty.getPaidAmount())) {
            // Человек использовал первую скидку
            forceExecutionDTO = new ForceExecutionDTO(penalty.getLastPayTime().toLocalDate(), ForceExecutionType.DISCOUNT);
            changeReason = ExecutorType.BILLING_WITH_DISCOUNT;
        } else if (penalty.isDiscount70Alive(penalty.getLastPayTime().toLocalDate()) && isFullyPaid(penalty.getDiscount70Amount(), penalty.getPaidAmount())) {
            // Человек использовал скидку вторую скидку
            forceExecutionDTO = new ForceExecutionDTO(penalty.getLastPayTime().toLocalDate(), ForceExecutionType.DISCOUNT);
            changeReason = ExecutorType.BILLING_WITH_DISCOUNT;
        } else {
            forceExecutionDTO = null;
            changeReason = ExecutorType.BILLING;
        }


        punishmentActionService.addExecution(punishment, changeReason, executorNames, forceExecutionDTO);

        Decision decision = decisionService.getById(punishment.getDecisionId());
        executionCallbackService.executeCallback(decision);
    }

    private void executionCompensation(BillingEntity billingEntity, List<String> executorNames) {
        Compensation compensation = compensationService.setExecution((Compensation) billingEntity, executorNames, null);

        executionCallbackService.executeCallback(decisionService.getById(compensation.getDecisionId()));
    }

    private void executionDamage(BillingEntity billingEntity, List<String> executorNames) {
        throw new NotImplementedException("Payment for damage");
    }

    private boolean isFullyPaid(Long actualAmount, Long paidAmount) {
        if (paidAmount == null) {
            return false;
        }
        int compareResult = paidAmount.compareTo(actualAmount);
        return compareResult >= 0;
    }

    @FunctionalInterface
    public interface PaymentConsumer {
        void accept(BillingEntity billingEntity, List<String> executorNames);
    }
}