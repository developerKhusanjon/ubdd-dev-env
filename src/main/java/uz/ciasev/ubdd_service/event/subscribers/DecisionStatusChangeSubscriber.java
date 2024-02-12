package uz.ciasev.ubdd_service.event.subscribers;

import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.exception.event.AdmEventUnexpectedDataTypeError;

public abstract class DecisionStatusChangeSubscriber implements AdmEventSubscriber {

    @Override
    public boolean canAccept(AdmEventType type, Object data) {
        return AdmEventType.DECISION_STATUS_CHANGE.equals(type);
    }

    @Override
    public void accept(Object data) {
        if (data instanceof Decision) {
            apply((Decision) data);
        } else {
            throw new AdmEventUnexpectedDataTypeError(AdmEventType.DECISION_STATUS_CHANGE, Decision.class, data);
        }
    }

    public abstract void apply(Decision decision);
}
