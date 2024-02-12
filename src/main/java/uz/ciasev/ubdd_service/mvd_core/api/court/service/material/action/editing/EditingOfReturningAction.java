package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.editing;

import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionRequirementServices;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.MaterialActionContext;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.part.CancelReturnActionPart;
import uz.ciasev.ubdd_service.mvd_core.api.court.types.CourtActionName;

import java.util.Collection;
import java.util.List;

public class EditingOfReturningAction implements CourtAction {

    private final CancelReturnActionPart cancelReturnActionPart;

    public EditingOfReturningAction(CourtActionRequirementServices services) {
        this.cancelReturnActionPart = new CancelReturnActionPart(services);
    }

    @Override
    public MaterialActionContext apply(MaterialActionContext context) {
        return cancelReturnActionPart.applyPart(context);
    }

    @Override
    public CourtActionName getName() {
        return CourtActionName.EDITING_OF_RETURNING;
    }

    @Override
    public Collection<CourtActionName> getConflicts() {
        return List.of(CourtActionName.MERGE, CourtActionName.MOVEMENT, CourtActionName.REVIEW);
    }
}
