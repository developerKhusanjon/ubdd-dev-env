package uz.ciasev.ubdd_service.mvd_core.api.court;

import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.first.FirstCourtAdmCaseRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.fourth.FourthCourtPaymentDTO;

public interface CourtApiService {

    CourtSendResult sendAdmCase(CourtRequestDTO<FirstCourtAdmCaseRequestDTO> requestBody);

    CourtSendResult sendInvoiceWithPayment(CourtRequestDTO<FourthCourtPaymentDTO> invoice);

    byte[] getByteByUri(String uri);

//    byte[] getResolutionFileContent(String fileUri);
}
