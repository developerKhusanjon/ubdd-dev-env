package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.review;

import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionRequirementServices;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.part.CanselResolutionActionPart;
import uz.ciasev.ubdd_service.entity.dict.resolution.ReasonCancellationAlias;

public class ReviewResolutionAction extends ReviewCourtAction {
    public ReviewResolutionAction(CourtActionRequirementServices services, long newClimeId) {
        super(services, new CanselResolutionActionPart(services, ReasonCancellationAlias.COURT_REVIEW), newClimeId);
    }
}
