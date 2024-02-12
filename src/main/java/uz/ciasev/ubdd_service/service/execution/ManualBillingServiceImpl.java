package uz.ciasev.ubdd_service.service.execution;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.ManualPaymentRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias;
import uz.ciasev.ubdd_service.entity.invoice.InvoiceOwnerTypeAlias;
import uz.ciasev.ubdd_service.entity.history.ManualExecutionDeleteRegistrationType;
import uz.ciasev.ubdd_service.entity.mib.PaymentData;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.execution.ExecutorType;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecutionType;
import uz.ciasev.ubdd_service.entity.resolution.execution.ManualPayment;
import uz.ciasev.ubdd_service.entity.dict.resolution.ManualPaymentSourceTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.implementation.ImplementationException;
import uz.ciasev.ubdd_service.exception.implementation.LogicalException;
import uz.ciasev.ubdd_service.service.history.HistoryService;
import uz.ciasev.ubdd_service.service.invoice.ManualPaymentService;
import uz.ciasev.ubdd_service.service.resolution.compensation.CompensationActionService;
import uz.ciasev.ubdd_service.service.resolution.punishment.PunishmentActionService;
import uz.ciasev.ubdd_service.service.status.StatusService;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static uz.ciasev.ubdd_service.entity.dict.resolution.ManualPaymentSourceTypeAlias.*;
import static uz.ciasev.ubdd_service.entity.status.AdmStatusAlias.EXECUTED;
import static uz.ciasev.ubdd_service.entity.status.AdmStatusAlias.IN_EXECUTION_PROCESS;

@Service
public class ManualBillingServiceImpl implements ManualBillingService {

    private final BillingExecutionService billingExecutionService;
    private final ManualPaymentService manualPaymentService;
    private final ObjectMapper objectMapper;
    private final StatusService admStatusService;
    private final ExecutionCallbackService executionCallbackService;
    private final HistoryService historyService;

    private final EnumMap<InvoiceOwnerTypeAlias, Consumer<BillingEntity>> canselExecutionHandlerMap;
    private final EnumMap<InvoiceOwnerTypeAlias, BiConsumer<ManualPayment, BillingEntity>> ownerSetttMap;

    public ManualBillingServiceImpl(BillingExecutionService billingExecutionService, ManualPaymentService manualPaymentService, PunishmentActionService punishmentActionService, ObjectMapper objectMapper, StatusService admStatusService, ExecutionCallbackService executionCallbackService, CompensationActionService compensationActionService, HistoryService historyService) {
        this.billingExecutionService = billingExecutionService;
        this.manualPaymentService = manualPaymentService;
        this.objectMapper = objectMapper;
        this.admStatusService = admStatusService;
        this.executionCallbackService = executionCallbackService;
        this.historyService = historyService;

        this.canselExecutionHandlerMap = new EnumMap<>(InvoiceOwnerTypeAlias.class);
        this.canselExecutionHandlerMap.put(InvoiceOwnerTypeAlias.PENALTY, punishment -> punishmentActionService.deleteExecution((Punishment) punishment, ExecutorType.BILLING, "", ForceExecutionType.DISCOUNT));
        this.canselExecutionHandlerMap.put(InvoiceOwnerTypeAlias.COMPENSATION, compensation -> compensationActionService.deleteExecution((Compensation) compensation, "", null));

        this.ownerSetttMap = new EnumMap<>(InvoiceOwnerTypeAlias.class);
        this.ownerSetttMap.put(InvoiceOwnerTypeAlias.PENALTY, (payment, punishment) -> payment.setPenaltyPunishment(getPenalty((Punishment) punishment)));
        this.ownerSetttMap.put(InvoiceOwnerTypeAlias.COMPENSATION, (payment, compensation) -> payment.setCompensation((Compensation) compensation));

    }

    @Override
    public void replaceMibPayments(Punishment punishment, List<PaymentData> payments) {
        deletePayments(punishment, MIB_EXECUTION);

        if (payments != null && !payments.isEmpty()) {
            payments.forEach(paymentData -> createMibPayment(punishment, paymentData));
        }

        // BILLING LIKE STATUS CALCULATE
        billingExecutionService.calculateAndSetExecution(punishment);
    }

    @Override
    public void deleteMibPayments(Punishment punishment) {
        PenaltyPunishment penaltyPunishment = getPenalty(punishment);

        deletePayments(punishment, MIB_EXECUTION);
        billingExecutionService.calculateAndSetExecution(punishment);
    }

