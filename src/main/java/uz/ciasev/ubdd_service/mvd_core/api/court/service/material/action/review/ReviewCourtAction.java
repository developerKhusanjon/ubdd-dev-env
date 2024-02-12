package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.review;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionRequirementServices;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.MaterialActionContext;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.part.CancelResolvedActionPart;
import uz.ciasev.ubdd_service.mvd_core.api.court.types.CourtActionName;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public abstract class ReviewCourtAction implements CourtAction {

    protected final CourtActionRequirementServices services;

    private final CancelResolvedActionPart cancelResolvedActionPart;
    private final long newClimeId;

    @Override
    public MaterialActionContext apply(MaterialActionContext context) {
        context = cancelResolvedActionPart.applyPart(context);

        CourtMaterialFields reviewCourtFields = services.getCourtMaterialFieldsService().review(context.getCourtFields(), newClimeId);
        context.getMaterial().setClaimId(newClimeId);

        return context
                .resetCourtFields(reviewCourtFields);
    }

    @Override
    public CourtActionName getName() {
        return CourtActionName.REVIEW;
    }

    @Override
    public Collection<CourtActionName> getConflicts() {
        return List.of(CourtActionName.EDITING_OF_RETURNING, CourtActionName.EDITING_OF_RETURNING);
    }
}
