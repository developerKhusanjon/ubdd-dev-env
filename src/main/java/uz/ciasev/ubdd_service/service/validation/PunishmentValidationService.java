package uz.ciasev.ubdd_service.service.validation;

import uz.ciasev.ubdd_service.dto.internal.request.resolution.PunishmentRequestDTO;
import uz.ciasev.ubdd_service.exception.ValidationCollectingError;


public interface PunishmentValidationService {

    @Deprecated
    void validatePunishment(ValidationCollectingError error, PunishmentRequestDTO punishmentRequestDTO);
}
