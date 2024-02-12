package uz.ciasev.ubdd_service.service.invoice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.BillingInvoiceAmountRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.PayerType;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.PaymentTerms;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.ServiceType;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.request.BiInvoiceDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.request.ContractDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.request.InvoiceRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.request.PayerDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.response.BiInvoiceResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.service.BillingInvoiceApiService;
import uz.ciasev.ubdd_service.entity.court.BillingSending;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.invoice.InvoiceDeactivateReasonAlias;
import uz.ciasev.ubdd_service.entity.invoice.InvoiceOwnerTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.settings.BankAccount;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.GenerateInvoiceForNotPenaltyDecisionException;
import uz.ciasev.ubdd_service.exception.ServerException;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotPresent;
import uz.ciasev.ubdd_service.repository.invoice.InvoiceRepository;
import uz.ciasev.ubdd_service.repository.settings.BankAccountRepository;
import uz.ciasev.ubdd_service.service.address.AddressService;
import uz.ciasev.ubdd_service.service.generator.InvoiceNumberGeneratorService;
import uz.ciasev.ubdd_service.service.publicapi.eventdata.PublicApiWebhookEventPopulationService;
import uz.ciasev.ubdd_service.service.resolution.compensation.CompensationService;
import uz.ciasev.ubdd_service.utils.FormatUtils;
import uz.ciasev.ubdd_service.utils.MoneyFormatter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static uz.ciasev.ubdd_service.mvd_core.api.billing.dto.ServiceType.COMPENSATION;
import static uz.ciasev.ubdd_service.mvd_core.api.billing.dto.ServiceType.PENALTY;

@Service
@RequiredArgsConstructor

public class InvoiceActionServiceImpl implements InvoiceActionService {

    private final AddressService addressService;
    private final BillingInvoiceApiService billingService;
    private final CompensationService compensationService;
    private final InvoiceRepository invoiceRepository;
    private final BillingSendingService billingSendingService;
    private final InvoiceNumberGeneratorService numberGeneratorService;
    private final InvoiceBlockDescriptionService invoiceBlockDescriptionService;
    private final PublicApiWebhookEventPopulationService publicApiWebhookEventPopulationService;
    private final InvoiceService invoiceService;
    private final BankAccountRepository bankAccountRepository;

    @Override
    @Transactional
    public Invoice createForPenalty(Decision decision) {

        var violator = decision.getViolator();
        var penalty = Optional
                .ofNullable(decision.getMainPunishment())
                .map(Punishment::getPenalty)
                .orElseThrow(GenerateInvoiceForNotPenaltyDecisionException::new);

        if (invoiceRepository.existsByPenaltyPunishmentId(penalty.getId())) {
            throw new RuntimeException("Penalty punishment " + penalty.getId() + " уже имеет инвойс");
        }

        BankAccount account = bankAccountRepository.findById(penalty.getAccountId()).orElseThrow(() -> new EntityByIdNotPresent(BankAccount.class, penalty.getAccountId()));

        PenaltyPunishment.DiscountVersion discount = decision.getIsCourt() ? PenaltyPunishment.DiscountVersion.NO : penalty.getDiscount();

        InvoiceRequestDTO invoiceRequest = buildRequest(decision, violator, penalty.getAmount(), account.getBillingId(), PENALTY, discount);
        BiInvoiceResponseDTO billingInvoice = createBiInvoice(invoiceRequest);

        validateDiscountDataSet(discount, billingInvoice);

        Invoice invoice = buildInvoice(invoiceRequest, account, billingInvoice, decision, violator, discount);

        invoice.setDiscountVersion(discount);
        invoice.setOwnerTypeAlias(InvoiceOwnerTypeAlias.PENALTY);
        invoice.setPenaltyPunishment(penalty);

        return invoiceRepository.saveAndFlush(invoice);
    }

    @Override
    @Transactional
    public Invoice createForCompensation(Compensation compensation) {

        if (invoiceRepository.existsByCompensationId(compensation.getId())) {
            throw new RuntimeException("Compensation " + compensation.getId() + " уже имеет инвойс");
        }

        var decision = compensationService.findDecisionByCompensationId(compensation.getId());
        var violator = decision.getViolator();

        var account = compensation.getAccount();
        var invoiceRequest = buildRequest(decision, violator, compensation.getAmount(), account.getBillingId(), COMPENSATION, PenaltyPunishment.DiscountVersion.NO);
        var billingInvoice = createBiInvoice(invoiceRequest);

        var invoice = buildInvoice(invoiceRequest, account, billingInvoice, decision, violator, PenaltyPunishment.DiscountVersion.NO);

        invoice.setOwnerTypeAlias(InvoiceOwnerTypeAlias.COMPENSATION);
        invoice.setCompensation(compensation);

        return invoiceRepository.saveAndFlush(invoice);
    }

