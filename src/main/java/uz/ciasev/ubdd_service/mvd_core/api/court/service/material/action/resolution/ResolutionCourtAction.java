package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.resolution;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionRequirementServices;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.MaterialActionContext;
import uz.ciasev.ubdd_service.mvd_core.api.court.types.CourtActionName;
import uz.ciasev.ubdd_service.entity.court.CourtMaterial;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public abstract class ResolutionCourtAction implements CourtAction {

    protected final CourtActionRequirementServices services;

    @Override
    public MaterialActionContext apply(MaterialActionContext context) {
        List<Decision> restoredBaseDecisions = services.getResolutionActionService().cancelReview(context.getDecisions());
        context = context.resetDecisions(restoredBaseDecisions);

        MaterialActionContext newContext = applyTyped(context);

        CourtMaterial resolvedMaterial = services.getStatusService().setStatusAndSave(context.getMaterial(), AdmStatusAlias.DECISION_MADE);
        // TODO: 06.11.2023

        return newContext.resetMaterial(resolvedMaterial);
    }

    @Override
    public CourtActionName getName() {
        return CourtActionName.RESOLUTION;
    }

    @Override
    public Collection<CourtActionName> getConflicts() {
        return List.of(CourtActionName.MERGE, CourtActionName.MOVEMENT, CourtActionName.RETURNING);
    }

    protected abstract MaterialActionContext applyTyped(MaterialActionContext context);


}
