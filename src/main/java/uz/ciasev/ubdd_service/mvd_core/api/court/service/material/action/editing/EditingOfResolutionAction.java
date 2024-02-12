package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.editing;

import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionRequirementServices;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.MaterialActionContext;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.part.CanselResolutionActionPart;
import uz.ciasev.ubdd_service.mvd_core.api.court.types.CourtActionName;
import uz.ciasev.ubdd_service.entity.dict.resolution.ReasonCancellationAlias;

import java.util.Collection;
import java.util.List;

public class EditingOfResolutionAction implements CourtAction {

    private final CanselResolutionActionPart canselResolutionActionPart;

    public EditingOfResolutionAction(CourtActionRequirementServices services) {
        this.canselResolutionActionPart = new CanselResolutionActionPart(services, ReasonCancellationAlias.COURT_CORRECTION);
    }

    @Override
    public MaterialActionContext apply(MaterialActionContext context) {
        return canselResolutionActionPart.applyPart(context);
    }

    @Override
    public CourtActionName getName() {
        return CourtActionName.EDITING_OF_RESOLUTION;
    }

    @Override
    public Collection<CourtActionName> getConflicts() {
        return List.of(CourtActionName.MERGE, CourtActionName.MOVEMENT, CourtActionName.REVIEW);
    }
}
