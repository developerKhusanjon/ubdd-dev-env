package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action;

import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;

import java.util.Optional;

public interface CourtActionBuilder {

    Optional<CourtAction> buildAction(MaterialBuilderContext context, ThirdCourtRequest courtRequest);
}
