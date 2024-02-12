package uz.ciasev.ubdd_service.service.trans.court;

import uz.ciasev.ubdd_service.dto.internal.trans.request.court.CourtTransferCreateRequestDTO;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransfer;
import uz.ciasev.ubdd_service.service.trans.TransEntityCRDService;

public interface CourtTransferAdminService extends TransEntityCRDService<CourtTransfer, CourtTransferCreateRequestDTO> {
}
