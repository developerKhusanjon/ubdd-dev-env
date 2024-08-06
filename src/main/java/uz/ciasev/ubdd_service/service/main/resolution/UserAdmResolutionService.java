package uz.ciasev.ubdd_service.service.main.resolution;

import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.SimplifiedResolutionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.SingleResolutionRequestDTO;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedSingleResolutionDTO;
import uz.ciasev.ubdd_service.utils.validator.Inspector;

@Validated
public interface UserAdmResolutionService {

    CreatedSingleResolutionDTO createSingle(@Inspector User user, Long externalId, SingleResolutionRequestDTO resolution);
}