    @Override
    public void createAdminPayment(User user, BillingEntity billingEntity, ManualPaymentRequestDTO dto) {
        BiConsumer<ManualPayment, BillingEntity> ownerSetter = getTypedFunction(this.ownerSetttMap, billingEntity.getInvoiceOwnerTypeAlias());

        ManualPayment manualPayment = build(billingEntity, ADMIN_ENTRY, dto.getPaidAmount(), dto.getLastPayDate());
        manualPayment.setUser(user);

        manualPaymentService.create(manualPayment);

        // STATUSES
        billingExecutionService.calculateAndSetExecution(billingEntity);
    }

    public boolean hasAdminPayment(BillingEntity entity) {
        return manualPaymentService.existsByEntity(entity, ADMIN_ENTRY, MIGRATION);
    }

    @Override
    @Transactional
    public void deleteAdminPayment(User user, Decision decision, BillingEntity billingEntity) {
        if (!hasAdminPayment(billingEntity)) {
            return;
        }

        Consumer<BillingEntity> canselExecutionHandler = getTypedFunction(this.canselExecutionHandlerMap, billingEntity.getInvoiceOwnerTypeAlias());

        deletePayments(billingEntity, ADMIN_ENTRY);
        deletePayments(billingEntity, MIGRATION);
        billingEntity.setPaidAmount(0l); // без этого пересчет исполнения происходит не правельно.
        //  Потаму в deleteExecution вычисляеться статус по новое, и в вычисление используется это поле. если не зателеть сумму, то вычислится и застеиться статаус 11 или 2
        // Если сетить суму 0, то всегда вычислиться 2, каторый потом в calculateAndSetExecution пересчитатеся на правельный.

        canselExecutionHandler.accept(billingEntity);

        admStatusService.cancelStatus(decision, EXECUTED, IN_EXECUTION_PROCESS);
        admStatusService.cancelStatus(decision.getResolution(), EXECUTED, IN_EXECUTION_PROCESS);
        executionCallbackService.executeCallbackWithoutLazy(decision);

        billingExecutionService.calculateAndSetExecution(billingEntity);

        historyService.deleteManualExecution(billingEntity, ManualExecutionDeleteRegistrationType.ADMIN_MANUAL_PAYMENT);

    }

//    @Override
//    @Transactional
//    public void deleteAdminPayment(User user, Decision decision, Punishment punishment) {
//        PenaltyPunishment penaltyPunishment = getPenalty(punishment);
//
//        deletePayments(punishment, ADMIN_ENTRY);
//        deletePayments(punishment, MIGRATION);
//        penaltyPunishment.setPaidAmount(0l); // без этого пересчет исполнения происходит не правельно.
//        //  Потаму в deleteExecution вычисляеться статус по новое, и в вычисление используется это поле. если не зателеть сумму, то вычислится и застеиться статаус 11 или 2
//        // Если сетить суму 0, то всегда вычислиться 2, каторый потом в calculateAndSetExecution пересчитатеся на правельный.
//        punishmentActionService.deleteExecution(punishment, ExecutorType.BILLING, "", ForceExecutionType.DISCOUNT);
//        admStatusService.cancelStatus(decision, EXECUTED, IN_EXECUTION_PROCESS);
//        admStatusService.cancelStatus(decision.getResolution(), EXECUTED, IN_EXECUTION_PROCESS);
//        executionCallbackService.executeCallbackWithoutLazy(decision);
//
//        billingExecutionService.calculateAndSetExecution(punishment);
//
//    }
//
//    @Override
//    @Transactional
//    public void deleteAdminPayment(User user, Decision decision, Compensation compensation) {
//        deletePayments(compensation, ADMIN_ENTRY);
//        deletePayments(compensation, MIGRATION);
//        compensation.setPaidAmount(0l); // без этого пересчет исполнения происходит не правельно.
//        //  Потаму в deleteExecution вычисляеться статус по новое, и в вычисление используется это поле. если не зателеть сумму, то вычислится и застеиться статаус 11 или 2
//        // Если сетить суму 0, то всегда вычислиться 2, каторый потом в calculateAndSetExecution пересчитатеся на правельный.
//        compensationActionService.deleteExecution(compensation, "", null);
//        admStatusService.cancelStatus(decision, EXECUTED, IN_EXECUTION_PROCESS);
//        admStatusService.cancelStatus(decision.getResolution(), EXECUTED, IN_EXECUTION_PROCESS);
//        executionCallbackService.executeCallbackWithoutLazy(decision);
//
//        billingExecutionService.calculateAndSetExecution(compensation);
//
//    }
//
//    @Override
//    public void createAdminPayment(User user, Punishment punishment, ManualPaymentRequestDTO dto) {
//        PenaltyPunishment penaltyPunishment = getPenalty(punishment);
//
//        ManualPayment manualPayment = build(penaltyPunishment, ADMIN_ENTRY, dto.getPaidAmount(), dto.getLastPayDate());
//
//        manualPayment.setUser(user);
//
//        manualPaymentService.create(manualPayment);
//
//        // STATUSES
//        billingExecutionService.calculateAndSetExecution(punishment);
//    }
//
//    @Override
//    public void createAdminPayment(User user, Compensation compensation, ManualPaymentRequestDTO dto) {
//        ManualPayment manualPayment = build(compensation, ADMIN_ENTRY, dto.getPaidAmount(), dto.getLastPayDate());
//
//        manualPayment.setUser(user);
//
//        manualPaymentService.create(manualPayment);
//
//        // STATUSES
//        billingExecutionService.calculateAndSetExecution(compensation);
//    }
//
//    private ManualPayment build(PenaltyPunishment penaltyPunishment, ManualPaymentSourceTypeAlias typeAlias, Long amount, LocalDate paymentDate) {
//
//        ManualPayment manualPayment = build(typeAlias, amount, paymentDate);
//        manualPayment.setOwnerType(invoiceOwnerTypeRepository.findByAlias(InvoiceOwnerTypeAlias.PENALTY).orElse(null));
//        manualPayment.setPenaltyPunishment(penaltyPunishment);
//
//        return manualPayment;
//    }
//
//    private ManualPayment build(Compensation compensation, ManualPaymentSourceTypeAlias typeAlias, Long amount, LocalDate paymentDate) {
//
//        ManualPayment manualPayment = build(typeAlias, amount, paymentDate);
//        manualPayment.setOwnerType(invoiceOwnerTypeRepository.findByAlias(InvoiceOwnerTypeAlias.COMPENSATION).orElse(null));
//        manualPayment.setCompensation(compensation);
//
//        return manualPayment;
//    }

