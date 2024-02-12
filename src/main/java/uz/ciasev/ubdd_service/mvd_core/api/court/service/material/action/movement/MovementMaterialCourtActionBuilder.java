package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.movement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.*;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.helper.ActionCheckUtils;
import uz.ciasev.ubdd_service.mvd_core.api.court.types.CourtActionName;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.MovementRequest;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.entity.dict.court.CourtFinalResultAlias;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransfer;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MovementMaterialCourtActionBuilder implements CourtActionBuilder {

    private final CourtActionFactory actionFactory;

    @Override
    public Optional<CourtAction> buildAction(MaterialBuilderContext context, ThirdCourtRequest request) {
        Optional<MovementRequest> courtMovement = Optional.ofNullable(request.getMovement());
        Long climeId = courtMovement.map(m -> m.getClaimId()).orElse(null);
        CourtTransfer court = courtMovement.map(m -> m.getCourt()).orElse(null);

        if (Objects.isNull(climeId) != Objects.isNull(court)) {
            throw new CourtValidationException(CourtValidationException.OTHER_COURT_MOVEMENT_DATA_NOT_VALID);
        }

        if (climeId == null) {
            validateNoAction(request);
            return Optional.empty();
        }

        ActionCheckUtils.checkStatus(request, CourtStatusAlias.RESOLVED, CourtActionName.MOVEMENT);
        ActionCheckUtils.checkAllViolatorHasFinalResult(request, CourtFinalResultAlias.SENT_TO_OTHER_COURT);

        return Optional.of(actionFactory.createMovementAction(climeId, court));
    }

    private void validateNoAction(ThirdCourtRequest request) {
        ActionCheckUtils.checkNoOneViolatorHasFinalResult(request, CourtFinalResultAlias.SENT_TO_OTHER_COURT);
    }
}
