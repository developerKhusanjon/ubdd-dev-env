package uz.ciasev.ubdd_service.service.court.methods;

import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.fourth.FourthCourtPaymentDTO;
import uz.ciasev.ubdd_service.entity.court.CourtExecutionPayment;

public interface FourMethodToCourtDTOService {

    CourtRequestDTO<FourthCourtPaymentDTO> wrap(CourtExecutionPayment courtExecutionPayment);
}