    @Override
    @Transactional
    public Invoice editOwner(Invoice invoice, Compensation compensation) {

        invoice.setCompensation(compensation);
        return invoiceRepository.saveAndFlush(invoice);
    }

    @Override
    @Transactional
    public Invoice editOwner(Invoice invoice, PenaltyPunishment penaltyPunishment) {

        invoice.setPenaltyPunishment(penaltyPunishment);
        return invoiceRepository.saveAndFlush(invoice);
    }

    @Override
    @Transactional
    public void open(Invoice invoice) {

        Optional.ofNullable(doOpen(invoice)).ifPresent(billingSendingService::save);
    }

    @Override
    @Transactional
    public void openPermanently(User user, Invoice invoice) {

        BillingSending billingSending = doOpen(invoice);
        if (billingSending == null) {
            return;
        }

        billingService.openInvoice(billingSending.getInvoiceId());
        billingSending.setUser(user);
        billingSending.setIsSent(true);

        billingSendingService.save(billingSending);
    }

    @Override
    @Transactional
    public void updateInvoiceAmount(Invoice invoice, Compensation compensation) {

        updateInvoiceAmount(invoice, compensation.getAmount());
    }

    @Override
    @Transactional
    public void closePermanently(User user, Invoice invoice, InvoiceDeactivateReasonAlias reason, List<Object> params) {

        List<BillingSending> billingSendings = doClose(List.of(invoice), reason, params);

        for (BillingSending billingSending : billingSendings) {
            billingService.cancelInvoice(billingSending.getInvoiceId(), billingSending.getReason());
            billingSending.setIsSent(true);
            billingSending.setUser(user);
            billingSendingService.save(billingSending);
        }

//        registerInvoiceStatusChangeToWebhook(List.of(invoice));
    }

    @Override
    @Transactional
    public void closeForPenalty(PenaltyPunishment penaltyPunishment, InvoiceDeactivateReasonAlias reason, List<Object> params) {
        invoiceService.findByPenalty(penaltyPunishment)
                .ifPresent(invoice -> {
                    close(
                            invoice,
                            reason,
                            params
                    );
                });
    }

    @Override
    @Transactional
    public void close(Invoice invoice, InvoiceDeactivateReasonAlias reason, List<Object> params) {
        try {
            closeBatch(List.of(invoice), reason, params);
        } catch (Exception e) {
            throw new RuntimeException("\nInvoiceActionServiceImpl close throw nullPointer exception");
        }
    }

    @Override
    @Transactional
    public void closeBatch(List<Invoice> invoices, InvoiceDeactivateReasonAlias reason, List<Object> params) {

        List<BillingSending> billingSendings = doClose(invoices, reason, params);

        billingSendingService.saveAll(billingSendings);

//        registerInvoiceStatusChangeToWebhook(invoices);
    }

    @Override
    @Transactional
    public void openForPenalty(PenaltyPunishment penaltyPunishment) {
        invoiceService.findByPenalty(penaltyPunishment)
                .ifPresent(this::open);
    }

    @Override
    @Transactional
    public void updateInvoiceAmount(Invoice invoice, PenaltyPunishment penaltyPunishment) {
        updateInvoiceAmount(invoice, penaltyPunishment.getAmount());
    }

    @Override
    public void updateInvoiceInfoFromBilling(Long invoiceId) {

        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(() -> new EntityByIdNotFound(Invoice.class, invoiceId));

        if (invoice.getInvoiceId() == null)
            throw new ValidationException(ErrorCode.INVOICE_NOT_REGISTERED_IN_BILLING);

        BiInvoiceResponseDTO dto = billingService.getInvoiceBySerial(invoice.getInvoiceSerial());

        invoice.setBankInn(dto.getBank().getInn());
        invoice.setBankName(dto.getBank().getName());
        invoice.setBankCode(dto.getBank().getMfo());
        invoice.setBankAccount(dto.getBankAccount());
        invoice.setTreasuryAccount(dto.getBudgetAccount());

        invoiceRepository.saveAndFlush(invoice);
    }

