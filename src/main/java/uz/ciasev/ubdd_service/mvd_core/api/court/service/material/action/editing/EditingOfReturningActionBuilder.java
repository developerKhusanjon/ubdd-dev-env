package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.editing;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionBuilder;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionFactory;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.MaterialBuilderContext;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.helper.CourtHelper;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class EditingOfReturningActionBuilder implements CourtActionBuilder {

    private final CourtActionFactory actionFactory;

    @Override
    public Optional<CourtAction> buildAction(MaterialBuilderContext context, ThirdCourtRequest request) {
        if (!CourtHelper.isEditingAvailable(context, request)) {
            return Optional.empty();
        }

        CourtMaterialFields fields = context.getCourtMaterialFields();

        if (fields.getCourtStatus().not(CourtStatusAlias.RETURNED)) {
            return Optional.empty();
        }

        return Optional.ofNullable(actionFactory.createEditingOfReturningAction());

    }
}
