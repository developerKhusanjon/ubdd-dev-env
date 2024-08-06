package uz.ciasev.ubdd_service.mvd_core.api.billing.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.BillingPaymentDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.webhook.BillingWebhookRequestDTO;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.service.execution.BillingExecutionService;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillingPaymentApiServiceImpl implements BillingPaymentApiService {

    private static final String PAYMENT_CREATED = "payment.created";

    private final BillingExecutionService billingExecutionService;
    private final ObjectMapper objectMapper;

    @Override
    public void acceptPayment(BillingWebhookRequestDTO webhookData) {
        BillingPaymentDTO data;
        if (PAYMENT_CREATED.equals(webhookData.getType())) {
            try {
                data = objectMapper.treeToValue(webhookData.getData(), BillingPaymentDTO.class);
            } catch (JsonProcessingException e) {
                throw new ValidationException("BILLING_JSON_PARSE_ERROR", e.getMessage());
            }
            billingExecutionService.handlePayment(data);
        }
    }

//
//    /**
//     * Данный метод позволяет получить все полученные оплаты и добавить их в систему
//     */
//    private void findAllNotReceivedPayments(String invoiceSerial) {
//        BillingDataDTO invoice = billingService.getInvoiceBySerial(invoiceSerial);
//
//        List<BillingPaymentDTO> payments = invoice.getPayments();
//
//        for (BillingPaymentDTO payment : payments) {
//            Optional<Payment> paymentOptional = paymentService.findByNumber(payment.getId().toString());
//
//        }
//
//        billingExecutionService.handlePayment();
//    }

//    private BillingPaymentDTO buildPayment(BillingDataDTO data) {
//        return BillingPaymentDTO.builder()
//                .id(data.getId())
//                .invoiceId(data.getInvoiceId())
//                .invoiceSerial(data.getInvoiceSerial())
//                .bid(data.getBid())
//                .amount(data.getAmount())
//                .docNumber(data.getDocNumber())
//                .paidAt(data.getPaidAt())
//                .payerInfo(data.getPayerInfo())
//                .payeeInfo(data.getPayeeInfo())
//                .build();
//    }
//
//    private void validatePayment(BillingPaymentDTO payment) {
//        if (payment.getId() == null || payment.getInvoiceId() == null || payment.getAmount() == null
//                || payment.getDocNumber() == null || payment.getPaidAt() == null
//                || payment.getPayerInfo() == null || payment.getPayeeInfo() == null)
//            throw new BillingArgumentsValidationException();
//
//        validatePayerInfo(payment.getPayerInfo());
//        validatePayeeInfo(payment.getPayeeInfo());
//    }
//
//    private void validateWebhookData(BillingWebhookRequestDTOOld webhookData) {
//        BillingDataDTO data = webhookData.getData();
//
//        if (webhookData.getType().equals(PAYMENT_CREATED)
//                && (data.getId() == null || data.getInvoiceId() == null
//                || data.getInvoiceSerial() == null || data.getAmount() == null
//                || data.getDocNumber() == null || data.getPaidAt() == null
//                || data.getPayerInfo() == null || data.getPayeeInfo() == null
//                || data.getBid() == null))
//            throw new BillingArgumentsValidationException();
//
//        if (webhookData.getType().equals(INVOICE_PAID)
//                && (data.getSerial() == null || data.getPayments().isEmpty()))
//            throw new BillingArgumentsValidationException();
//
//        if (!webhookData.getType().equals(PAYMENT_CREATED) && !webhookData.getType().equals(INVOICE_PAID))
//            throw new BillingArgumentsValidationException();
//    }
//
//    private void validatePayeeInfo(BillingPayeeInfoDTO payeeInfo) {
//        if (payeeInfo.getToBankCode() == null || payeeInfo.getToBankName() == null
//                || payeeInfo.getToBankAccount() == null || payeeInfo.getToInn() == null)
//            throw new BillingArgumentsValidationException();
//    }
//
//    private void validatePayerInfo(BillingPayerInfoDTO payerInfo) {
//        if (payerInfo.getFromBankCode() == null || payerInfo.getFromBankName() == null
//                || payerInfo.getFromBankAccount() == null || payerInfo.getFromInn() == null)
//            throw new BillingArgumentsValidationException();
//    }
}
