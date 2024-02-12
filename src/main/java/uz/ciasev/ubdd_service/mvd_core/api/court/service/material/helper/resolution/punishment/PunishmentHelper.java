package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.helper.resolution.punishment;

import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.DefendantRequest;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.court.CourtPunishmentRequestDTO;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;

public interface PunishmentHelper {

    void checkAsMain(DefendantRequest defendant, ThirdCourtRequest request);

    CourtPunishmentRequestDTO buildAsMain(DefendantRequest defendant, ThirdCourtRequest request);

    default CourtPunishmentRequestDTO buildAsAddition(DefendantRequest defendant, ThirdCourtRequest request) {
        throw new CourtValidationException(CourtValidationException.PUNISHMENT_TYPE_NOT_ALLOWED_AS_ADDITION);
    }

    default void checkAsAddition(DefendantRequest defendant, ThirdCourtRequest request) {
        throw new CourtValidationException(CourtValidationException.PUNISHMENT_TYPE_NOT_ALLOWED_AS_ADDITION);
    }
}
