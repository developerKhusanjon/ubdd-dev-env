package uz.ciasev.ubdd_service.event.subscribers;

import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.exception.event.AdmEventUnexpectedDataTypeError;

public abstract class CompensationStatusChangeSubscriber implements AdmEventSubscriber {

    @Override
    public boolean canAccept(AdmEventType type, Object data) {
        return AdmEventType.COMPENSATION_STATUS_CHANGE.equals(type);
    }

    @Override
    public void accept(Object data) {
        if (data instanceof Compensation) {
            apply((Compensation) data);
        } else {
            throw new AdmEventUnexpectedDataTypeError(AdmEventType.COMPENSATION_STATUS_CHANGE, Compensation.class, data);
        }
    }

    public abstract void apply(Compensation compensation);
}
