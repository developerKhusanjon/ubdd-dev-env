package uz.ciasev.ubdd_service.service.court.files;

import uz.ciasev.ubdd_service.mvd_core.api.court.dto.first.FirstCourtFileRequestDTO;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;

import java.util.List;

public interface CourtFileSendingService {

    List<FirstCourtFileRequestDTO> buildFiles(Long admCaseId);

    String buildInvoicePath(Invoice invoice);

}
