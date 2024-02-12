package uz.ciasev.ubdd_service.mvd_core.api.court.exception;

import uz.ciasev.ubdd_service.mvd_core.api.court.types.CourtActionName;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;

public class ForbiddenCourtActionError extends CourtValidationException {

    public ForbiddenCourtActionError(CourtActionName actionName) {
        super(String.format(
//                "Действие '%s' запрещено в данном контексте",
                "Action '%s' permitted in given context",
                actionName
        ));
    }
}
