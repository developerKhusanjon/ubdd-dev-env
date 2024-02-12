package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.helper.resolution.punishment;

import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.DefendantRequest;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.court.CourtPunishmentRequestDTO;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;

public class ArrestPunishmentHelper implements PunishmentHelper {

    @Override
    public void checkAsMain(DefendantRequest defendant, ThirdCourtRequest request) {
        if (defendant.getArrestDurationDay() == null)
            throw new CourtValidationException(CourtValidationException.ARREST_DURATION_REQUIRED);
    }

    @Override
    public CourtPunishmentRequestDTO buildAsMain(DefendantRequest defendant, ThirdCourtRequest request) {
        return CourtPunishmentRequestDTO.builder()
                .punishmentType(defendant.getMainPunishmentType())
                .arrestDate(defendant.getArrestDurationDay())
                .build();
    }
}