    private BillingSending doOpen(Invoice invoice) {

        invoice.setActive(true);
        invoice.setDeactivateTime(null);
        invoice.setDeactivateReason(null);
        invoice.setDeactivateReasonDesc(null);
        invoiceRepository.saveAndFlush(invoice);

        registerInvoiceStatusChangeToWebhook(List.of(invoice));

        if (!invoice.registeredInBilling()) {
            return null;
        }

        return BillingSending.open(invoice);
//        return BillingSending.builder()
//                .invoiceId(invoice.getInvoiceId())
//                .action(BillingAction.OPEN_INVOICE)
//                .build();
    }

    private List<BillingSending> doClose(List<Invoice> invoices, InvoiceDeactivateReasonAlias reason, List<Object> params) {
// TODO: 13.11.2023 next 4 
        String reasonDesc = invoiceBlockDescriptionService.getDescriptionByReason(reason, params);

        List<Invoice> billingInvoices = new ArrayList<>();
        try {

            for (Invoice invoice : invoices) {

                invoice.setActive(false);
                invoice.setDeactivateTime(LocalDateTime.now());
                invoice.setDeactivateReason(reason);
                invoice.setDeactivateReasonDesc(reasonDesc);

                if (invoice.registeredInBilling()) {
                    billingInvoices.add(invoice);
                }
            }
            invoiceRepository.saveAll(invoices);
        } catch (NullPointerException e) {
            throw new NullPointerException("InvoiceActionServiceImpl doClose saving invoices throw nullPointer");
        }

        try {
            registerInvoiceStatusChangeToWebhook(invoices);
        }catch (NullPointerException e){
            throw new NullPointerException("InvoiceActionServiceImpl registerInvoiceStatusChangeToWebhook method throw nullPointer ");
        }

        List<BillingSending> billingSendings = new ArrayList<>();
try {

        for (Invoice billingInvoice : billingInvoices) {
            BillingSending billingSending = BillingSending.close(billingInvoice, reason, Optional.ofNullable(reasonDesc).filter(s -> !s.isEmpty()).orElse(reason.toString()));
//            BillingSending billingSending = BillingSending.builder()
//                    .invoiceId(id)
//                    .action(BillingAction.CANCEL_INVOICE)
//                    .reasonType(reason)
//                    .reason(
//                            Optional.ofNullable(reasonDesc).filter(s -> !s.isEmpty()).orElse(reason.toString())
//                    )
//                    .build();
            billingSendings.add(billingSending);
        }
        }catch (NullPointerException e){
    throw new NullPointerException("InvoiceActionServiceImpl BillingSending.close throw nullPointer exception");
}

        return billingSendings;
    }

    private void registerInvoiceStatusChangeToWebhook(List<Invoice> invoices) {

        invoices.forEach(publicApiWebhookEventPopulationService::addInvoiceEvent);
    }

    private void updateInvoiceAmount(Invoice invoice, Long newAmount) {

        // поставить проверку только открытого инвойса???

        invoice.setAmount(newAmount);
//        invoice.setIsDiscount70(isDiscount);
        invoiceRepository.saveAndFlush(invoice);

//        var paymentTerms = Boolean.TRUE.equals(isDiscount)
//                ? DISCOUNT.getValue()
//                : PaymentTerms.FULL_AMOUNT.getValue();

        var paymentTerms = PaymentTerms.FULL_AMOUNT.getValue();

        if (!invoice.registeredInBilling()) return;

        var invoiceAmount = BillingInvoiceAmountRequestDTO.builder()
                .amount(MoneyFormatter.coinToCurrency(newAmount))
                .paymentTerms(paymentTerms)
                .build();

        billingService.updateInvoice(invoice.getInvoiceSerial(), invoiceAmount);
    }

