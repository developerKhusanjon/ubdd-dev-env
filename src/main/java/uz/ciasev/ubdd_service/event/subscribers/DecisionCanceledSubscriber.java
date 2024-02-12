package uz.ciasev.ubdd_service.event.subscribers;

import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.exception.event.AdmEventUnexpectedDataTypeError;

import java.util.List;

public abstract class DecisionCanceledSubscriber implements AdmEventSubscriber {

    @Override
    public boolean canAccept(AdmEventType type, Object data) {
        return AdmEventType.DECISIONS_CANCEL.equals(type);
    }

    @Override
    public void accept(Object data) {
        if (!(data instanceof List)) {
            throw new AdmEventUnexpectedDataTypeError(AdmEventType.DECISIONS_CANCEL, List.class, data);
        }

        List listTypedData = (List) data;

        try {
            apply((List<Decision>) listTypedData);
        } catch (Exception e) {
            throw new AdmEventUnexpectedDataTypeError(AdmEventType.DECISIONS_CANCEL, e);
        }
    }

    public abstract void apply(List<Decision> decisions);
}
