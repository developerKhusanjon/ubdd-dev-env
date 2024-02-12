package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.resolution;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.*;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.helper.ActionCheckUtils;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.helper.CourtHelper;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.helper.resolution.ResolutionHelper;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.court.CourtResolutionRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.court.CourtFinalResultAlias;
import uz.ciasev.ubdd_service.entity.dict.court.CourtMaterialGroupAlias;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ResolutionCourtActionBuilder implements CourtActionBuilder {

    private final CourtActionFactory actionFactory;
    private final ResolutionHelper resolutionHelper;

    @Override
    public Optional<CourtAction> buildAction(MaterialBuilderContext context, ThirdCourtRequest request) {
        if (CourtHelper.isMovement(request) || request.getStatus().not(CourtStatusAlias.RESOLVED)) {
            return Optional.empty();
        }

        ActionCheckUtils.checkOneDefendant(request);

        if (request.getDefendants().size() != 1)
            throw new NotImplementedException("Material resolution implement only for one violator");

        // todo если материал больше чем на одного нарушителя, надо обрабатывать в разрезе каждого нарушителя
        Boolean isGranted = CourtHelper.extractIGrunted(request)
                .orElseThrow(() -> new CourtValidationException(CourtValidationException.IS_GRANTED_REQUIRED_FOR_MATERIAL_RESOLUTION));

        if (!isGranted) {
            ActionCheckUtils.checkReject(request);
            return Optional.of(actionFactory.createResolutionRejectAction(CourtHelper.extractCourtMaterialRejectBase(request).get()));
        }

        if (request.getMaterialType().is(CourtMaterialGroupAlias.APPEAL_ON_DECISION)) {
            checkResolution(request);
            CourtResolutionRequestDTO resolutionRequestDTO = buildResolution(request);
            return Optional.of(actionFactory.createResolutionGrantedWithNewResolutionAction(resolutionRequestDTO));
        }
        
        ActionCheckUtils.checkAllViolatorHasNoFinalResult(request);
        return Optional.of(actionFactory.createResolutionGrantedAction(request.getHearingTime()));

    }

    private void checkResolution(ThirdCourtRequest request) {
        resolutionHelper.check(request);
        ActionCheckUtils.checkNoOneViolatorHasFinalResult(request, CourtFinalResultAlias.NOT_RELATE);
    }

    private CourtResolutionRequestDTO buildResolution(ThirdCourtRequest request) {

        if (request.getDefendants().size() != 1) {
            throw new NotImplementedException("Material resolution implement only for one violator");
        }

        CourtResolutionRequestDTO resolution = resolutionHelper.build(request);

        return resolution;
    }
}
