package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.separate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.mvd_core.api.court.exception.ForbiddenCourtActionError;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.helper.ActionCheckUtils;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionBuilder;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.MaterialBuilderContext;
import uz.ciasev.ubdd_service.mvd_core.api.court.types.CourtActionName;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.SeparationRequest;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.entity.dict.court.CourtFinalResultAlias;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class SeparateCourtActionBuilder implements CourtActionBuilder {

    @Override
    public Optional<CourtAction> buildAction(MaterialBuilderContext context, ThirdCourtRequest request) {
        Optional<List<SeparationRequest>> separation = Optional.ofNullable(request.getSeparations());
        if (separation.isEmpty()) {
            validateNoAction(request);
            return Optional.empty();
        }

        ActionCheckUtils.checkStatus(request, CourtStatusAlias.RESOLVED, CourtActionName.MOVEMENT);

        throw new ForbiddenCourtActionError(CourtActionName.SEPARATION);
    }

    private void validateNoAction(ThirdCourtRequest request) {
        ActionCheckUtils.checkNoOneViolatorHasFinalResult(request, CourtFinalResultAlias.SEPARATING);
    }
}