    private Invoice buildInvoice(InvoiceRequestDTO invoiceRequest,
                                 BankAccount account,
                                 BiInvoiceResponseDTO biInvoice,
                                 Decision decision,
                                 Violator violator,
                                 PenaltyPunishment.DiscountVersion discountVersion) {

        var invoice = new Invoice();

        invoice.setAddition("billingDepartmentId=" + account.getBillingId());
        invoice.setInvoiceId(biInvoice.getId());
        invoice.setInvoiceInternalNumber(invoiceRequest.getInvoiceNumber());
        invoice.setInvoiceSerial(biInvoice.getSerial());
        invoice.setInvoiceDate(invoiceRequest.getIssueDate());
        invoice.setDiscount70ForDate(biInvoice.getFee70Date(discountVersion).orElse(null));
        invoice.setDiscount50ForDate(biInvoice.getFee50Date(discountVersion).orElse(null));
        invoice.setAmount(biInvoice.getContract().getAmount() * 100);
        invoice.setDiscount70Amount(biInvoice.getFee70(discountVersion).map(a -> a * 100).orElse(null));
        invoice.setDiscount50Amount(biInvoice.getFee50(discountVersion).map(a -> a * 100).orElse(null));
        invoice.setOrganName(decision.getResolution().getOrgan().getDefaultName());
        invoice.setBankInn(biInvoice.getBank().getInn());
        invoice.setBankName(biInvoice.getBank().getName());
        invoice.setBankCode(biInvoice.getBank().getMfo());
        invoice.setBankAccount(biInvoice.getBankAccount());
        invoice.setTreasuryAccount(biInvoice.getBudgetAccount());
        invoice.setPayerName(violator.getPerson().getFIOLat());
        invoice.setPayerAddress(FormatUtils.addressToText(addressService.findById(violator.getActualAddressId())));
        invoice.setPayerBirthdate(violator.getPerson().getBirthDate());
        invoice.setPayerInn(violator.getInn());

        return invoice;
    }

    private InvoiceRequestDTO buildRequest(Decision decision,
                                           Violator violator,
                                           Long amount,
                                           Long department,
                                           ServiceType serviceType,
                                           PenaltyPunishment.DiscountVersion discount) {

        PaymentTerms paymentTerms = PaymentTerms.getByDiscountVersion(discount);

        InvoiceRequestDTO requestDTO = new InvoiceRequestDTO();

        requestDTO.setInvoiceNumber(numberGeneratorService.generateNumber());

        String fioLat = violator.getPerson().getFIOLat();
        if (fioLat.length() > 100)
            fioLat = fioLat.substring(0, 99);

        requestDTO.setLfName(fioLat);
        requestDTO.setType(PayerType.PERSON);
        requestDTO.setServiceId(serviceType);
        requestDTO.setAmount(MoneyFormatter.coinToCurrency(amount));
        requestDTO.setIssueDate(decision.getExecutionFromDate());
        requestDTO.setPaymentTerms(paymentTerms);
        requestDTO.setDepartmentId(department);

        return requestDTO;
    }

    private BiInvoiceResponseDTO createBiInvoice(InvoiceRequestDTO request) {
        var payerDTO = PayerDTO.builder()
                .name(request.getLfName())
                .taxid(request.getInn())
                .email(request.getEmail())
                .phone(request.getPhone())
                .type(request.getType().getValue())
                .passport(request.getPassport())
                .build();

        var payer = billingService.createPayer(payerDTO);

        var contractDTO = ContractDTO.builder()
                .number(request.getInvoiceNumber())
                .payerId(payer.getId())
                .issueDate(request.getIssueDate())
                .departmentId(request.getDepartmentId())
                .amount(request.getAmount())
                .build();
        var contract = billingService.createContract(contractDTO);

        var invoiceDTO = BiInvoiceDTO.builder()
                .requestId(request.getInvoiceNumber())
                .serviceId(request.getServiceId().getValue())
                .departmentId(request.getDepartmentId())
                .payerId(payer.getId())
                .paymentTerms(request.getPaymentTerms().getValue())
                .contractId(contract.getId())
                .build();

        return billingService.createInvoice(invoiceDTO);
    }

    private void validateDiscountDataSet(PenaltyPunishment.DiscountVersion discountVersion, BiInvoiceResponseDTO billingInvoice) {
        if (discountVersion.isDiscount70()) {
            if (billingInvoice.getFee70(discountVersion).isEmpty() || billingInvoice.getFee70Date(discountVersion).isEmpty()) {
                throw new ServerException(ErrorCode.BILLING_NOT_SET_FEE_DATA);
            }
        }
        if (discountVersion.isDiscount50()) {
            if (billingInvoice.getFee50(discountVersion).isEmpty() || billingInvoice.getFee50Date(discountVersion).isEmpty()) {
                throw new ServerException(ErrorCode.BILLING_NOT_SET_FEE_2_DATA);
            }
        }
    }
}
