package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.part;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionPart;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionRequirementServices;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.MaterialActionContext;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;

import java.util.List;

@RequiredArgsConstructor
public abstract class CancelResolvedActionPart implements CourtActionPart {

    protected final CourtActionRequirementServices services;

    @Override
    public MaterialActionContext applyPart(MaterialActionContext context) {
        context = applyTyped(context);

        List<Decision> reviewedDecisions = services.getResolutionActionService().review(context.getDecisions());
        services.getStatusService().setStatusAndSave(context.getMaterial(), AdmStatusAlias.SENT_TO_COURT);

        return context
                .resetDecisions(reviewedDecisions);
    }

    protected abstract MaterialActionContext applyTyped(MaterialActionContext context);
}
