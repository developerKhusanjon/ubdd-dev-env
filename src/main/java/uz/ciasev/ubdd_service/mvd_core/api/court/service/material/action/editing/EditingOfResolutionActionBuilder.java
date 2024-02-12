package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.editing;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.*;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.helper.CourtHelper;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class EditingOfResolutionActionBuilder implements CourtActionBuilder {

    private final CourtActionFactory actionFactory;

    @Override
    public Optional<CourtAction> buildAction(MaterialBuilderContext context, ThirdCourtRequest request) {
        if (!CourtHelper.isEditingAvailable(context, request)) {
            return Optional.empty();
        }

        CourtMaterialFields fields = context.getCourtMaterialFields();

        if (fields.getCourtStatus().not(CourtStatusAlias.RESOLVED)) {
            return Optional.empty();
        }

        return Optional.of(actionFactory.createEditingOfResolutionAction());

    }
}
