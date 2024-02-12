package uz.ciasev.ubdd_service.mvd_core.api.court.exception;

import uz.ciasev.ubdd_service.mvd_core.api.court.types.CourtActionName;

public class ConflictedCourtActionError extends CourtActionError {

    public ConflictedCourtActionError(CourtActionName action, CourtActionName conflictedActionName) {
        super(String.format(
                "Action '%s' and action '%s' conflicted",
                action,
                conflictedActionName
        ));
    }
}
