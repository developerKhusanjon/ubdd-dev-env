package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.review;

import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionRequirementServices;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.part.CancelReturnActionPart;

public class ReviewReturnAction extends ReviewCourtAction {
    public ReviewReturnAction(CourtActionRequirementServices services, long newClimeId) {
        super(services, new CancelReturnActionPart(services), newClimeId);
    }
}
