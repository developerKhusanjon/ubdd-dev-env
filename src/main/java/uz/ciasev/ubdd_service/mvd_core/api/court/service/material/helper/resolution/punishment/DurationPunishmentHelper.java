package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.helper.resolution.punishment;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.DefendantRequest;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.court.CourtPunishmentRequestDTO;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;

@RequiredArgsConstructor
public class DurationPunishmentHelper implements PunishmentHelper {

    private final boolean allowAsAddition;

    @Override
    public void checkAsMain(DefendantRequest defendant, ThirdCourtRequest request) {
        if (defendant.getPunishmentDurationYear() == null && defendant.getPunishmentDurationMonth() == null && defendant.getPunishmentDurationDay() == null)
            throw new CourtValidationException(CourtValidationException.PUNISHMENT_DURATION_REQUIRED);
    }

    @Override
    public CourtPunishmentRequestDTO buildAsMain(DefendantRequest defendant, ThirdCourtRequest request) {
        return CourtPunishmentRequestDTO.builder()
                .punishmentType(defendant.getMainPunishmentType())
                .years(defendant.getPunishmentDurationYear())
                .months(defendant.getPunishmentDurationMonth())
                .days(defendant.getPunishmentDurationDay())
                .build();
    }

    @Override
    public CourtPunishmentRequestDTO buildAsAddition(DefendantRequest defendant, ThirdCourtRequest request) {
        if (!allowAsAddition) {
            throw new CourtValidationException(CourtValidationException.PUNISHMENT_DURATION_REQUIRED);
        }

        return CourtPunishmentRequestDTO.builder()
                .punishmentType(defendant.getAdditionalPunishmentType())
                .years(defendant.getAdditionalPunishmentDurationYear())
                .months(defendant.getAdditionalPunishmentDurationMonth())
                .days(defendant.getAdditionalPunishmentDurationDay())
                .build();
    }

    @Override
    public void checkAsAddition(DefendantRequest defendant, ThirdCourtRequest request) {
        if (!allowAsAddition) {
            throw new CourtValidationException(CourtValidationException.PUNISHMENT_DURATION_REQUIRED);
        }

        if (defendant.getAdditionalPunishmentDurationDay() == null && defendant.getAdditionalPunishmentDurationMonth() == null && defendant.getAdditionalPunishmentDurationDay() == null)
            throw new CourtValidationException(CourtValidationException.PUNISHMENT_DURATION_REQUIRED);
    }
}
