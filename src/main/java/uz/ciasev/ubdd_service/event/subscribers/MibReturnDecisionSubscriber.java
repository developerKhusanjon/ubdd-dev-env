package uz.ciasev.ubdd_service.event.subscribers;

import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.exception.event.AdmEventUnexpectedDataTypeError;

public abstract class MibReturnDecisionSubscriber implements AdmEventSubscriber {

    @Override
    public boolean canAccept(AdmEventType type, Object data) {
        return AdmEventType.MIB_RETURN_DECISION.equals(type);
    }

    @Override
    public void accept(Object data) {
        if (data instanceof Decision) {
            apply((Decision) data);
        } else {
            throw new AdmEventUnexpectedDataTypeError(AdmEventType.MIB_RETURN_DECISION, Decision.class, data);
        }
    }

    public abstract void apply(Decision decision);

}
