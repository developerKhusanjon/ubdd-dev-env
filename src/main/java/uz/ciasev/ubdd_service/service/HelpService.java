package uz.ciasev.ubdd_service.service;

import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.response.BiInvoiceResponseDTO;

public interface HelpService {

    void createPayer(int limit);

    void createContract(int limit);

    void createInvoice(int limit);

    BiInvoiceResponseDTO getInvoiceBySerial(String invoiceNumber);
}
