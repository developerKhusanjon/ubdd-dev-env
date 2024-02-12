package uz.ciasev.ubdd_service.exception;

import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

public class DecisionStatusNoSuitableException extends ForbiddenException {

    public DecisionStatusNoSuitableException(Decision decision, ActionAlias action) {
        super(
                ErrorCode.DECISION_STATUS_NO_SUITABLE,
                "Недопустимое действие " + action + " для решения " + decision.getId() + " в статусе " + decision.getStatus().getAlias());
    }
}
