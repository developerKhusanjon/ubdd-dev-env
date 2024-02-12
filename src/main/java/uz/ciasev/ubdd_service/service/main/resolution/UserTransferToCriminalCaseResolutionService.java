package uz.ciasev.ubdd_service.service.main.resolution;

import uz.ciasev.ubdd_service.dto.internal.request.resolution.criminal_case.TransferToCriminalCaseRequestDTO;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedResolutionDTO;

public interface UserTransferToCriminalCaseResolutionService {

    CreatedResolutionDTO create(User user, Long admCaseId, TransferToCriminalCaseRequestDTO requestDTO);
}
