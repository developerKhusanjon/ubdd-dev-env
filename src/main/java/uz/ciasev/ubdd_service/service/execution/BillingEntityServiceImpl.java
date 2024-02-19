package uz.ciasev.ubdd_service.service.execution;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.invoice.InvoiceOwnerTypeAlias;
import uz.ciasev.ubdd_service.entity.invoice.Payment;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.execution.ManualPayment;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.implementation.ImplementationException;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;
import uz.ciasev.ubdd_service.repository.invoice.InvoiceRepository;
import uz.ciasev.ubdd_service.service.damage.DamageSettlementDetailService;
import uz.ciasev.ubdd_service.service.invoice.ManualPaymentService;
import uz.ciasev.ubdd_service.service.invoice.PaymentService;
import uz.ciasev.ubdd_service.service.resolution.compensation.CompensationService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;
import uz.ciasev.ubdd_service.service.resolution.punishment.PunishmentService;
import uz.ciasev.ubdd_service.service.resolution.ResolutionService;
import uz.ciasev.ubdd_service.utils.DateTimeUtils;
import uz.ciasev.ubdd_service.utils.LongUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BillingEntityServiceImpl implements BillingEntityService {
    private final PaymentService paymentService;
    private final CompensationService compensationService;
    private final PunishmentService punishmentService;
    private final InvoiceRepository invoiceRepository;
    private final ManualPaymentService manualPaymentService;
    private final ResolutionService resolutionService;
    private final DecisionService decisionService;
    private final DamageSettlementDetailService damageSettlementDetailService;

    @Override
    public BillingData getBillingData(BillingEntity billingEntity) {
        Violator violator = getOwnerViolator(billingEntity);

        Optional<PaymentsData> invoicePaymentsData = getInvoicePaymentsData(violator, billingEntity);
        Optional<PaymentsData> manualPaymentsData = getManualPaymentsData(violator, billingEntity);

        if (invoicePaymentsData.isEmpty() && manualPaymentsData.isEmpty()) {
            return new BillingData(false, violator, Optional.empty(), Optional.empty(), List.of());
        }

        Optional<Long> totalAmount = LongUtils.sum(
                invoicePaymentsData.map(PaymentsData::getTotalAmount),
                manualPaymentsData.map(PaymentsData::getTotalAmount)
        );

        Optional<LocalDateTime> lastPaymentTime = DateTimeUtils.max(
                invoicePaymentsData.map(PaymentsData::getLastPayTime),
                manualPaymentsData.map(PaymentsData::getLastPayTime)
        );

        List<String> executorNames = new ArrayList<>();
        invoicePaymentsData.map(PaymentsData::getExecutorNames).ifPresent(executorNames::addAll);
        manualPaymentsData.map(PaymentsData::getExecutorNames).ifPresent(executorNames::addAll);

        return new BillingData(true, violator, lastPaymentTime, totalAmount, executorNames);
    }

    @Override
    public Optional<PaymentsData> getInvoicePaymentsData(BillingEntity billingEntity) {
        Violator violator = getOwnerViolator(billingEntity);
        return getInvoicePaymentsData(violator, billingEntity);
    }

    @Override
    public Optional<PaymentsData> getManualPaymentsData(BillingEntity billingEntity) {
        Violator violator = getOwnerViolator(billingEntity);
        return getManualPaymentsData(violator, billingEntity);
    }

    @Override
    public Optional<PaymentsData> getInvoicePaymentsData(Violator violator, BillingEntity billingEntity) {
        Collection<Long> invoiceIdList;
        if (billingEntity.getInvoiceOwnerTypeAlias().isAggregatable()) {
            invoiceIdList = getInvoiceIdListByType(violator, billingEntity.getInvoiceOwnerTypeAlias());
        } else {
            invoiceIdList = getInvoiceIdListByEntity(billingEntity);
        }

        if (invoiceIdList.isEmpty()) {
            return Optional.empty();
        }

        Optional<Payment> lastPaymentOpt = paymentService.getLastPaymentForInvoices(invoiceIdList);
        if (lastPaymentOpt.isEmpty()) {
            return Optional.empty();
        }

        PaymentsData data = PaymentsData.builder()
                .lastPayTime(lastPaymentOpt.get().getPaymentTime())
                .totalAmount(paymentService.getTotalAmountForInvoices(invoiceIdList).get())
                .executorNames(List.of("YAGONA BILLING"))
                .build();

        return Optional.of(data);
    }

    @Override
    public List<InvoicePayment> getInvoicePayments(Violator violator, InvoiceOwnerTypeAlias invoiceTypeAlias) {
        Collection<Long> invoicesId = getInvoiceIdListByType(violator, invoiceTypeAlias);

        if (invoicesId.isEmpty()) {
            return List.of();
        }

        return paymentService
                .findPaymentByInvoices(invoicesId)
                .stream()
                .map(InvoicePayment::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ManualPayment> getManualPayments(Violator violator, InvoiceOwnerTypeAlias invoiceTypeAlias) {
        Collection<Long> manualPaymentsId = getManualPaymentIdListByType(violator, invoiceTypeAlias);
        return manualPaymentService.findAllById(manualPaymentsId);
    }

    @Override
    public Optional<PaymentsData> getManualPaymentsData(Violator violator, BillingEntity billingEntity) {
        Collection<Long> manualPaymentsId;
        if (billingEntity.getInvoiceOwnerTypeAlias().isAggregatable()) {
            manualPaymentsId = getManualPaymentIdListByType(violator, billingEntity.getInvoiceOwnerTypeAlias());
        } else {
            manualPaymentsId = getManualPaymentIdListByType(billingEntity);
        }

        if (manualPaymentsId.isEmpty()) {
            return Optional.empty();
        }

        Optional<ManualPayment> lastManualPaymentOpt = manualPaymentService.getLastPaymentForm(manualPaymentsId);
        if (lastManualPaymentOpt.isEmpty()) {
            return Optional.empty();
        }

        List<String> executorNames = manualPaymentService.getCreateUsers(manualPaymentsId).stream()
                .map(punishmentService::buildExecutorNameForUser)
                .collect(Collectors.toList());

        PaymentsData data = PaymentsData.builder()
                .lastPayTime(lastManualPaymentOpt.get().getPaymentDate().atTime(LocalTime.MIN))
                .totalAmount(manualPaymentService.getTotalAmountForm(manualPaymentsId).get())
                .executorNames(executorNames)
                .build();

        return Optional.of(data);
    }

    @Override
    public BillingEntity getInvoiceOwner(Invoice invoice) {
        return applyByType(
                invoice,
                punishmentService::findByPenaltyId,
                compensationService::findById,
                damageSettlementDetailService::getById
        );
    }

    @Override
    public boolean isResolutionActive(BillingEntity billingEntity) {
        return applyByType(
                billingEntity,
                resolutionService::isResolutionActiveByPunishmentId,
                resolutionService::isResolutionActiveByCompensationId,
                id -> true
        );
    }

    @Override
    public Optional<BillingEntity> findActiveBillingEntity(Violator violator, InvoiceOwnerTypeAlias invoiceTypeAlias) {

        return applyByType(
                invoiceTypeAlias,
                violator,
                violatorInner -> decisionService.findActiveByViolatorId(violatorInner.getId()).map(Decision::getMainPunishment),
                violatorInner -> Optional.ofNullable(compensationService.findActiveGovByViolator(violatorInner).orElse(null)),
                () -> new ImplementationException("All BillingEntities of type DamageSettlementDetail are always active")
        );
    }

    @Override
    public Violator getOwnerViolator(Invoice invoice) {
//        switch (invoice.getOwnerType().getAlias()) {
//            case COMPENSATION:
//                return compensationService.findViolatorByCompensationId(invoice.getCompensationId());
//            case PENALTY:
//                return punishmentService.findViolatorByPenaltyId(invoice.getPenaltyPunishmentId());
//            default:
//                throw new NotImplementedException("Invoice owner type");
//        }

        return applyByType(
                invoice,
                punishmentService::findViolatorByPenaltyId,
                compensationService::findViolatorByCompensationId,
                damageSettlementDetailService::findViolatorByDamageSettlementDetailId
        );
    }

    @Override
    public Violator getOwnerViolator(BillingEntity billingEntity) {
//        switch (billingEntity.getInvoiceOwnerTypeAlias()) {
//            case COMPENSATION:
//                return compensationService.findViolatorByCompensationId(billingEntity.getId());
//            case PENALTY:
//                return punishmentService.findViolatorByPunishmentId(billingEntity.getId());
//            default:
//                throw new NotImplementedException("Invoice owner type");
//        }

        return applyByType(
                billingEntity,
                punishmentService::findViolatorByPunishmentId,
                compensationService::findViolatorByCompensationId,
                damageSettlementDetailService::findViolatorByDamageSettlementDetailId
        );
    }

    private Collection<Long> getInvoiceIdListByType(Violator violator, InvoiceOwnerTypeAlias billingEntityType) {
//        switch (billingEntityType) {
//            case COMPENSATION:
//                return invoiceRepository.findCompensationInvoiceIdByViolator(violator);
//            case PENALTY:
//                return invoiceRepository.findPunishmentInvoiceIdByViolator(violator);
//            default:
//                throw new NotImplementedException("Invoice owner type");
//        }

        return applyByType(
                billingEntityType,
                violator,
                invoiceRepository::findPunishmentInvoiceIdByViolator,
                invoiceRepository::findCompensationInvoiceIdByViolator,
                () -> new ImplementationException(String.format("Invoices cannot be aggregated by violator for billing entity type of %s", billingEntityType))
        );
    }

    private Collection<Long> getInvoiceIdListByEntity(BillingEntity billingEntity) {
        return applyByTypeForNonAggregatable(
                billingEntity.getInvoiceOwnerTypeAlias(),
                billingEntity.getId(),
                invoiceRepository::findInvoiceIdByDamageSettlementDetailId);
    }

    private Collection<Long> getManualPaymentIdListByType(Violator violator, InvoiceOwnerTypeAlias billingEntityType) {
//        switch (billingEntityType) {
//            case COMPENSATION:
//                return manualPaymentService.findCompensationManualPaymentIdByViolator(violator);
//            case PENALTY:
//                return manualPaymentService.findPunishmentManualPaymentIdByViolator(violator);
//            default:
//                throw new NotImplementedException("Invoice owner type");
//        }

        return applyByType(
                billingEntityType,
                violator,
                manualPaymentService::findPunishmentManualPaymentIdByViolator,
                manualPaymentService::findCompensationManualPaymentIdByViolator,
                () -> new ImplementationException(String.format("Manual payments cannot be aggregated by violator for billing entity type of %s", billingEntityType))
        );
    }

    private Collection<Long> getManualPaymentIdListByType(BillingEntity billingEntity) {
        return applyByTypeForNonAggregatable(
                billingEntity.getInvoiceOwnerTypeAlias(),
                billingEntity.getId(),
                manualPaymentService::findManualPaymentIdByDamageSettlementDetailId
        );
    }

    private <T> T applyByType(InvoiceOwnerTypeAlias billingEntityType,
                            Violator violator,
                            Function<Violator, T> forPenalty,
                            Function<Violator, T> forCompensation,
                            Supplier<? extends ApplicationException> damageException) {
        switch (billingEntityType) {
            case COMPENSATION:
                return forCompensation.apply(violator);
            case PENALTY:
                return forPenalty.apply(violator);
            case DAMAGE:
                throw damageException.get();
            default:
                throw new NotImplementedException("Invoice owner type");
        }
    }

    private <T> T applyByTypeForNonAggregatable(
            InvoiceOwnerTypeAlias billingEntityType,
            Long billingEntityId,
            Function<Long, T> forDamage) {
        switch (billingEntityType) {
            case DAMAGE:
                return forDamage.apply(billingEntityId);
            default:
                throw new NotImplementedException("Invoice owner type");
        }
    }

    private <T> T applyByType(
            BillingEntity billingEntity,
            Function<Long, T> forPenalty,
            Function<Long, T> forCompensation,
            Function<Long, T> forDamage) {
        switch (billingEntity.getInvoiceOwnerTypeAlias()) {
            case COMPENSATION:
                return forCompensation.apply(billingEntity.getId());
            case PENALTY:
                return forPenalty.apply(billingEntity.getId());
            case DAMAGE:
                return forDamage.apply(billingEntity.getId());
            default:
                throw new NotImplementedException("Invoice owner type");
        }
    }

    private <T> T applyByType(
            Invoice invoice,
            Function<Long, T> forPenalty,
            Function<Long, T> forCompensation,
            Function<Long, T> forDamage) {
        switch (invoice.getOwnerTypeAlias()) {
            case COMPENSATION:
                return forCompensation.apply(invoice.getCompensationId());
            case PENALTY:
                return forPenalty.apply(invoice.getPenaltyPunishmentId());
            case DAMAGE:
                return forDamage.apply(invoice.getDamageSettlementDetailId());
            default:
                throw new NotImplementedException("Invoice owner type");
        }
    }
}
