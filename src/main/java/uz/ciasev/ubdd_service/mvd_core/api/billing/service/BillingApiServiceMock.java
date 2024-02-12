package uz.ciasev.ubdd_service.mvd_core.api.billing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.BillingInvoiceAmountRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.PaymentTerms;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.request.BiInvoiceDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.request.ContractDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.request.PayerDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.response.BiInvoiceResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.response.BillingBankResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.response.BillingContractResponseDTO;
import org.springframework.web.client.HttpClientErrorException;
import uz.ciasev.ubdd_service.mvd_core.api.billing.BillingClientApplicationException;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.response.EarlyDiscountResponseDTO;

import java.time.LocalDate;
import java.util.*;


@Service
@Primary
@Profile({"local", "test"})
public class BillingApiServiceMock implements BillingInvoiceApiService {

    private final Random rnd = new Random();
    private final Map<Long, Double> amountMap = new HashMap<>();
    private final RestTemplate restTemplate;
    private final String host;

    @Autowired
    public BillingApiServiceMock(@Value("${billing-api.host}") String host,
                                 @Qualifier("billingRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.host = host;
    }

    @Override
    public void cancelInvoice(Long invoiceId, String declineReason) {
        throw new BillingClientApplicationException(new HttpClientErrorException(HttpStatus.CONFLICT, "ksinntest error"));
    }

    @Override
    public void cancelInvoiceBatch(List<Long> ids, String reason) {
        throw new BillingClientApplicationException(new HttpClientErrorException(HttpStatus.CONFLICT, "ksinntest error"));
    }

    @Override
    public void openInvoice(Long invoiceId) {

    }

    @Override
    public void updateInvoice(String invoiceSerial, BillingInvoiceAmountRequestDTO amount) {
        BiInvoiceResponseDTO r = new BiInvoiceResponseDTO();
        r.setId(rnd.nextLong());
        r.setRequestId("mock-request-id");
        r.setSerial("mock-serial-" + rnd.nextGaussian());
        r.setBalance(1L);
        r.setStatus("mock-status");
        r.setBankAccount("mock-bank-account");
        r.setBudgetAccount("mock-budget-account");
        r.setMfo("mk-mfo");
        r.setContract(new BillingContractResponseDTO(1L, "mock-contract", 1L, "mock-currency"));
        r.setPayer(null);
        r.setBank(new BillingBankResponseDTO(1L, "mock-bank", "mk-mfo", "mock-inn", "mock-type", "mock-branch", "address"));
        r.setPaymentTerms("mock-term");
        r.setIssueDate(LocalDate.now());
        r.setExpireDate(LocalDate.now());

        return;
    }

    @Override
    public BiInvoiceResponseDTO getInvoiceBySerial(String invoiceSerial) {
        BiInvoiceResponseDTO r = new BiInvoiceResponseDTO();
        r.setId(1L);
        r.setRequestId("mock-request-id");
        r.setSerial(invoiceSerial);
        r.setBalance(1L);
        r.setStatus("mock-status");
        r.setBankAccount("mock-bank-account");
        r.setBudgetAccount("mock-budget-account");
        r.setMfo("mk-mfo");
        r.setContract(new BillingContractResponseDTO(1L, "mock-contract", 1L, "mock-currency"));
        r.setPayer(null);
        r.setBank(new BillingBankResponseDTO(1L, "mock-bank", "mk-mfo", "mock-inn", "mock-type", "mock-branch", "address"));
        r.setPaymentTerms("mock-term");
        r.setIssueDate(LocalDate.now());
        r.setExpireDate(LocalDate.now());

        return r;
    }

    @Override
    public PayerDTO createPayer(PayerDTO requestDTO) {
        PayerDTO r = new PayerDTO();

        r.setId(1L);
        r.setName(requestDTO.getName());
        r.setTaxid(requestDTO.getTaxid());
        r.setEmail(requestDTO.getEmail());
        r.setPhone(requestDTO.getPhone());
        r.setType(requestDTO.getType());
        r.setPassport(requestDTO.getPassport());

        return r;
    }

    @Override
    public ContractDTO createContract(ContractDTO requestDTO) {
        Long contractId = rnd.nextLong();
        amountMap.put(contractId, requestDTO.getAmount());

        requestDTO.setId(contractId);
        return requestDTO;
    }

    @Override
    public BiInvoiceResponseDTO createInvoice(BiInvoiceDTO requestDTO) {
        Double amount = amountMap.getOrDefault(requestDTO.getContractId(), 1D);
//
//        var builder = UriComponentsBuilder
//                .fromUriString("/billing/create-invoice")
//                .queryParam("amount", amount)
//                .queryParam("paymentTerm", requestDTO.getPaymentTerms());
//
//        try {
//            ResponseEntity<BiInvoiceResponseDTO> response = restTemplate.exchange(
//                    host + builder.toUriString(),
//                    HttpMethod.GET,
//                    null,
//                    BiInvoiceResponseDTO.class);
//
//            return response.getBody();
//
//        } catch (HttpServerErrorException e) {
//            throw new BillingApplicationException(e);
//        }

        BiInvoiceResponseDTO r = new BiInvoiceResponseDTO();
        r.setId(Integer.valueOf(rnd.nextInt()).longValue());
        r.setRequestId("mock-request-id");
        r.setSerial("mock-serial-" + rnd.nextGaussian());

        List<EarlyDiscountResponseDTO> discounts;
        if (requestDTO.getPaymentTerms().equals(PaymentTerms.DISCOUNT.getValue())) {
            discounts = List.of(
                    new EarlyDiscountResponseDTO(Double.valueOf(amount * 0.70).longValue(), LocalDate.now().plusDays(15))
            );
        } else if (requestDTO.getPaymentTerms().equals(PaymentTerms.DISCOUNT_50_70.getValue())) {
            discounts = List.of(
                    new EarlyDiscountResponseDTO(Double.valueOf(amount * 0.50).longValue(), LocalDate.now().plusDays(15)),
                    new EarlyDiscountResponseDTO(Double.valueOf(amount * 0.70).longValue(), LocalDate.now().plusDays(30))
            );
        } else {
            discounts = null;
        }

        r.setEarlyPaymentDiscounts(discounts);
        r.setBalance(1L);
        r.setStatus("mock-status");
        r.setBankAccount("mock-bank-account");
        r.setBudgetAccount("mock-budget-account");
        r.setMfo("mk-mfo");
        r.setContract(new BillingContractResponseDTO(1L, "mock-contract", amount.longValue(), "mock-currency"));
        r.setPayer(null);
        r.setBank(new BillingBankResponseDTO(1L, "mock-bank", "mk-mfo", "mock-inn", "mock-type", "mock-branch", "address"));
        r.setPaymentTerms("mock-term");
        r.setIssueDate(LocalDate.now());
        r.setExpireDate(LocalDate.now());

        return r;
    }

    @Override
    public ContractDTO getContractByNumber(String number) {
        ContractDTO r = new ContractDTO();

        r.setId(1L);
        r.setNumber(number);
        r.setAmount(1D);
        r.setCurrency("mock-currency");
        r.setPayerId(1L);
        r.setDepartmentId(1L);
        r.setIssueDate(LocalDate.now());
        r.setPayerName("mock-payer-name");
        r.setDepartmentName("mock-department-date");

        return r;
    }
}
