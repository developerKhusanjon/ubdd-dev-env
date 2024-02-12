package uz.ciasev.ubdd_service.mvd_core.api.billing.service;

import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.BillingInvoiceAmountRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.request.BiInvoiceDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.request.ContractDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.request.PayerDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.response.BiInvoiceResponseDTO;

import java.util.List;

public interface BillingInvoiceApiService {

    BiInvoiceResponseDTO getInvoiceBySerial(String invoiceSerial);

    void openInvoice(Long invoiceId);

    void cancelInvoice(Long invoiceId, String declineReason);

    void cancelInvoiceBatch(List<Long> ids, String reason);

    void updateInvoice(String invoiceSerial, BillingInvoiceAmountRequestDTO amount);

    PayerDTO createPayer(PayerDTO requestDTO);

    ContractDTO createContract(ContractDTO requestDTO);

    BiInvoiceResponseDTO createInvoice(BiInvoiceDTO requestDTO);

    ContractDTO getContractByNumber(String number);
}
