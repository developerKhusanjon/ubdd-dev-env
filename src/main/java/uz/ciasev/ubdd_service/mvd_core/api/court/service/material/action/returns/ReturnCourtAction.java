package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.returns;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionRequirementServices;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.MaterialActionContext;
import uz.ciasev.ubdd_service.mvd_core.api.court.types.CourtActionName;
import uz.ciasev.ubdd_service.entity.court.CourtMaterial;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;
import uz.ciasev.ubdd_service.entity.dict.court.CourtReturnReason;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

import java.util.Collection;
import java.util.List;

import static uz.ciasev.ubdd_service.entity.status.AdmStatusAlias.RETURN_FROM_COURT;

@RequiredArgsConstructor
public abstract class ReturnCourtAction implements CourtAction {

    protected final CourtActionRequirementServices services;
    private final CourtReturnReason returnReason;

    @Override
    public MaterialActionContext apply(MaterialActionContext context) {
        List<Decision> newDecisions = services.getResolutionActionService().cancelReview(context.getDecisions());
        context = context.toBuilder().decisions(newDecisions).build();

        context = applyTyped(context);

        CourtMaterialFields newCourtFields = services.getCourtMaterialFieldsService().returnMaterial(context.getCourtFields(), returnReason);
        CourtMaterial returnedMaterial = services.getStatusService().setStatusAndSave(context.getMaterial(), RETURN_FROM_COURT);

        return context.toBuilder()
                .courtFields(newCourtFields)
                .material(returnedMaterial)
                .build();
    }

    public abstract MaterialActionContext applyTyped(MaterialActionContext context);

    @Override
    public CourtActionName getName() {
        return CourtActionName.RETURNING;
    }

    @Override
    public Collection<CourtActionName> getConflicts() {
        return List.of(CourtActionName.MERGE, CourtActionName.RESOLUTION, CourtActionName.MOVEMENT);
    }
}
