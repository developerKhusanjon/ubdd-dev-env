package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.helper.resolution.punishment;

import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.DefendantRequest;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.court.CourtPunishmentRequestDTO;

public class DeportationPunishmentHelper implements PunishmentHelper {

    @Override
    public void checkAsMain(DefendantRequest defendant, ThirdCourtRequest request) {
        return;
    }

    @Override
    public CourtPunishmentRequestDTO buildAsMain(DefendantRequest defendant, ThirdCourtRequest request) {
        return CourtPunishmentRequestDTO.builder()
                .punishmentType(defendant.getMainPunishmentType())
                .build();
    }
}