    private ManualPayment build(BillingEntity billingEntity, ManualPaymentSourceTypeAlias typeAlias, Long amount, LocalDate paymentDate) {
        BiConsumer<ManualPayment, BillingEntity> ownerSetter = getTypedFunction(this.ownerSetttMap, billingEntity.getInvoiceOwnerTypeAlias());

        ManualPayment manualPayment = new ManualPayment();
        manualPayment.setSourceTypeAlias(typeAlias);
        manualPayment.setAmount(amount);
        manualPayment.setPaymentDate(paymentDate);
        manualPayment.setOwnerTypeAlias(billingEntity.getInvoiceOwnerTypeAlias());
        ownerSetter.accept(manualPayment, billingEntity);

        return manualPayment;
    }

    private ManualPayment createMibPayment(Punishment punishment, PaymentData payment) {

        ManualPayment manualPayment = build(punishment, MIB_EXECUTION, payment.getAmount(), payment.getPaymentTime().toLocalDate());

        // SAVE PAYMENT JSON TO DESCRIPTION
        manualPayment.setDescription(objectMapper.convertValue(payment, JsonNode.class).toString());

        return manualPaymentService.create(manualPayment);
    }

    private void deletePayments(BillingEntity entity, ManualPaymentSourceTypeAlias typeAlias) {
        manualPaymentService.deleteByEntity(entity, typeAlias);
    }

    private PenaltyPunishment getPenalty(Punishment punishment) {
        if (punishment.getType().not(PunishmentTypeAlias.PENALTY))
            throw new LogicalException("Manual billing only for penalty punishment");
        return punishment.getPenalty();
    }

    private <T> T getTypedFunction(EnumMap<InvoiceOwnerTypeAlias, T> map, InvoiceOwnerTypeAlias typeAlias) {
        T func = map.get(typeAlias);
        if (func == null) {
            throw new ImplementationException("Not implement for " + typeAlias);
        }

        return func;
    }
}
