package uz.ciasev.ubdd_service.service.main.resolution;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.criminal_case.TransferToCriminalCaseRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.SimplifiedResolutionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.SingleResolutionRequestDTO;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedResolutionDTO;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedSingleResolutionDTO;


@Service
@RequiredArgsConstructor
public class UserResolutionMadeService {

    private final UserAdmResolutionService admResolutionService;
    private final UserTransferToCriminalCaseResolutionService transferToCriminalCaseResolutionService;

    public CreatedSingleResolutionDTO createAdmSimplified(User user, Long admCaseId, SimplifiedResolutionRequestDTO requestDTO) {
        return admResolutionService.createSimplified(user, admCaseId, requestDTO);
    }

    public CreatedSingleResolutionDTO createAdmSingle(User user, Long admCaseId, SingleResolutionRequestDTO requestDTO) {
        return admResolutionService.createSingle(user, admCaseId, requestDTO);
    }

    public CreatedResolutionDTO createTransferToCriminalCase(User user, Long admCaseId, TransferToCriminalCaseRequestDTO requestDTO) {
        return transferToCriminalCaseResolutionService.create(user, admCaseId, requestDTO);
    }

}
